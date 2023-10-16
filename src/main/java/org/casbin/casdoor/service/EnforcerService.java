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
import org.casbin.casdoor.entity.Enforcer;
import org.casbin.casdoor.exception.Exception;
import org.casbin.casdoor.util.EnforcerOperations;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;

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
                Map.of("id", config.organizationName + "/" + name), new TypeReference<CasdoorResponse<Enforcer, Object>>() {});
        return response.getData();
    }

    public List<Enforcer> getEnforcers() throws IOException {
        CasdoorResponse<List<Enforcer>, Object> resp = doGet("get-enforcers",
                Map.of("owner", config.organizationName), new TypeReference<CasdoorResponse<List<Enforcer>, Object>>() {});
        return resp.getData();
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

    public boolean enforce(String permissionId, String modelId, String resourceId, Object[] casbinRequest) throws IOException {
        byte[] postBytes = objectMapper.writeValueAsBytes(casbinRequest);
        if (postBytes == null) {
            throw new Exception("Failed to get bytes from URL");
        }
        CasdoorResponse<Boolean[], Object> response = doPost("enforce",
                Map.of(
                        "permissionId", config.organizationName + "/" + permissionId,
                        "modelId", modelId,
                        "resourceId", resourceId
                ),
                new String(postBytes, StandardCharsets.UTF_8),
                new TypeReference<CasdoorResponse<Boolean[], Object>>() {
                }
        );

        // All true
        return Arrays.stream(response.getData()).allMatch(Boolean::booleanValue);
    }

    public Boolean[][] batchEnforce(String permissionId, String modelId, String resourceId, Object[][] casbinRequests) throws IOException {
        byte[] postBytes = objectMapper.writeValueAsBytes(casbinRequests);
        if (postBytes == null) {
            throw new Exception("Failed to get bytes from URL");
        }
        CasdoorResponse<Boolean[][], Object> response = doPost("batch-enforce",
                Map.of(
                        "permissionId", config.organizationName + "/" + permissionId,
                        "modelId", modelId,
                        "resourceId", resourceId
                ),
                new String(postBytes, StandardCharsets.UTF_8),
                new TypeReference<CasdoorResponse<Boolean[][], Object>>() {
                }
        );

        return response.getData();
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyEnforcer(EnforcerOperations method, Enforcer enforcer) throws IOException {
        String id = enforcer.owner + "/" + enforcer.name;
        String payload = objectMapper.writeValueAsString(enforcer);
        return doPost(method.getOperation(),
                Map.of("id", enforcer.owner + "/" + enforcer.name),
                objectMapper.writeValueAsString(enforcer), new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}
