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

import org.casbin.casdoor.entity.Provider;
import org.casbin.casdoor.service.ProviderService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProviderTest {

    private final ProviderService providerService = new ProviderService(
            TestDefaultConfig.InitConfig());

    @Test
    public void testProvider() {
        String name = TestDefaultConfig.getRandomName("application");

        // Add a new object
        Provider provider = new Provider(
                "admin",
                name,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                name,
                "Captcha",
                "Default"
        );
        assertDoesNotThrow(() -> providerService.addProvider(provider));

        // Get all objects, check if our added object is inside the list
        List<Provider> providers;
        try {
            providers = providerService.getProviders();
        } catch (Exception e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        boolean found = providers.stream().anyMatch(item -> item.name.equals(name));
        assertTrue(found, "Added object not found in list");

        // Get the object
        Provider retrievedProvider;
        try {
            retrievedProvider = providerService.getProvider(name);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(name, retrievedProvider.name, "Retrieved object does not match added object");

        // Update the object
        String updatedDisplayName = "Updated Casdoor Website";
        retrievedProvider.displayName = updatedDisplayName;
        assertDoesNotThrow(() -> providerService.updateProvider(retrievedProvider));

        // Validate the update
        Provider updatedProvider;
        try {
            updatedProvider = providerService.getProvider(name);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedDisplayName, updatedProvider.displayName, "Failed to update object, displayName mismatch");

        // Delete the object
        assertDoesNotThrow(() -> providerService.deleteProvider(provider));

        // Validate the deletion
        Provider deletedProvider;
        try {
            deletedProvider = providerService.getProvider(name);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
            return;
        }
        assertNull(deletedProvider, "Failed to delete object, it's still retrievable");
    }

}
