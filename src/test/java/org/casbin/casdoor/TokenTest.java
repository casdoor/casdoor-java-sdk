// Copyright 2026 The Casdoor Authors. All Rights Reserved.
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

package org.casbin.casdoor;

import org.casbin.casdoor.entity.Token;
import org.casbin.casdoor.service.TokenService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class TokenTest {
    private final TokenService tokenService = new TokenService(TestDefaultConfig.InitConfig());

    @Test
    public void testToken() throws IOException {
        String name = TestDefaultConfig.getRandomName("Token");

        Token token = new Token();
        token.owner = "admin";
        token.name = name;
        token.createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        token.application = "app-casbin";
        token.organization = TestDefaultConfig.TEST_CASDOOR_ORGANIZATION;
        token.user = "admin";
        token.code = "abc";
        token.accessToken = "123456";
        token.expiresIn = 3600;
        token.scope = "read";
        token.tokenType = "Bearer";
        assertEquals("Affected", tokenService.addToken(token).getData());

        assertTrue(tokenService.getTokens().stream().anyMatch(item -> item.name.equals(name)));
        assertTrue((Long) ((Number) tokenService.getPaginationTokens(1, 10, null).get("data2")).longValue() > 0);

        Token retrieved = tokenService.getToken(name);
        assertEquals(name, retrieved.name);

        retrieved.code = "Updated Code";
        assertEquals("Affected", tokenService.updateToken(retrieved).getData());

        retrieved.scope = "profile";
        assertEquals("Affected", tokenService.updateTokenForColumns(retrieved, "scope").getData());

        Token updated = tokenService.getToken(name);
        assertEquals("Updated Code", updated.code);
        assertEquals("profile", updated.scope);

        assertEquals("Affected", tokenService.deleteToken(retrieved).getData());
        assertThrows(Exception.class, () -> tokenService.getToken(name));
    }
}
