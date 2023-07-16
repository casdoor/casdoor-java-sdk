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
import org.casbin.casdoor.entity.CasdoorToken;
import org.casbin.casdoor.exception.CasdoorException;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.casbin.casdoor.util.http.HttpClient;

import java.io.IOException;

public class CasdoorTokenService {
    private final CasdoorConfig casdoorConfig;
    final private ObjectMapper objectMapper = new ObjectMapper();

    public CasdoorTokenService(CasdoorConfig casdoorConfig) {
        this.casdoorConfig = casdoorConfig;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public CasdoorResponse getTokens(int p, int pageSize) throws IOException {
        String url = String.format("%s/api/get-tokens?owner=%s&p=%d&pageSize=%d&clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getOrganizationName(),
                p, pageSize, casdoorConfig.getClientId(), casdoorConfig.getClientSecret());
        String response = HttpClient.syncGet(url);
        if (response == null) {
            throw new CasdoorException("Failed to get bytes from URL");
        }
        CasdoorResponse casdoorResponse = objectMapper.readValue(response, CasdoorResponse.class);
        if(!casdoorResponse.getStatus().equals("ok")){
            throw new CasdoorException("Failed to unmarshal JSON");
        }
        return casdoorResponse;
    }

    public CasdoorResponse deleteToken(CasdoorToken casdoorToken) throws IOException {
        String targetUrl = String.format("%s/api/delete-token?clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getClientId(), casdoorConfig.getClientSecret());

        String tokenStr = objectMapper.writeValueAsString(casdoorToken);
        String response = HttpClient.postString(targetUrl, tokenStr);
        if (response == null) {
            throw new CasdoorException("Failed to get bytes from URL");
        }
        CasdoorResponse casdoorResponse = objectMapper.readValue(response, CasdoorResponse.class);
        if(!casdoorResponse.getStatus().equals("ok")){
            throw new CasdoorException("Failed to unmarshal JSON");
        }
        return casdoorResponse;
    }

}