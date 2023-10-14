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
// See the License for the specific language governing permissions and
// limitations under the License.

package org.casbin.casdoor.service;


import com.fasterxml.jackson.core.type.TypeReference;
import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.Permission;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.PermissionOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;


public class PermissionService extends Service {
    public PermissionService(Config config) {
        super(config);
    }

    public Permission getPermission(String name) throws IOException {
        CasdoorResponse<Permission, Object> response = doGet("get-permission",
                Map.of("id", config.organizationName + "/" + name), new TypeReference<CasdoorResponse<Permission, Object>>() {});
        return response.getData();
    }

    public List<Permission> getPermissions() throws IOException {
        CasdoorResponse<List<Permission>, Object> resp = doGet("get-permissions",
                Map.of("owner", config.organizationName), new TypeReference<CasdoorResponse<List<Permission>, Object>>() {});
        return resp.getData();
    }

    public List<Permission> getPermissionsByRole(String name) throws IOException {
        CasdoorResponse<List<Permission>, Object> resp = doGet("get-permissions-by-role",
                Map.of("id", config.organizationName + "/" + name,
                        "owner", config.organizationName), new TypeReference<CasdoorResponse<List<Permission>, Object>>() {});

        return resp.getData();
    }
    public java.util.Map<String, Object> getPaginationPermissions(int p, int pageSize, @Nullable java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<Permission[], Object> resp = doGet("get-permissions",
                Map.mergeMap(Map.of("owner", config.organizationName,
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<Permission[], Object>>() {});

        return Map.of("casdoorPermissions", resp.getData(), "data2", resp.getData2());
    }


    public CasdoorResponse<String, Object> updatePermission(Permission permission) throws IOException {
        return modifyPermission(PermissionOperations.UPDATE_PERMISSION, permission);
    }

    public CasdoorResponse<String, Object> updatePermissionForColumns(Permission permission, String... columns) throws IOException {
        return modifyPermission(PermissionOperations.UPDATE_PERMISSION, permission);
    }

    public CasdoorResponse<String, Object> addPermission(Permission permission) throws IOException {
        return modifyPermission(PermissionOperations.ADD_PERMISSION, permission);
    }

    public CasdoorResponse<String, Object> deletePermission(Permission permission) throws IOException {
        return modifyPermission(PermissionOperations.DELETE_PERMISSION, permission);
    }

    /**
     * modifyPermission is an encapsulation of permission CUD(Create, Update, Delete) operations.
     * possible actions are `add-permission`, `update-permission`, `delete-permission`,
     */
    private <T1, T2> CasdoorResponse<T1, T2> modifyPermission(PermissionOperations method, Permission permission) throws IOException {
        return doPost(method.getOperation(),
                Map.of("id", permission.owner + "/" + permission.name),
                objectMapper.writeValueAsString(permission), new TypeReference<CasdoorResponse<T1, T2>>() {});
    }
}
