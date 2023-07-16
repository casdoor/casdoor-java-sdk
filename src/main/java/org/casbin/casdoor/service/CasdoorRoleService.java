// Copyright 2023 The casbin Authors. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing CasdoorPermissions and
// limitations under the License.

package org.casbin.casdoor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorRole;
import org.casbin.casdoor.exception.CasdoorException;
import org.casbin.casdoor.util.MapToUrlUtils;
import org.casbin.casdoor.util.RoleOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.casbin.casdoor.util.http.HttpClient;

import java.io.IOException;
import java.util.*;

public class CasdoorRoleService {
    private final CasdoorConfig casdoorConfig;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CasdoorRoleService(CasdoorConfig casdoorConfig) {
        this.casdoorConfig = casdoorConfig;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    public CasdoorRole getRole(String name) throws IOException {
        String url = String.format("%s/api/get-role?id=%s/%s&clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getOrganizationName(), name,
                casdoorConfig.getClientId(), casdoorConfig.getClientSecret());
        String response = HttpClient.syncGet(url);
        if (response == null) {
            throw new CasdoorException("Failed to get bytes from URL");
        }
        return objectMapper.readValue(response, CasdoorRole.class);

    }

    public CasdoorRole[] getRoles() throws IOException {
        String url = String.format("%s/api/get-roles?owner=%s&clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getOrganizationName(),
                casdoorConfig.getClientId(), casdoorConfig.getClientSecret());
        String response = HttpClient.syncGet(url);
        if (response == null) {
            throw new CasdoorException("Failed to get bytes from URL");
        }
        CasdoorRole[] casdoorRole = objectMapper.readValue(response, CasdoorRole[].class);
        return casdoorRole;

    }
    public Map<String, Object> getPaginationRoles(int p, int pageSize, Map<String, String> queryMap) throws IOException {
        queryMap.put("owner", casdoorConfig.getOrganizationName());
        queryMap.put("clientId", casdoorConfig.getClientId());
        queryMap.put("clientSecret",casdoorConfig.getClientSecret());
        queryMap.put("p", Integer.toString(p));
        queryMap.put("pageSize", Integer.toString(pageSize));

        String url = casdoorConfig.getEndpoint() + "/api/get-roles?" + MapToUrlUtils.mapToUrlParams(queryMap);
        String response = HttpClient.syncGet(url);
        if (response == null) {
            throw new CasdoorException("Failed to get bytes from URL");
        }
        CasdoorResponse casdoorResponse = objectMapper.readValue(response, CasdoorResponse.class);
        if (!casdoorResponse.getStatus().equals("ok")) {
            throw new CasdoorException("Failed to unmarshal JSON");
        }
        List<CasdoorRole> roles = objectMapper.convertValue(casdoorResponse.getData(), new TypeReference<List<CasdoorRole>>() {});
        int data2 = objectMapper.convertValue(casdoorResponse.getData2(), Integer.class);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("casdoorRoles", roles);
        resultMap.put("data2", data2);
        return resultMap;
    }
    public CasdoorResponse updateRole(CasdoorRole role) throws IOException {
        return modifyRole(RoleOperations.UPDATE_ROLE, role);
    }

    public CasdoorResponse updateRoleForColumns(CasdoorRole role, String... columns) throws IOException {
        return modifyRole(RoleOperations.UPDATE_ROLE, role);
    }

    public CasdoorResponse addRole(CasdoorRole role) throws IOException {
        return modifyRole(RoleOperations.ADD_ROLE, role);
    }

    public CasdoorResponse deleteRole(CasdoorRole role) throws IOException {
        return modifyRole(RoleOperations.DELETE_ROLE, role);
    }
    private CasdoorResponse modifyRole(RoleOperations method, CasdoorRole role) throws IOException {
        String url = buildUrl(method, role);
        String response = HttpClient.postString(url, objectMapper.writeValueAsString(role));
        if (response == null) {
            throw new CasdoorException("Failed to get bytes from URL");
        }
        return objectMapper.readValue(response, CasdoorResponse.class);
    }

    private String buildUrl(RoleOperations method, CasdoorRole role) {
        return String.format("%s/api/%s?id=%s/%s&clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), method.getOperation(), role.getOwner(),
                role.getName(), casdoorConfig.getClientId(), casdoorConfig.getClientSecret());
    }


}
