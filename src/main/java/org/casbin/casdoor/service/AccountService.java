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

import java.io.IOException;

import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.Organization;
import org.casbin.casdoor.entity.User;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Service Related to Account API
 */
public class AccountService extends Service {
    public AccountService(Config config) {
        super(config);
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
        return doPost("set-password",
                Map.of("owner", config.getOrganizationName()),
                Map.of(
                        "userOwner", config.getOrganizationName(),
                        "userName", userName,
                        "oldPassword", oldPassword,
                        "newPassword", newPassword),
                new TypeReference<CasdoorResponse<Object, Object>>() {
                });
    }

    /**
     * Get current user
     *
     * @param accessToken access token of current user
     * @return user and organization info of current user
     * @throws IOException when JSON unmarshalling fails or HTTP requests fails
     */
    public CasdoorResponse<User, Organization> getAccount(String accessToken) throws IOException {
        return doGet("get-account", Map.of("access_token", accessToken),
                new TypeReference<CasdoorResponse<User, Organization>>() {
                });
    }
}
