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
import org.casbin.casdoor.exception.CasdoorException;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CasdoorEnforcerService extends CasdoorService {
    public CasdoorEnforcerService(CasdoorConfig casdoorConfig) {
        super(casdoorConfig);
    }

    public boolean enforce(String permissionId, String modelId, String resourceId, Object[] casbinRequest) throws IOException {
        byte[] postBytes = objectMapper.writeValueAsBytes(casbinRequest);
        if (postBytes == null) {
            throw new CasdoorException("Failed to get bytes from URL");
        }
        CasdoorResponse<Boolean[], Object> response = doPost("enforce",
                Map.of(
                        "permissionId", casdoorConfig.getOrganizationName() + "/" + permissionId,
                        "modelId", modelId,
                        "resourceId", resourceId
                ),
                new String(postBytes, StandardCharsets.UTF_8),
                new TypeReference<CasdoorResponse<Boolean[], Object>>() {}
        );

        // All true
        return Arrays.stream(response.getData()).allMatch(Boolean::booleanValue);
    }
    public Boolean[][] batchEnforce(String permissionId, String modelId, String resourceId, Object[][] casbinRequests) throws IOException {
        byte[] postBytes = objectMapper.writeValueAsBytes(casbinRequests);
        if (postBytes == null) {
            throw new CasdoorException("Failed to get bytes from URL");
        }
        CasdoorResponse<Boolean[][], Object> response = doPost("batch-enforce",
                Map.of(
                        "permissionId", casdoorConfig.getOrganizationName() + "/" + permissionId,
                        "modelId", modelId,
                        "resourceId", resourceId
                ),
                new String(postBytes, StandardCharsets.UTF_8),
                new TypeReference<CasdoorResponse<Boolean[][], Object>>() {}
        );

        return response.getData();
    }
}
