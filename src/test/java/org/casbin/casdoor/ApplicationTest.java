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

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

import org.casbin.casdoor.entity.Application;
import org.casbin.casdoor.service.ApplicationService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    private final ApplicationService applicationService = new ApplicationService(TestDefaultConfig.InitConfig());

    @Test
    public void testApplication() {
        String name = TestDefaultConfig.getRandomName("application");

        // Add a new object
        Application application = new Application(
                "Admin",
                name,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                name,
                "https://cdn.casbin.org/img/casdoor-logo_1185x256.png",
                "https://casdoor.org",
                "Casdoor Website",
                "casbin"
        );
        assertDoesNotThrow(() -> applicationService.addApplication(application));

        // Get all objects, check if our added object is inside the list
        List<Application> applications;
        try {
            applications = applicationService.getApplications();
        } catch (Exception e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        boolean found = applications.stream().anyMatch(item -> item.name.equals(name));
        assertTrue(found, "Added object not found in list");

        // Get the object
        Application retrievedApplication;
        try {
            retrievedApplication = applicationService.getApplication(name);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(name, retrievedApplication.name, "Retrieved object does not match added object");

        // Update the object
        String updatedDescription = "Updated Casdoor Website";
        retrievedApplication.description = updatedDescription;
        assertDoesNotThrow(() -> applicationService.updateApplication(retrievedApplication));

        // Validate the update
        Application updatedApplication;
        try {
            updatedApplication = applicationService.getApplication(name);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedDescription, updatedApplication.description, "Failed to update object, description mismatch");

        // Delete the object
        assertDoesNotThrow(() -> applicationService.deleteApplication(name));

        // Validate the deletion
        Application deletedApplication;
        try {
            deletedApplication = applicationService.getApplication(name);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
            return;
        }
        assertNull(deletedApplication, "Failed to delete object, it's still retrievable");
    }
}
