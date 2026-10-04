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
import org.casbin.casdoor.entity.CasbinRule;
import org.casbin.casdoor.entity.Enforcer;
import org.casbin.casdoor.exception.Exception;
import org.casbin.casdoor.util.EnforcerOperations;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;

import jakarta.annotation.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class EnforcerService extends Service {
    public EnforcerService(Config config) {
        super(config);
    }

    public Enforcer getEnforcer(String name) throws IOException {
        CasdoorResponse<Enforcer, Object> response = doGet("get-enforcer",
                Map.of("id", getId(name)), new TypeReference<CasdoorResponse<Enforcer, Object>>() {
                });
        return response.getData();
    }

    public List<Enforcer> getEnforcers() throws IOException {
        CasdoorResponse<List<Enforcer>, Object> resp = doGet("get-enforcers",
                Map.of("owner", config.organizationName), new TypeReference<CasdoorResponse<List<Enforcer>, Object>>() {
                });
        return resp.getData();
    }

    public java.util.Map<String, Object> getPaginationEnforcers(int p, int pageSize, @Nullable java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<Enforcer[], Object> casdoorResponse = doGet("get-enforcers",
                Map.mergeMap(Map.of("owner", config.organizationName,
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<Enforcer[], Object>>() {
                });

        return Map.of("casdoorEnforcers", casdoorResponse.getData(), "data2", casdoorResponse.getData2());
    }

    public CasdoorResponse<Object, String> addEnforcer(Enforcer enforcer) throws IOException {
        return modifyEnforcer(EnforcerOperations.ADD_Enforcer, enforcer);
    }

    public CasdoorResponse<Object, String> deleteEnforcer(Enforcer enforcer) throws IOException {
        return modifyEnforcer(EnforcerOperations.DELETE_Enforcer, enforcer);
    }

    public CasdoorResponse<Object, String> updateEnforcer(Enforcer enforcer) throws IOException {
        return modifyEnforcer(EnforcerOperations.UPDATE_Enforcer, enforcer);
    }

    public boolean enforce(String permissionId, String modelId, String resourceId, String enforcerId, String owner, Object[] casbinRequest) throws IOException {
        byte[] postBytes = objectMapper.writeValueAsBytes(casbinRequest);
        if (postBytes == null) {
            throw new Exception("Failed to get bytes from URL");
        }
        CasdoorResponse<Boolean[], Object> response = doPost("enforce",
                Map.of(
                        "permissionId", permissionId != null ? getId(permissionId) : null,
                        "modelId", modelId,
                        "resourceId", resourceId,
                        "enforcerId", enforcerId,
                        "owner", owner
                ),
                new String(postBytes, StandardCharsets.UTF_8),
                new TypeReference<CasdoorResponse<Boolean[], Object>>() {
                }
        );

        // All true
        return Arrays.stream(response.getData()).allMatch(Boolean::booleanValue);
    }

    public boolean enforce(String permissionId, String modelId, String resourceId, Object[] casbinRequest) throws IOException {
        return enforce(permissionId, modelId, resourceId, null, null, casbinRequest);
    }

    public Boolean[][] batchEnforce(String permissionId, String modelId, String resourceId, Object[][] casbinRequests) throws IOException {
        byte[] postBytes = objectMapper.writeValueAsBytes(casbinRequests);
        if (postBytes == null) {
            throw new Exception("Failed to get bytes from URL");
        }
        CasdoorResponse<Boolean[][], Object> response = doPost("batch-enforce",
                Map.of(
                        "permissionId", permissionId != null ? getId(permissionId) : null,
                        "modelId", modelId,
                        "resourceId", resourceId
                ),
                new String(postBytes, StandardCharsets.UTF_8),
                new TypeReference<CasdoorResponse<Boolean[][], Object>>() {
                }
        );

        return response.getData();
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyEnforcer(EnforcerOperations method, Enforcer enforcer, String... columns) throws IOException {
        enforcer.owner = getOwner(enforcer.owner, config.organizationName);
        String id = enforcer.owner + "/" + enforcer.name;
        String payload = objectMapper.writeValueAsString(enforcer);
        return doPost(method.getOperation(),
                Map.of("id", id, "columns", joinColumns(columns)),
                payload, new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }

}
