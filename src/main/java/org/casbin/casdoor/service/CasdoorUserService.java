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
// See the License for the specific language governing CasdoorPermissions and
// limitations under the License.

package org.casbin.casdoor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorPermission;
import org.casbin.casdoor.entity.CasdoorUser;
import org.casbin.casdoor.exception.CasdoorException;
import org.casbin.casdoor.util.MapToUrlUtils;
import org.casbin.casdoor.util.UserOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.casbin.casdoor.util.http.HttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CasdoorUserService {
    private final CasdoorConfig casdoorConfig;
    final private ObjectMapper objectMapper = new ObjectMapper();

    public CasdoorUserService(CasdoorConfig casdoorConfig) {
        this.casdoorConfig = casdoorConfig;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public CasdoorUser[] getUsers() throws IOException {
        String targetUrl = String.format("%s/api/get-users?owner=%s&clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getOrganizationName(),
                casdoorConfig.getClientId(), casdoorConfig.getClientSecret());
        String response = getUserResponse(targetUrl);
        return objectMapper.readValue(response, CasdoorUser[].class);
    }

    public CasdoorUser[] getSortedUsers(String sorter, int limit) throws IOException {
        String targetUrl = String.format("%s/api/get-sorted-users?owner=%s&clientId=%s&clientSecret=%s&sorter=%s&limit=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getOrganizationName(),
                casdoorConfig.getClientId(), casdoorConfig.getClientSecret(),
                sorter, limit);
        String response = getUserResponse(targetUrl);
        return objectMapper.readValue(response, CasdoorUser[].class);
    }

    public int getUserCount(String isOnline) throws IOException {
        String targetUrl = String.format("%s/api/get-user-count?owner=%s&clientId=%s&clientSecret=%s&isOnline=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getOrganizationName(),
                casdoorConfig.getClientId(), casdoorConfig.getClientSecret(),
                isOnline);
        String response = getUserResponse(targetUrl);
        return objectMapper.readValue(response, Integer.class);
    }

    public CasdoorUser getUser(String name) throws IOException {
        String targetUrl = String.format("%s/api/get-user?id=%s/%s&clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getOrganizationName(), name,
                casdoorConfig.getClientId(), casdoorConfig.getClientSecret());
        String response = getUserResponse(targetUrl);
        return objectMapper.readValue(response, CasdoorUser.class);
    }

    public CasdoorUser getUserByEmail(String email) throws IOException {
        String targetUrl = String.format("%s/api/get-user?owner=%s&clientId=%s&clientSecret=%s&email=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getOrganizationName(),
                casdoorConfig.getClientId(), casdoorConfig.getClientSecret(),
                email);
        String response = getUserResponse(targetUrl);
        return objectMapper.readValue(response, CasdoorUser.class);
    }

    private String getUserResponse(String targetUrl) throws IOException {
        String response = HttpClient.syncGet(targetUrl);
        if (response == null) throw new CasdoorException("Connection timeout.");
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
    public CasdoorResponse updateUserById(String id, CasdoorUser casdoorUser) throws IOException {
        casdoorUser.setId(id);
        return updateUser(casdoorUser);
    }
    private CasdoorResponse modifyUser(UserOperations method, CasdoorUser casdoorUser) throws IOException {
        String targetUrl = String.format("%s/api/%s?id=%s/%s&clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), method.getOperation(),
                casdoorUser.getOwner(), casdoorUser.getName(), casdoorConfig.getClientId(), casdoorConfig.getClientSecret());
        String userStr = objectMapper.writeValueAsString(casdoorUser);
        String responseStr = HttpClient.postString(targetUrl, userStr);
        return objectMapper.readValue(responseStr, CasdoorResponse.class);
    }

    public Map<String, Object> getPaginationUsers(int p, int pageSize, Map<String, String> queryMap) throws IOException {
        queryMapInit(p, pageSize, queryMap);

        String url = buildUsersUrl(queryMap);
        CasdoorResponse casdoorResponse = getCasdoorResponse(url);

        List<CasdoorUser> users = getCasdoorUsers(casdoorResponse);
        int data2 = getCasdoorData2(casdoorResponse);

        return buildResultMap(users, data2);
    }

    private void queryMapInit(int p, int pageSize, Map<String, String> queryMap) {
        String[] keys = {"owner", "clientId", "clientSecret", "p", "pageSize"};
        String[] values = {casdoorConfig.getOrganizationName(), casdoorConfig.getClientId(), casdoorConfig.getClientSecret(), Integer.toString(p), Integer.toString(pageSize)};

        for (int i = 0; i < keys.length; i++) {
            queryMap.put(keys[i], values[i]);
        }
    }

    private String buildUsersUrl(Map<String, String> queryMap) {
        return casdoorConfig.getEndpoint() + "/api/get-users?" + MapToUrlUtils.mapToUrlParams(queryMap);
    }

    private CasdoorResponse getCasdoorResponse(String url) throws IOException {
        String response = HttpClient.syncGet(url);
        return objectMapper.readValue(response, CasdoorResponse.class);
    }
    private List<CasdoorUser> getCasdoorUsers(CasdoorResponse casdoorResponse) {
        return objectMapper.convertValue(casdoorResponse.getData(), new TypeReference<List<CasdoorUser>>() {});
    }

    private int getCasdoorData2(CasdoorResponse casdoorResponse) {
        return objectMapper.convertValue(casdoorResponse.getData2(), Integer.class);
    }

    private Map<String, Object> buildResultMap(List<CasdoorUser> users, int data2) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("casdoorUsers", users);
        resultMap.put("data2", data2);
        return resultMap;
    }


}
