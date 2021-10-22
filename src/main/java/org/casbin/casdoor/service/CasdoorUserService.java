// Copyright 2021 The casbin Authors. All Rights Reserved.
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
import org.casbin.casdoor.entity.CasdoorUser;
import org.casbin.casdoor.exception.CasdoorException;
import org.casbin.casdoor.util.UserOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.casbin.casdoor.util.http.HttpClient;

import java.io.IOException;

public class CasdoorUserService {
    private final CasdoorConfig casdoorConfig;
    final private ObjectMapper objectMapper = new ObjectMapper();

    public CasdoorUserService(CasdoorConfig casdoorConfig) {
        this.casdoorConfig = casdoorConfig;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public CasdoorUser[] getUsers() throws Exception {
        String targetUrl = String.format("%s/api/get-users?owner=%s&clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getOrganizationName(),
                casdoorConfig.getClientId(), casdoorConfig.getClientSecret());
        String response = getUserResponse(targetUrl);
        return objectMapper.readValue(response, CasdoorUser[].class);
    }

    public CasdoorUser getUser(String name) throws Exception {
        String targetUrl = String.format("%s/api/get-user?id=%s/%s&clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getOrganizationName(), name,
                casdoorConfig.getClientId(), casdoorConfig.getClientSecret());
        String response = getUserResponse(targetUrl);
        return objectMapper.readValue(response, CasdoorUser.class);
    }

    private String getUserResponse(String targetUrl) throws Exception {
        String response = HttpClient.syncGet(targetUrl);
        if (response == null) throw CasdoorException.NETWORK_EXCEPTION;
        return response;
    }

    public CasdoorResponse updateUser(CasdoorUser casdoorUser) throws IOException {
        return modifyUser(UserOperations.UPDATE_USER, casdoorUser);
    }

    public CasdoorResponse addUser(CasdoorUser casdoorUser) throws IOException {
        return modifyUser(UserOperations.ADD_USER, casdoorUser);
    }

    public CasdoorResponse deleteUser(CasdoorUser casdoorUser) throws IOException {
        return modifyUser(UserOperations.DELETE_USER, casdoorUser);
    }

    private CasdoorResponse modifyUser(UserOperations method, CasdoorUser casdoorUser) throws IOException {
        String targetUrl = String.format("%s/api/%s?id=%s/%s&clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), method.getOperation(),
                casdoorUser.getOwner(), casdoorUser.getName(), casdoorConfig.getClientId(), casdoorConfig.getClientSecret());
        String userStr = objectMapper.writeValueAsString(casdoorUser);
        String responseStr = HttpClient.postString(targetUrl, userStr);
        return objectMapper.readValue(responseStr, CasdoorResponse.class);
    }
}
