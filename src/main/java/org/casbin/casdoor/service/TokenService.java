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
// See the License for the specific language governing permissions and
// limitations under the License.

package org.casbin.casdoor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.Token;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.casbin.casdoor.util.http.HttpClient;
import jakarta.annotation.Nullable;

import java.io.IOException;
import java.util.List;

public class TokenService extends Service {
    public TokenService(Config config) {
        super(config);
    }

    public List<Token> getTokens() throws IOException {
        return getTokens(-1, 0).getData();
    }

    /*** Get Tokens
     *
     * @param p index of page. pass -1 if not enable pageable search
     * @param pageSize size of page
     * @return the list of tokens
     * @throws IOException if fails.
     */
    public CasdoorResponse<List<Token>, Object> getTokens(int p, int pageSize) throws IOException {
        return doGet("get-tokens", p > -1 ? Map.of(
                "owner", "admin",
                "p", Integer.toString(p),
                "pageSize", Integer.toString(pageSize)
        ) : Map.of(
                "owner", "admin"
        ), new TypeReference<CasdoorResponse<List<Token>, Object>>() {});
    }

    public java.util.Map<String, Object> getPaginationTokens(int p, int pageSize, @Nullable java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<Token[], Object> casdoorResponse = doGet("get-tokens",
                Map.mergeMap(Map.of("owner", "admin",
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<Token[], Object>>() {
                });
        return Map.of("casdoorTokens", casdoorResponse.getData(), "data2", casdoorResponse.getData2());
    }

    public Token getToken(String name) throws IOException {
        CasdoorResponse<Token, Object> response = doGet("get-token",
                Map.of("id", getAdminId(name)), new TypeReference<CasdoorResponse<Token, Object>>() {});
        return response.getData();
    }

    public CasdoorResponse<String, Object> addToken(Token token) throws IOException {
        return modifyToken("add-token", token);
    }

    public CasdoorResponse<String, Object> updateToken(Token token) throws IOException {
        return modifyToken("update-token", token);
    }

    public CasdoorResponse<String, Object> updateTokenForColumns(Token token, String... columns) throws IOException {
        return modifyToken("update-token", token, columns);
    }

    public CasdoorResponse<String, Object> deleteToken(Token token) throws IOException {
        return modifyToken("delete-token", token);
    }

    /**
     * Introspects the token (RFC 7662), the result contains "active" and the claims of the token.
     * @param token the access token or the refresh token
     * @param tokenTypeHint "access_token" or "refresh_token"
     * @return the introspection result
     * @throws IOException when the request fails
     */
    public java.util.Map<String, Object> introspectToken(String token, String tokenTypeHint) throws IOException {
        String url = String.format("%s/api/login/oauth/introspect", config.endpoint);
        String response = HttpClient.postForm(url, Map.of("token", token, "token_type_hint", tokenTypeHint), headers(), false);
        return objectMapper.readValue(response, new TypeReference<java.util.Map<String, Object>>() {});
    }

    private CasdoorResponse<String, Object> modifyToken(String action, Token token, String... columns) throws IOException {
        token.owner = getOwner(token.owner, "admin");
        String payload = objectMapper.writeValueAsString(token);
        return doPost(action, Map.of("id", token.owner + "/" + token.name, "columns", joinColumns(columns)), payload,
                new TypeReference<CasdoorResponse<String, Object>>() {});
    }
}
