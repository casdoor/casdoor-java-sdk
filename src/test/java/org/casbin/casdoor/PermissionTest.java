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

import org.casbin.casdoor.entity.Permission;
import org.casbin.casdoor.service.PermissionService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PermissionTest {

    private final PermissionService permissionService = new PermissionService(
            TestDefaultConfig.InitConfig());

    @Test
    public void testPermission() throws IOException {
        String name = TestDefaultConfig.getRandomName("Permission");

        // Add a new object
        Permission permission = new Permission(
                "casbin",
                name,
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                name,
                "Casdoor Website",
                new String[]{"casbin/*"},
                new String[]{},
                new String[]{},
                "user-model-built-in",
                "Application",
                new String[]{"app-casbin"},
                new String[]{"Read", "Write"},
                "Allow",
                true
        );
        assertDoesNotThrow(() -> permissionService.addPermission(permission));

        // Get all objects, check if our added object is inside the list
        List<Permission> permissions;
        try {
            permissions = permissionService.getPermissions();
        } catch (Exception e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        boolean found = permissions.stream().anyMatch(item -> item.name.equals(name));
        assertTrue(found, "Added object not found in list");

        // Get the object
        Permission retrievedPermission;
        try {
            retrievedPermission = permissionService.getPermission(name);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(name, retrievedPermission.name, "Retrieved object does not match added object");

        // Update the object
        String updatedDescription = "Updated Casdoor Website";
        retrievedPermission.description = updatedDescription;
        assertDoesNotThrow(() -> permissionService.updatePermission(retrievedPermission));

        // Validate the update
        Permission updatedPermission;
        try {
            updatedPermission = permissionService.getPermission(name);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedDescription, updatedPermission.description, "Failed to update object, description mismatch");

        // Delete the object
        try {
            permissionService.deletePermission(permission);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
        }

        // Validate the deletion
        Permission deletedPermission = null;
        try {
            deletedPermission = permissionService.getPermission(name);
        } catch (Exception e) {
            fail("Failed to delete object, it's still retrievable: " + e.getMessage());
        }
        if (deletedPermission != null) {
            fail("Failed to delete object, it's still retrievable");
        }
    }
}
