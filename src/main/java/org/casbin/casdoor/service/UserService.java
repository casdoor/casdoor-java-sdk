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
import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.User;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.UserOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;
import java.util.List;

public class UserService extends Service {
    public UserService(Config config) {
        super(config);
    }

    public List<User> getUsers() throws IOException {
        CasdoorResponse<List<User>, Object> resp = doGet("get-users",
                Map.of("owner", config.getOrganizationName()), new TypeReference<CasdoorResponse<List<User>, Object>>() {});
        return resp.getData();
    }
    public List<User> getGlobalUsers() throws IOException {
        CasdoorResponse<List<User>, Object> response = doGet("get-global-users",null,
                new TypeReference<CasdoorResponse<List<User>, Object>>() {});

        return response.getData();
    }
    /**
     * Get users with pagination.
     * @param sorter sorter of users
     * @param limit limit of users to return. If limit is 0, then return all users.
     * @return list of users
     * @throws IOException if failed to get users
     */
    public List<User> getSortedUsers(String sorter, int limit) throws IOException {
        CasdoorResponse<List<User>, Object> resp = doGet("get-users",
                Map.of("owner", config.getOrganizationName(),
                        "sorter", sorter,
                        "limit", limit > 0 ? Integer.toString(limit) : ""), new TypeReference<CasdoorResponse<List<User>, Object>>() {});
        return resp.getData();
    }

    public int getUserCount(String isOnline) throws IOException {
        CasdoorResponse<Integer, Object> resp = doGet("get-user-count",
                Map.of("owner", config.getOrganizationName(),
                        "isOnline", isOnline), new TypeReference<CasdoorResponse<Integer, Object>>() {});
        return resp.getData();
    }

    public User getUser(String name) throws IOException {
        CasdoorResponse<User, Object> resp = doGet("get-user",
                Map.of("id", config.getOrganizationName() + "/" + name), new TypeReference<CasdoorResponse<User, Object>>() {});
        return objectMapper.convertValue(resp.getData(), User.class);
    }

    public User getUserByEmail(String email) throws IOException {
        CasdoorResponse<User, Object> resp = doGet("get-user",
                Map.of("owner", config.getOrganizationName(),
                        "email", email), new TypeReference<CasdoorResponse<User, Object>>() {});
        return resp.getData();
    }

    public CasdoorResponse<String, Object> updateUser(User user) throws IOException {
        return modifyUser(UserOperations.UPDATE_USER, user);
    }

    public CasdoorResponse<String, Object> addUser(User user) throws IOException {
        return modifyUser(UserOperations.ADD_USER, user);
    }

    public CasdoorResponse<String, Object> deleteUser(User user) throws IOException {
        return modifyUser(UserOperations.DELETE_USER, user);
    }

    public CasdoorResponse<String, Object> updateUserById(String id, User user) throws IOException {
        user.setId(id);
        return updateUser(user);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyUser(UserOperations method, User user) throws IOException {
        String userStr = objectMapper.writeValueAsString(user);
        return doPost(method.getOperation(), Map.of(
                "id", user.getOwner() + "/" + user.getName()
        ), userStr, new TypeReference<CasdoorResponse<T1, T2>>() {});
    }

    public java.util.Map<String, Object> getPaginationUsers(int p, int pageSize, java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<List<User>, Object> resp = doGet("get-users",
                Map.mergeMap(Map.of("owner", config.getOrganizationName(),
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<List<User>, Object>>() {});

        return Map.of("casdoorUsers", resp.getData(), "data2", resp.getData2());
    }
}
