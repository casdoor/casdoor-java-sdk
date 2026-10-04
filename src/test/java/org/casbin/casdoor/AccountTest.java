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
import org.casbin.casdoor.entity.OAuthToken;
import org.casbin.casdoor.entity.User;
import org.casbin.casdoor.exception.AuthException;
import org.casbin.casdoor.service.ApplicationService;
import org.casbin.casdoor.service.AuthService;
import org.casbin.casdoor.service.CertService;
import org.casbin.casdoor.service.OrganizationService;
import org.casbin.casdoor.service.TokenService;
import org.casbin.casdoor.service.UserService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    // the CI user "admin" of the CI application's organization (built-in) has the password "123"
    private static final String TEST_USERNAME = "admin";
    private static final String TEST_PASSWORD = "123";

    private final Config config = TestDefaultConfig.InitConfig();
    private final AuthService authService = new AuthService(config);
    private final UserService userService = new UserService(config);

    @Test
    public void testOAuthTokenByPassword() throws IOException {
        OAuthToken token = authService.getOAuthTokenByPassword(TEST_USERNAME, TEST_PASSWORD);
        assertNotNull(token.accessToken);
        assertNotNull(token.refreshToken);

        assertThrows(AuthException.class, () -> authService.getOAuthTokenByPassword(TEST_USERNAME, "wrong-password"));

        java.util.Map<String, Object> introspection = new TokenService(config).introspectToken(token.accessToken, "access_token");
        assertEquals(true, introspection.get("active"));

        // refreshing the token revokes the old access token
        OAuthToken refreshed = authService.refreshOAuthToken(token.refreshToken);
        assertNotNull(refreshed.accessToken);
    }

    @Test
    public void testWithAccessToken() throws IOException {
        OAuthToken token = authService.getOAuthTokenByPassword(TEST_USERNAME, TEST_PASSWORD);

        UserService userUserService = new UserService(config.withAccessToken(token.accessToken));
        User account = userUserService.getAccount();
        assertEquals(TEST_USERNAME, account.name);

        // the original config still calls the APIs as the application
        assertNull(config.accessToken);
        assertThrows(Exception.class, userService::getAccount);

        authService.logoutCurrentSession(token.accessToken);
        authService.logout(authService.getOAuthTokenByPassword(TEST_USERNAME, TEST_PASSWORD).accessToken);
        assertThrows(AuthException.class, () -> authService.logout(""));
    }

    @Test
    public void testUserExtra() throws IOException {
        String name = TestDefaultConfig.getRandomName("User");
        User user = new User();
        user.owner = TestDefaultConfig.TEST_CASDOOR_ORGANIZATION;
        user.name = name;
        user.createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        user.displayName = name;
        user.email = name + "@example.com";
        user.phone = "202555" + TestDefaultConfig.getRandomCode(4);
        user.countryCode = "US";
        user.password = "123456";
        assertEquals("Affected", userService.addUser(user).getData());

        User byEmail = userService.getUserByEmail(user.email);
        assertEquals(name, byEmail.name);
        assertEquals(name, userService.getUserByPhone(user.phone).name);
        assertEquals(name, userService.getUserByUserId(byEmail.id).name);

        assertTrue(((Number) userService.getPaginationUsers(1, 100, null).get("data2")).longValue() > 0);
        assertEquals(1, userService.getSortedUsers("created_time", 1).size());
        assertTrue(userService.getGlobalUsers().stream().anyMatch(item -> item.name.equals(name)));

        byEmail.displayName = "Updated by columns";
        byEmail.bio = "should not be updated";
        assertEquals("Affected", userService.updateUserForColumns(byEmail, "displayName").getData());
        User updated = userService.getUser(name);
        assertEquals("Updated by columns", updated.displayName);
        assertTrue(updated.bio == null || updated.bio.isEmpty());

        updated.displayName = "Updated by id";
        assertEquals("Affected", userService.updateUserById(updated.owner + "/" + name, updated).getData());
        updated.displayName = "Updated by user id";
        assertEquals("Affected", userService.updateUserByUserId(updated.owner, updated.id, updated).getData());
        assertEquals("Updated by user id", userService.getUser(name).displayName);

        updated.password = "123456";
        assertTrue(userService.checkUserPassword(updated));
        updated.password = "wrong-password";
        assertFalse(userService.checkUserPassword(updated));

        assertEquals("Affected", userService.deleteUser(updated).getData());
        assertNull(userService.getUser(name));
    }

    @Test
    public void testOwner() throws IOException {
        assertEquals(TestDefaultConfig.TEST_CASDOOR_ORGANIZATION + "/role", userService.getId("role"));
        assertEquals("other/role", userService.getId("other/role"));

        assertFalse(new OrganizationService(config).getOrganizationNames().isEmpty());
        assertTrue(new ApplicationService(config).getOrganizationApplications().stream().anyMatch(item -> item.name.equals("app-casbin")));
        assertFalse(new CertService(config).getGlobalCerts().isEmpty());

        // an "owner/name" ID addresses an object of another organization
        List<org.casbin.casdoor.entity.Application> applications = new ApplicationService(config).getApplications();
        assertFalse(applications.isEmpty());
    }
}
