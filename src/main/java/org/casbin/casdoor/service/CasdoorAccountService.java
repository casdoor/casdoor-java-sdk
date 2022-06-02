// Copyright 2022 The Casdoor Authors. All Rights Reserved.
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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.casbin.casdoor.util.http.HttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Service Related to Account API
 */
public class CasdoorAccountService {
    private final CasdoorConfig casdoorConfig;
    final private ObjectMapper objectMapper = new ObjectMapper();

    public CasdoorAccountService(CasdoorConfig casdoorConfig) {
        this.casdoorConfig = casdoorConfig;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Set user password by username, old and new passwords
     *
     * @param userName    username
     * @param oldPassword old password
     * @param newPassword new password
     * @return CasdoorResponse
     * @throws IOException when JSON unmarshalling fails or HTTP requests fails
     */
    public CasdoorResponse setPassword(String userName, String oldPassword, String newPassword) throws IOException {
        String targetUrl = String.format("%s/api/set-password?owner=%s&clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getOrganizationName(),
                casdoorConfig.getClientId(), casdoorConfig.getClientSecret());

        Map<String, String> formData = new HashMap<>(4);
        formData.put("userOwner", casdoorConfig.getOrganizationName());
        formData.put("userName", userName);
        formData.put("oldPassword", oldPassword);
        formData.put("newPassword", newPassword);

        String responseStr = HttpClient.postForm(targetUrl, formData);
        return objectMapper.readValue(responseStr, CasdoorResponse.class);
    }
}
