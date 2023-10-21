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


import org.casbin.casdoor.entity.Organization;
import org.casbin.casdoor.service.OrganizationService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrganizationTest {

    private OrganizationService organizationService = new OrganizationService(
            TestDefaultConfig.InitConfig());

    @Test
    public void testOrganization() {
        String name = TestDefaultConfig.getRandomName("Organization");

        // Add a new object
        Organization organization = new Organization(
                "admin",
                name,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                name,
                "https://example.com",
                "plain",
                new String[]{"AtLeast6"},
                new String[]{"US", "ES", "FR", "DE", "GB", "CN", "JP", "KR", "VN", "ID", "SG", "IN"},
                new String[]{},
                new String[]{"en", "zh", "es", "fr", "de", "id", "ja", "ko", "ru", "vi", "pt"},
                2000,
                false,
                false
        );
        assertDoesNotThrow(() -> organizationService.addOrganization(organization));

        // Get all objects, check if our added object is inside the list
//        List<Organization> organizations;
//        try {
//            organizations = organizationService.getOrganizations();
//        } catch (Exception e) {
//            fail("Failed to get objects: " + e.getMessage());
//            return;
//        }
//
//        boolean found = organizations.stream().anyMatch(item -> item.name.equals(name));
//        assertTrue(found, "Added object not found in list");

        // Get the object
        Organization retrievedOrganization;
        try {
            retrievedOrganization = organizationService.getOrganization(name);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(name, retrievedOrganization.name, "Retrieved object does not match added object");

        // Update the object
        String updatedDisplayName = "Updated Casdoor Website";
        retrievedOrganization.displayName = updatedDisplayName;
        assertDoesNotThrow(() -> organizationService.updateOrganization(retrievedOrganization));

        // Validate the update
        Organization updatedOrganization;
        try {
            updatedOrganization = organizationService.getOrganization(name);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedDisplayName, updatedOrganization.displayName, "Failed to update object, displayName mismatch");

        // Delete the object
        assertDoesNotThrow(() -> organizationService.deleteOrganization(organization));

        // Validate the deletion
        Organization deletedOrganization;
        try {
            deletedOrganization = organizationService.getOrganization(name);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
            return;
        }
        assertNull(deletedOrganization, "Failed to delete object, it's still retrievable");
    }

}
