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
import org.casbin.casdoor.service.RoleService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class RoleTest {

    private final RoleService roleService = new RoleService(
            TestDefaultConfig.InitConfig());

    @Test
    public void testRole() {
        String name = TestDefaultConfig.getRandomName("Role");

        // Add a new object
        Role role = new Role(
                "admin",
                name,
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                name,
                "Casdoor Website"
        );
        assertDoesNotThrow(() -> roleService.addRole(role));

        // Get all objects, check if our added object is inside the list
        List<Role> roles;
        try {
            roles = roleService.getRoles();
        } catch (Exception e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }
        boolean found = roles.stream().anyMatch(item -> item.name.equals(name));
        assertTrue(found, "Added object not found in list");

        // Get the object
        Role retrievedRole;
        try {
            retrievedRole = roleService.getRole(name);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(name, retrievedRole.name, "Retrieved object does not match added object");

        // Update the object
        String updatedDescription = "Updated Casdoor Website";
        retrievedRole.description = updatedDescription;
        assertDoesNotThrow(() -> roleService.updateRole(retrievedRole));

        // Validate the update
        Role updatedRole;
        try {
            updatedRole = roleService.getRole(name);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedDescription, updatedRole.description, "Failed to update object, description mismatch");

        // Delete the object
        assertDoesNotThrow(() -> roleService.deleteRole(role));

        // Validate the deletion
        Role deletedRole;
        try {
            deletedRole = roleService.getRole(name);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
            return;
        }
        assertNull(deletedRole, "Failed to delete object, it's still retrievable");
    }
}


