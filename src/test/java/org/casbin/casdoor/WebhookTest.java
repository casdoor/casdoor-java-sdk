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

import org.casbin.casdoor.entity.Webhook;
import org.casbin.casdoor.service.WebhookService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WebhookTest {
    private final WebhookService webhookService = new WebhookService(
            TestDefaultConfig.InitConfig());

    @Test
    public void testWebhook() { 
        String name = TestDefaultConfig.getRandomName("Webhook");

        // Add a new object
        Webhook webhook = new Webhook(
                "casbin",
                name,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                "casbin"
        );
        assertDoesNotThrow(() -> webhookService.addWebhook(webhook)); 

        // Get all objects, check if our added object is inside the list
        List<Webhook> webhooks; 
        try {
            webhooks = webhookService.getWebhooks(); 
        } catch (Exception e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        boolean found = webhooks.stream().anyMatch(item -> item.name.equals(name));
        assertTrue(found, "Added object not found in list");

        // Get the object
        Webhook retrievedWebhook;
        try {
            retrievedWebhook = webhookService.getWebhook(name);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(name, retrievedWebhook.name, "Retrieved object does not match added object");

        // Update the object
        String updatedOrganization = "Updated Casdoor Website";
        retrievedWebhook.organization = updatedOrganization;
        assertDoesNotThrow(() -> webhookService.updateWebhook(retrievedWebhook));

        // Validate the update
        Webhook updatedWebhook;
        try {
            updatedWebhook = webhookService.getWebhook(name);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedOrganization, updatedWebhook.organization, "Failed to update object, organization mismatch");

        // Delete the object
        assertDoesNotThrow(() -> webhookService.deleteWebhook(webhook));

        // Validate the deletion
        Webhook deletedWebhook;
        try {
            deletedWebhook = webhookService.getWebhook(name);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
            return;
        }
        assertNull(deletedWebhook, "Failed to delete object, it's still retrievable");
    }
}
