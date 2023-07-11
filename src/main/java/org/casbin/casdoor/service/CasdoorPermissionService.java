// Copyright 2021 The casbin Authors. All Rights Reserved.
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
// See the License for the specific language governing permissions and
// limitations under the License.

package org.casbin.casdoor.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorPermission;
import org.casbin.casdoor.exception.CasdoorException;
import org.casbin.casdoor.util.MapToUrlUtils;
import org.casbin.casdoor.util.PermissionOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.casbin.casdoor.util.http.HttpClient;

import java.io.IOException;
import java.util.*;


public class CasdoorPermissionService {
    private final CasdoorConfig casdoorConfig;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CasdoorPermissionService(CasdoorConfig casdoorConfig) {
        this.casdoorConfig = casdoorConfig;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public CasdoorPermission getPermission(String name) throws IOException {
        String url = String.format("%s/api/get-permission?id=%s/%s&clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getOrganizationName(), name,
                casdoorConfig.getClientId(), casdoorConfig.getClientSecret());
        String response = HttpClient.syncGet(url);
        return objectMapper.readValue(response, CasdoorPermission.class);

    }

    public CasdoorPermission[] getPermissions() throws IOException {
        String url = String.format("%s/api/get-permissions?owner=%s&clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getOrganizationName(),
                casdoorConfig.getClientId(), casdoorConfig.getClientSecret());
        String response = HttpClient.syncGet(url);
        CasdoorPermission[] permission = objectMapper.readValue(response, CasdoorPermission[].class);
        return permission;

    }


    public CasdoorResponse updatePermission(CasdoorPermission permission) throws IOException {
        return modifyPermission(PermissionOperations.UPDATE_PERMISSION, permission);
    }

    public CasdoorResponse updatePermissionForColumns(CasdoorPermission permission, String... columns) throws IOException {
        return modifyPermission(PermissionOperations.UPDATE_PERMISSION, permission);
    }

    public CasdoorResponse addPermission(CasdoorPermission permission) throws IOException {
        return modifyPermission(PermissionOperations.ADD_PERMISSION, permission);
    }

    public CasdoorResponse deletePermission(CasdoorPermission permission) throws IOException {
        return modifyPermission(PermissionOperations.DELETE_PERMISSION, permission);
    }

    /**
     * modifyPermission is an encapsulation of permission CUD(Create, Update, Delete) operations.
     * possible actions are `add-permission`, `update-permission`, `delete-permission`,
     */
    private CasdoorResponse modifyPermission(PermissionOperations method, CasdoorPermission permission) throws IOException {
        String url = buildUrl(method, permission);
        String response = HttpClient.postString(url, objectMapper.writeValueAsString(permission));
        return objectMapper.readValue(response, CasdoorResponse.class);
    }

    private String buildUrl(PermissionOperations method, CasdoorPermission permission) {
        return String.format("%s/api/%s?id=%s/%s&clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), method.getOperation(), permission.getOwner(),
                permission.getName(), casdoorConfig.getClientId(), casdoorConfig.getClientSecret());
    }

    public Map<String, Object> getPaginationPermissions(int p, int pageSize, Map<String, String> queryMap) throws IOException {
        MapToUrlUtils.queryMapInit(p, pageSize, queryMap,casdoorConfig);

        String url = casdoorConfig.getEndpoint() + "/api/get-permissions?" + MapToUrlUtils.mapToUrlParams(queryMap);
        String response = HttpClient.syncGet(url);

        CasdoorResponse casdoorResponse = objectMapper.readValue(response, CasdoorResponse.class);

        if (!casdoorResponse.getStatus().equals("ok")) {
            throw new CasdoorException(casdoorResponse.getMsg());
        }

        List<CasdoorPermission> permissions = objectMapper.convertValue(casdoorResponse.getData(), new TypeReference<List<CasdoorPermission>>() {});
        int data2 = objectMapper.convertValue(casdoorResponse.getData2(), Integer.class);

        return new HashMap<String, Object>() {{
            put("permissions", permissions);
            put("data2", data2);
        }};

    }

}
