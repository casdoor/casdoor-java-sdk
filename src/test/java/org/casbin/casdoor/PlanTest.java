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

import org.casbin.casdoor.entity.Plan;
import org.casbin.casdoor.service.PlanService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlanTest {
    private final PlanService planService = new PlanService(
            TestDefaultConfig.InitConfig());

    @Test
    public void testPlan() {
        String name = TestDefaultConfig.getRandomName("Plan");

        // Add a new object
        Plan plan = new Plan(
                "admin",
                name,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                name,
                "casbin"
        );
        assertDoesNotThrow(() -> planService.addPlan(plan));

        // Get all objects, check if our added object is inside the list
        List<Plan> plans;
        try {
            plans = planService.getPlans();
        } catch (Exception e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        boolean found = plans.stream().anyMatch(item -> item.name.equals(name));
        assertTrue(found, "Added object not found in list");

        // Get the object
        Plan retrievedPlan;
        try {
            retrievedPlan = planService.getPlan(name);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(name, retrievedPlan.name, "Retrieved object does not match added object");

        // Update the object
        String updatedDescription = "Updated Casdoor Website";
        retrievedPlan.description = updatedDescription;
        assertDoesNotThrow(() -> planService.updatePlan(retrievedPlan));

        // Validate the update
        Plan updatedPlan;
        try {
            updatedPlan = planService.getPlan(name);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedDescription, updatedPlan.description, "Failed to update object, description mismatch");

        // Delete the object
        assertDoesNotThrow(() -> planService.deletePlan(retrievedPlan));

        // Validate the deletion
        Plan deletedPlan;
        try {
            deletedPlan = planService.getPlan(name);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
            return;
        }
        assertNull(deletedPlan, "Failed to delete object, it's still retrievable");
    }
}

