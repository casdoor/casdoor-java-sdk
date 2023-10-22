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

import org.casbin.casdoor.entity.Enforcer;
import org.casbin.casdoor.service.EnforcerService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EnforcerTest {

    private final EnforcerService enforcerService = new EnforcerService(TestDefaultConfig.InitConfig());

    @Test
    public void testEnforcer() {
        String name = TestDefaultConfig.getRandomName("Enforcer");

        // Add a new object
        Enforcer enforcer = new Enforcer(
                "admin",
                name,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                name,
                "built-in/user-model-built-in",
                "built-in/user-adapter-built-in",
                "Casdoor Website");
        assertDoesNotThrow(() -> enforcerService.addEnforcer(enforcer));

        // Get all objects, check if our added object is inside the list
        List<Enforcer> enforcers;
        try {
            enforcers = enforcerService.getEnforcers();
        } catch (IOException e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        boolean found = enforcers.stream().anyMatch(item -> item.name.equals(name));
        assertTrue(found, "Added object not found in list");

        // Get the object
        Enforcer retrievedEnforcer;
        try {
            retrievedEnforcer = enforcerService.getEnforcer(name);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(name, retrievedEnforcer.name, "Retrieved object does not match added object");

        // Update the object
        String updatedDescription = "Updated Casdoor Website";
        retrievedEnforcer.description = updatedDescription;
        assertDoesNotThrow(() -> enforcerService.updateEnforcer(retrievedEnforcer));

        // Validate the update
        Enforcer updatedEnforcer;
        try {
            updatedEnforcer = enforcerService.getEnforcer(name);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedDescription, updatedEnforcer.description, "Failed to update object, description mismatch");

        // Delete the object
        assertDoesNotThrow(() -> enforcerService.deleteEnforcer(enforcer));

        // Validate the deletion
        Enforcer deletedEnforcer;
        try {
            deletedEnforcer = enforcerService.getEnforcer(name);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
            return;
        }
        assertNull(deletedEnforcer, "Failed to delete object, it's still retrievable");
    }

}
