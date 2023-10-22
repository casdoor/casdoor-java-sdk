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

import org.casbin.casdoor.entity.Subscription;
import org.casbin.casdoor.service.SubscriptionService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class SubscriptionTest {
    private final SubscriptionService subscriptionService = new SubscriptionService(
            TestDefaultConfig.InitConfig());

    @Test
    public void testSubscription() {
        String name = TestDefaultConfig.getRandomName("Subscription");

        // Add a new object
        Subscription subscription = new Subscription(
                "admin",
                name,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                name,
                "Casdoor Website"
        );
        assertDoesNotThrow(() -> subscriptionService.addSubscription(subscription));

        // Get all objects, check if our added object is inside the list
        List<Subscription> subscriptions;
        try {
            subscriptions = subscriptionService.getSubscriptions();
        } catch (Exception e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        boolean found = subscriptions.stream().anyMatch(item -> item.name.equals(name));
        assertTrue(found, "Added object not found in list");

        // Get the object
        Subscription retrievedSubscription;
        try {
            retrievedSubscription = subscriptionService.getSubscription(name);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(name, retrievedSubscription.name, "Retrieved object does not match added object");

        // Update the object
        String updatedDescription = "Updated Casdoor Website";
        retrievedSubscription.description = updatedDescription;
        assertDoesNotThrow(() -> subscriptionService.updateSubscription(retrievedSubscription));

        // Validate the update
        Subscription updatedSubscription;
        try {
            updatedSubscription = subscriptionService.getSubscription(name);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedDescription, updatedSubscription.description, "Failed to update object, description mismatch");

        // Delete the object
        assertDoesNotThrow(() -> subscriptionService.deleteSubscription(subscription));

        // Validate the deletion
        Subscription deletedSubscription;
        try {
            deletedSubscription = subscriptionService.getSubscription(name);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
            return;
        }
        assertNull(deletedSubscription, "Failed to delete object, it's still retrievable");
    }
}
