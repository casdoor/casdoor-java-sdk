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

package org.casbin.casdoor;

import org.casbin.casdoor.entity.Role;
import org.casbin.casdoor.entity.User;
import org.casbin.casdoor.service.UserService;
import org.casbin.casdoor.support.ConfigFactory;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserServiceTest extends CasdoorServiceTest {

    private UserService casdoorUserService;

    @Before
    public void init() {
        casdoorUserService = new UserService(ConfigFactory.getConfig());
    }

    @Test
    public void testGetUser() throws Exception {
        User user = casdoorUserService.getUser("admin");
        Assert.assertNotNull(user);
        user = casdoorUserService.getUserByEmail("admin@example.com");
        Assert.assertNotNull(user);
    }

    @Test
    public void testGetUsers() throws Exception {
        List<User> users = casdoorUserService.getUsers();
        Assert.assertNotNull(users);
        users = casdoorUserService.getSortedUsers("created_time", 5);
        System.err.println(users.get(0).name);
        Assert.assertNotNull(users);
        Assert.assertEquals(5, users.size());
    }

    @Test
    public void testGetUserCount() throws Exception {
        int count = casdoorUserService.getUserCount("");
        Assert.assertTrue(count >= 0);
    }

    @Test
    public void testModifyUser() throws IOException {

        User user = new User();
        user.owner = "built-in";
        user.name = "test-modify-user";
        CasdoorResponse response = casdoorUserService.addUser(user);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        user.displayName = "test-display-name";
        response = casdoorUserService.updateUser(user);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        response = casdoorUserService.deleteUser(user);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());
    }

    @Test
    public void testGetPaginationUsers() throws IOException {
        Map<String, String> queryMap = new HashMap<>();
        Map<String, Object> result = casdoorUserService.getPaginationUsers(1, 10, queryMap);
        assertNotNull(result);
        assertTrue(result.containsKey("casdoorUsers"));
        assertTrue(result.containsKey("data2"));

        List<Role> roles = (List<Role>) result.get("casdoorUsers");
        int data2 = (int) result.get("data2");

        assertTrue(!roles.isEmpty());
        assertTrue(data2 > 0);
    }

    @Test
    public void testGetGolbalUsers() throws IOException {
        List<User> globalUsers = casdoorUserService.getGlobalUsers();
        assertNotNull(globalUsers);
    }

}
