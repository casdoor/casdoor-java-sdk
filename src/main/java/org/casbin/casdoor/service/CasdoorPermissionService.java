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
// See the License for the specific language governing permissions and
// limitations under the License.

package org.casbin.casdoor.service;


import com.fasterxml.jackson.core.type.TypeReference;
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorPermission;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.PermissionOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;


public class CasdoorPermissionService extends CasdoorService {
    public CasdoorPermissionService(CasdoorConfig casdoorConfig) {
        super(casdoorConfig);
    }

    public CasdoorPermission getPermission(String name) throws IOException {
        CasdoorResponse<CasdoorPermission, Object> response = doGet("get-permission",
                Map.of("id", casdoorConfig.getOrganizationName() + "/" + name), new TypeReference<CasdoorResponse<CasdoorPermission, Object>>() {});
        return response.getData();
    }

    public List<CasdoorPermission> getPermissions() throws IOException {
        CasdoorResponse<List<CasdoorPermission>, Object> resp = doGet("get-permissions",
                Map.of("owner", casdoorConfig.getOrganizationName()), new TypeReference<CasdoorResponse<List<CasdoorPermission>, Object>>() {});
        return resp.getData();
    }

    public List<CasdoorPermission> getPermissionsByRole(String name) throws IOException {
        CasdoorResponse<List<CasdoorPermission>, Object> resp = doGet("get-permissions-by-role",
                Map.of("id", casdoorConfig.getOrganizationName() + "/" + name,
                        "owner", casdoorConfig.getOrganizationName()), new TypeReference<CasdoorResponse<List<CasdoorPermission>, Object>>() {});

        return resp.getData();
    }
    public java.util.Map<String, Object> getPaginationPermissions(int p, int pageSize, @Nullable java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<CasdoorPermission[], Object> resp = doGet("get-permissions",
                Map.mergeMap(Map.of("owner", casdoorConfig.getOrganizationName(),
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<CasdoorPermission[], Object>>() {});

        return Map.of("casdoorPermissions", resp.getData(), "data2", resp.getData2());
    }


    public CasdoorResponse<String, Object> updatePermission(CasdoorPermission permission) throws IOException {
        return modifyPermission(PermissionOperations.UPDATE_PERMISSION, permission);
    }

    public CasdoorResponse<String, Object> updatePermissionForColumns(CasdoorPermission permission, String... columns) throws IOException {
        return modifyPermission(PermissionOperations.UPDATE_PERMISSION, permission);
    }

    public CasdoorResponse<String, Object> addPermission(CasdoorPermission permission) throws IOException {
        return modifyPermission(PermissionOperations.ADD_PERMISSION, permission);
    }

    public CasdoorResponse<String, Object> deletePermission(CasdoorPermission permission) throws IOException {
        return modifyPermission(PermissionOperations.DELETE_PERMISSION, permission);
    }

    /**
     * modifyPermission is an encapsulation of permission CUD(Create, Update, Delete) operations.
     * possible actions are `add-permission`, `update-permission`, `delete-permission`,
     */
    private <T1, T2> CasdoorResponse<T1, T2> modifyPermission(PermissionOperations method, CasdoorPermission permission) throws IOException {
        return doPost(method.getOperation(),
                Map.of("id", permission.getOwner() + "/" + permission.getName()),
                objectMapper.writeValueAsString(permission), new TypeReference<CasdoorResponse<T1, T2>>() {});
    }
}
