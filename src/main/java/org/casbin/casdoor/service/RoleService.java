// Copyright 2023 The Casdoor Authors. All Rights Reserved.
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
import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.Role;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.RoleOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public class RoleService extends Service {
    public RoleService(Config config) {
        super(config);
    }

    public Role getRole(String name) throws IOException {
        CasdoorResponse<Role, Object> resp = doGet("get-role",
                Map.of("id", getConfig().getOrganizationName() + "/" + name), new TypeReference<CasdoorResponse<Role, Object>>() {
                });
        return resp.getData();
    }

    public List<Role> getRoles() throws IOException {
        CasdoorResponse<List<Role>, Object> resp = doGet("get-roles",
                Map.of("owner", getConfig().getOrganizationName()), new TypeReference<CasdoorResponse<List<Role>, Object>>() {
                });
        return resp.getData();
    }

    public java.util.Map<String, Object> getPaginationRoles(int p, int pageSize, @Nullable java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<Role[], Object> casdoorResponse = doGet("get-roles",
                Map.mergeMap(Map.of("owner", getConfig().getOrganizationName(),
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<Role[], Object>>() {
                });

        return Map.of("casdoorRoles", casdoorResponse.getData(), "data2", casdoorResponse.getData2());
    }

    public CasdoorResponse<String, Object> updateRole(Role role) throws IOException {
        return modifyRole(RoleOperations.UPDATE_ROLE, role);
    }

    public CasdoorResponse<String, Object> updateRoleForColumns(Role role, String... columns) throws IOException {
        return modifyRole(RoleOperations.UPDATE_ROLE, role);
    }

    public CasdoorResponse<String, Object> addRole(Role role) throws IOException {
        return modifyRole(RoleOperations.ADD_ROLE, role);
    }

    public CasdoorResponse<String, Object> deleteRole(Role role) throws IOException {
        return modifyRole(RoleOperations.DELETE_ROLE, role);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyRole(RoleOperations method, Role role) throws IOException {
        String id = role.owner + "/" + role.name;
        role.owner = getConfig().getOrganizationName();
        String payload = getObjectMapper().writeValueAsString(role);
        return doPost(method.getOperation(),
                Map.of("id", id), payload
                , new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}
