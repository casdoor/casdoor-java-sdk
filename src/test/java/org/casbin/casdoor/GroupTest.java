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

import org.casbin.casdoor.entity.Group;
import org.casbin.casdoor.service.GroupService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupTest {
    private final GroupService groupService = new GroupService(TestDefaultConfig.InitConfig());

    @Test
    public void testGroup() {
        String name = TestDefaultConfig.getRandomName("group");

        // Add a new object
        Group group = new Group(
                "admin",
                name,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                name
        );
        assertDoesNotThrow(() -> groupService.addGroup(group));

        // Get all objects, check if our added object is inside the list
        List<Group> groups;
        try {
            groups = groupService.getGroups();
        } catch (Exception e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        boolean found = groups.stream().anyMatch(item -> item.name.equals(name));
        assertTrue(found, "Added object not found in list");

        // Get the object
        Group retrievedGroup;
        try {
            retrievedGroup = groupService.getGroup(name);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(name, retrievedGroup.name, "Retrieved object does not match added object");

        // Update the object
        String updatedDisplayName = "Updated Casdoor Website";
        retrievedGroup.displayName = updatedDisplayName;
        assertDoesNotThrow(() -> groupService.updateGroup(retrievedGroup));

        // Validate the update
        Group updatedGroup;
        try {
            updatedGroup = groupService.getGroup(name);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedDisplayName, updatedGroup.displayName, "Failed to update object, displayName mismatch");

        // Delete the object
        assertDoesNotThrow(() -> groupService.deleteGroup(name));

        // Validate the deletion
        Group deletedGroup;
        try {
            deletedGroup = groupService.getGroup(name);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
            return;
        }
        assertNull(deletedGroup, "Failed to delete object, it's still retrievable");
    }
}