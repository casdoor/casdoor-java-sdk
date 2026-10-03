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

import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.exception.AuthException;
import org.casbin.casdoor.service.AuthService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTest {

    private final AuthService authService = new AuthService(new Config(
            "https://door.casdoor.com",
            "client-id",
            "client-secret",
            "",
            "casbin",
            "app-example"));

    @Test
    public void testGetLogoutUrl() {
        assertEquals("https://door.casdoor.com/api/logout",
                authService.getLogoutUrl(null, null));

        assertEquals("https://door.casdoor.com/api/logout?id_token_hint=token123",
                authService.getLogoutUrl("token123", ""));

        assertEquals("https://door.casdoor.com/api/logout?id_token_hint=token123"
                        + "&post_logout_redirect_uri=https%3A%2F%2Fexample.com%2Fcallback%3Fa%3D1%26b%3D2"
                        + "&client_id=client-id&state=xyz",
                authService.getLogoutUrl("token123", "https://example.com/callback?a=1&b=2", "xyz"));
    }

    @Test
    public void testLogoutWithEmptyToken() {
        assertThrows(AuthException.class, () -> authService.logout(null));
        assertThrows(AuthException.class, () -> authService.logoutCurrentSession(" "));
    }
}
