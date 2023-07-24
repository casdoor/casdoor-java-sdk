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
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorRole;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.RoleOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public class CasdoorRoleService extends CasdoorService {
    public CasdoorRoleService(CasdoorConfig casdoorConfig) {
        super(casdoorConfig);
    }

    public CasdoorRole getRole(String name) throws IOException {
        CasdoorResponse<CasdoorRole, Object> resp = doGet("get-role",
                Map.of("id", casdoorConfig.getOrganizationName() + "/" + name), new TypeReference<CasdoorResponse<CasdoorRole, Object>>() {});
        return resp.getData();
    }

    public List<CasdoorRole> getRoles() throws IOException {
        CasdoorResponse<List<CasdoorRole>, Object> resp = doGet("get-roles",
                Map.of("owner", casdoorConfig.getOrganizationName()), new TypeReference<CasdoorResponse<List<CasdoorRole>, Object>>() {});
        return resp.getData();
    }

    public java.util.Map<String, Object> getPaginationRoles(int p, int pageSize, @Nullable java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<CasdoorRole[], Object> casdoorResponse = doGet("get-roles",
                Map.mergeMap(Map.of("owner", casdoorConfig.getOrganizationName(),
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<CasdoorRole[], Object>>() {});

        return Map.of("casdoorRoles", casdoorResponse.getData(), "data2", casdoorResponse.getData2());
    }
    public CasdoorResponse<String, Object> updateRole(CasdoorRole role) throws IOException {
        return modifyRole(RoleOperations.UPDATE_ROLE, role);
    }

    public CasdoorResponse<String, Object> updateRoleForColumns(CasdoorRole role, String... columns) throws IOException {
        return modifyRole(RoleOperations.UPDATE_ROLE, role);
    }

    public CasdoorResponse<String, Object> addRole(CasdoorRole role) throws IOException {
        return modifyRole(RoleOperations.ADD_ROLE, role);
    }

    public CasdoorResponse<String, Object> deleteRole(CasdoorRole role) throws IOException {
        return modifyRole(RoleOperations.DELETE_ROLE, role);
    }
    private <T1, T2> CasdoorResponse<T1, T2> modifyRole(RoleOperations method, CasdoorRole role) throws IOException {
       return doPost(method.getOperation(),
                Map.of("id", role.getOwner() + "/" + role.getName()),
                objectMapper.writeValueAsString(role), new TypeReference<CasdoorResponse<T1, T2>>() {});
    }
}
