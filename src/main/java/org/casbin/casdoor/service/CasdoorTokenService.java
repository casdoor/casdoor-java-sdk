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
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorToken;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;
import java.util.List;

public class CasdoorTokenService extends CasdoorService {
    public CasdoorTokenService(CasdoorConfig casdoorConfig) {
        super(casdoorConfig);
    }

    /*** Get Tokens
     *
     * @param p index of page. pass -1 if not enable pageable search
     * @param pageSize size of page
     * @return the list of tokens
     * @throws IOException if fails.
     */
    public CasdoorResponse<List<CasdoorToken>, Object> getTokens(int p, int pageSize) throws IOException {
        return doGet("get-tokens", p > -1 ? Map.of(
                "owner", "admin",
                "p", Integer.toString(p),
                "pageSize", Integer.toString(pageSize)
        ) : Map.of(
                "owner", "admin"
        ), new TypeReference<CasdoorResponse<List<CasdoorToken>, Object>>() {});
    }

    public CasdoorResponse<Boolean, Object> deleteToken(CasdoorToken casdoorToken) throws IOException {
        return doPost("delete-token", Map.of(), objectMapper.writeValueAsString(casdoorToken), new TypeReference<CasdoorResponse<Boolean, Object>>() {});
    }
}