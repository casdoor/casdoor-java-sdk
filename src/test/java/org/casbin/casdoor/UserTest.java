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

import org.casbin.casdoor.entity.User;
import org.casbin.casdoor.service.UserService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private final UserService userService = new UserService(TestDefaultConfig.InitConfig());

    @Test
    public void testUser() {
        String name = TestDefaultConfig.getRandomName("User");

        // Add a new object
        User user = new User(
                "admin",
                name,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                name);

        assertDoesNotThrow(() -> userService.addUser(user));

        // Get all objects, check if our added object is inside the list
        List<User> users;
        try {
            users = userService.getUsers();
        } catch (Exception e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        boolean found = users.stream().anyMatch(item -> item.name.equals(name));
        assertTrue(found, "Added object not found in list");

        // Get the object
        User retrievedUser;
        try {
            retrievedUser = userService.getUser(name);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(name, retrievedUser.name, "Retrieved object does not match added object");

        // Update the object
        String updatedDisplayName = "Updated Casdoor Website";
        retrievedUser.displayName = updatedDisplayName;
        assertDoesNotThrow(() -> userService.updateUser(retrievedUser));

        // Validate the update
        User updatedUser;
        try {
            updatedUser = userService.getUser(name);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedDisplayName, updatedUser.displayName, "Failed to update object, displayName mismatch");

        // Delete the object
        assertDoesNotThrow(() -> userService.deleteUser(user));

        // Validate the deletion
        User deletedUser;
        try {
            deletedUser = userService.getUser(name);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
            return;
        }
        assertNull(deletedUser, "Failed to delete object, it's still retrievable");

    }

}
