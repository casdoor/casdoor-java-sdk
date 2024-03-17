// Copyright 2024 The Casdoor Authors. All Rights Reserved.
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

import org.casbin.casdoor.entity.CasbinRule;
import org.casbin.casdoor.entity.Enforcer;
import org.casbin.casdoor.service.EnforcerService;
import org.casbin.casdoor.service.PolicyService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PolicyTest {

    private final EnforcerService enforcerService = new EnforcerService(TestDefaultConfig.InitConfig());

    private final PolicyService policyService = new PolicyService(TestDefaultConfig.InitConfig());

    @Test
    public void testPolicy() {
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

        CasbinRule policy = new CasbinRule(0,"p","1","2","4","","","","");
        assertDoesNotThrow(() -> policyService.addPolicy(enforcer, policy));

        // Get all objects, check if our added object is inside the list
        List<CasbinRule> policies;
        try {
            policies = policyService.getPolicies(name,"");
        } catch (IOException e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        boolean found = policies.stream().anyMatch(item -> item.Ptype.equals("p") && item.V2.equals("4"));
        assertTrue(found, "Added object not found in list");

        // Update the object
        CasbinRule newpolicy = new CasbinRule(0,"p","1","2","5","","","","");
        assertDoesNotThrow(() -> policyService.updatePolicy(enforcer, policy, newpolicy));

        // Validate the update
        try {
            policies = policyService.getPolicies(name,"");
        } catch (IOException e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        found = policies.stream().anyMatch(item -> item.Ptype.equals("p") && item.V2.equals("5"));
        assertTrue(found, "Updated object not found in list");

        // Delete the object
        assertDoesNotThrow(() -> policyService.removePolicy(enforcer, newpolicy));
        // Validate the deletion
        try {
            policies = policyService.getPolicies(name,"");
        } catch (IOException e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }
        found = policies.stream().anyMatch(item -> item.Ptype.equals("p") && item.V2.equals("5"));
        assertFalse(found, "Deleted object not found in list");
    }
}
