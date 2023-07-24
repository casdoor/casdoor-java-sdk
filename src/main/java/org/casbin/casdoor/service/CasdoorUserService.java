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
import org.casbin.casdoor.entity.CasdoorUser;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.UserOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;
import java.util.List;

public class CasdoorUserService extends CasdoorService {
    public CasdoorUserService(CasdoorConfig casdoorConfig) {
        super(casdoorConfig);
    }

    public List<CasdoorUser> getUsers() throws IOException {
        CasdoorResponse<List<CasdoorUser>, Object> resp = doGet("get-users",
                Map.of("owner", casdoorConfig.getOrganizationName()), new TypeReference<CasdoorResponse<List<CasdoorUser>, Object>>() {});
        return resp.getData();
    }

    /**
     * Get users with pagination.
     * @param sorter sorter of users
     * @param limit limit of users to return. If limit is 0, then return all users.
     * @return list of users
     * @throws IOException if failed to get users
     */
    public List<CasdoorUser> getSortedUsers(String sorter, int limit) throws IOException {
        CasdoorResponse<List<CasdoorUser>, Object> resp = doGet("get-users",
                Map.of("owner", casdoorConfig.getOrganizationName(),
                        "sorter", sorter,
                        "limit", limit > 0 ? Integer.toString(limit) : ""), new TypeReference<CasdoorResponse<List<CasdoorUser>, Object>>() {});
        return resp.getData();
    }

    public int getUserCount(String isOnline) throws IOException {
        CasdoorResponse<Integer, Object> resp = doGet("get-user-count",
                Map.of("owner", casdoorConfig.getOrganizationName(),
                        "isOnline", isOnline), new TypeReference<CasdoorResponse<Integer, Object>>() {});
        return resp.getData();
    }

    public CasdoorUser getUser(String name) throws IOException {
        CasdoorResponse<CasdoorUser, Object> resp = doGet("get-user",
                Map.of("id", casdoorConfig.getOrganizationName() + "/" + name), new TypeReference<CasdoorResponse<CasdoorUser, Object>>() {});
        return objectMapper.convertValue(resp.getData(), CasdoorUser.class);
    }

    public CasdoorUser getUserByEmail(String email) throws IOException {
        CasdoorResponse<CasdoorUser, Object> resp = doGet("get-user",
                Map.of("owner", casdoorConfig.getOrganizationName(),
                        "email", email), new TypeReference<CasdoorResponse<CasdoorUser, Object>>() {});
        return resp.getData();
    }

    public CasdoorResponse<String, Object> updateUser(CasdoorUser casdoorUser) throws IOException {
        return modifyUser(UserOperations.UPDATE_USER, casdoorUser);
    }

    public CasdoorResponse<String, Object> addUser(CasdoorUser casdoorUser) throws IOException {
        return modifyUser(UserOperations.ADD_USER, casdoorUser);
    }

    public CasdoorResponse<String, Object> deleteUser(CasdoorUser casdoorUser) throws IOException {
        return modifyUser(UserOperations.DELETE_USER, casdoorUser);
    }

    public CasdoorResponse<String, Object> updateUserById(String id, CasdoorUser casdoorUser) throws IOException {
        casdoorUser.setId(id);
        return updateUser(casdoorUser);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyUser(UserOperations method, CasdoorUser casdoorUser) throws IOException {
        String userStr = objectMapper.writeValueAsString(casdoorUser);
        return doPost(method.getOperation(), Map.of(
                "id", casdoorUser.getOwner() + "/" + casdoorUser.getName()
        ), userStr, new TypeReference<CasdoorResponse<T1, T2>>() {});
    }

    public java.util.Map<String, Object> getPaginationUsers(int p, int pageSize, java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<List<CasdoorUser>, Object> resp = doGet("get-users",
                Map.mergeMap(Map.of("owner", casdoorConfig.getOrganizationName(),
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<List<CasdoorUser>, Object>>() {});

        return Map.of("casdoorUsers", resp.getData(), "data2", resp.getData2());
    }
}
