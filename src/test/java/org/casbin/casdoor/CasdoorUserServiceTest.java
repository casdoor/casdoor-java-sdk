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

import org.casbin.casdoor.entity.CasdoorRole;
import org.casbin.casdoor.entity.CasdoorUser;
import org.casbin.casdoor.service.CasdoorUserService;
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

public class CasdoorUserServiceTest extends CasdoorServiceTest {

    private CasdoorUserService casdoorUserService;

    @Before
    public void init() {
        casdoorUserService = new CasdoorUserService(ConfigFactory.getConfig());
    }

    @Test
    public void testGetUser() throws Exception {
        CasdoorUser casdoorUser = casdoorUserService.getUser("admin");
        Assert.assertNotNull(casdoorUser);
        casdoorUser = casdoorUserService.getUserByEmail("admin@example.com");
        Assert.assertNotNull(casdoorUser);
    }

    @Test
    public void testGetUsers() throws Exception {
        List<CasdoorUser> casdoorUsers = casdoorUserService.getUsers();
        Assert.assertNotNull(casdoorUsers);
        casdoorUsers = casdoorUserService.getSortedUsers("created_time", 5);
        System.err.println(casdoorUsers.get(0).getName());
        Assert.assertNotNull(casdoorUsers);
        Assert.assertEquals(5, casdoorUsers.size());
    }

    @Test
    public void testGetUserCount() throws Exception {
        int count = casdoorUserService.getUserCount("");
        Assert.assertTrue(count >= 0);
    }

    @Test
    public void testModifyUser() throws IOException {

        CasdoorUser user = new CasdoorUser();
        user.setOwner("built-in");
        user.setName("test-modify-user");
        CasdoorResponse response = casdoorUserService.addUser(user);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        user.setDisplayName("test-display-name");
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

        List<CasdoorRole> roles = (List<CasdoorRole>) result.get("casdoorUsers");
        int data2 = (int) result.get("data2");

        assertTrue(!roles.isEmpty());
        assertTrue(data2 > 0);
    }

    @Test
    public void testGetGolbalUsers() throws IOException {
        List<CasdoorUser> globalUsers = casdoorUserService.getGlobalUsers();
        assertNotNull(globalUsers);
    }

}
