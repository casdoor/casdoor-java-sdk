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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.exception.CasdoorException;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.casbin.casdoor.util.http.HttpClient;
import org.casbin.casdoor.util.MapToUrlUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CasdoorEnforcerService {
    private final CasdoorConfig casdoorConfig;
    private final ObjectMapper objectMapper;

    public CasdoorEnforcerService(CasdoorConfig casdoorConfig) {
        this.casdoorConfig = casdoorConfig;
        this.objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    public boolean enforce(String permissionId, String modelId, String resourceId, Object[] casbinRequest) throws IOException {
        byte[] postBytes = objectMapper.writeValueAsBytes(casbinRequest);
        if (postBytes == null) {
            throw new CasdoorException("Failed to get bytes from URL");
        }
        CasdoorResponse response = doEnforce("enforce", permissionId, modelId, resourceId, postBytes);

        if (!(response.getStatus().equals("ok"))) {
            throw new CasdoorException("Failed to unmarshal JSON");
        }

        List<?> results = (List<?>) response.getData();
        for (Object result : results) {
            if (!(result instanceof Boolean)) {
                throw new CasdoorException("Invalid data");
            }

            if ((Boolean) result) {
                return true;
            }
        }

        return false;
    }
    public boolean[][] batchEnforce(String permissionId, String modelId, String resourceId, Object[][] casbinRequests) throws IOException {
        byte[] postBytes = objectMapper.writeValueAsBytes(casbinRequests);
        if (postBytes == null) {
            throw new CasdoorException("Failed to get bytes from URL");
        }
        CasdoorResponse response = doEnforce("batch-enforce", permissionId, modelId, resourceId, postBytes);

        if (!(response.getStatus().equals("ok"))) {
            throw new CasdoorException("Failed to unmarshal JSON");
        }
        List<?> responseData = (List<?>) response.getData();
        boolean[][] allows = new boolean[responseData.size()][];

        for (int i = 0; i < responseData.size(); i++) {
            Object data = responseData.get(i);
            if (!(data instanceof List<?>)) {
                throw new CasdoorException("Invalid data");
            }

            List<?> dataSublist = (List<?>) data;
            allows[i] = new boolean[dataSublist.size()];

            for (int j = 0; j < dataSublist.size(); j++) {
                Object elem = dataSublist.get(j);
                if (!(elem instanceof Boolean)) {
                    throw new CasdoorException("Invalid data");
                }
                allows[i][j] = (Boolean) elem;
            }
        }

        return allows;
    }
    public CasdoorResponse doEnforce(String action, String permissionId, String modelId, String resourceId, byte[] postBytes) throws IOException {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("action", action);
        queryMap.put("permissionId", casdoorConfig.getOrganizationName()+"/"+permissionId);
        queryMap.put("modelId", modelId);
        queryMap.put("resourceId", resourceId);
        queryMap.put("clientSecret",casdoorConfig.getClientSecret());
        queryMap.put("clientId", casdoorConfig.getClientId());

        String url = null;
        if(queryMap.get("action").equals("enforce")){
            url = casdoorConfig.getEndpoint() + "/api/enforce?" + MapToUrlUtils.mapToUrlParams(queryMap);
        }
        else{
            url = casdoorConfig.getEndpoint() + "/api/batch-enforce?" + MapToUrlUtils.mapToUrlParams(queryMap);

        }
        String data = new String(postBytes, StandardCharsets.UTF_8);
        String response = HttpClient.postString(url, data);
        CasdoorResponse casdoorResponse = objectMapper.readValue(response, CasdoorResponse.class);
        if (!casdoorResponse.getStatus().equals("ok")){
            throw new CasdoorException("Failed to unmarshal JSON");
        }
        return casdoorResponse;
    }

}
