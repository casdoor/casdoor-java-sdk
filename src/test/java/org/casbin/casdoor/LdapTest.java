// Copyright 2025 The Casdoor Authors. All Rights Reserved.
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

import org.casbin.casdoor.entity.Ldap;
import org.casbin.casdoor.service.LdapService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LdapTest {

    private final LdapService ldapService = new LdapService(TestDefaultConfig.InitConfig());

    @Test
    public void testLdap() {
        String id = TestDefaultConfig.getRandomName("Ldap");

        // Add a new object using constructor
        Ldap ldap = new Ldap(
                "built-in",
                "Test LDAP Server",
                "localhost", 
                390,
                "admin", 
                "password", 
                "dc=example,dc=com", 
                0
        );
        ldap.id = id;
        assertDoesNotThrow(() -> ldapService.addLdap(ldap));

        // Get all objects, check if our added object is inside the list
        List<Ldap> ldaps;
        try {
            ldaps = ldapService.getLdaps();
        } catch (Exception e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        boolean found = ldaps.stream().anyMatch(item -> item.id.equals(id));
        assertTrue(found, "Added object not found in list");

        // Get the object
        Ldap retrievedLdap;
        try {
            retrievedLdap = ldapService.getLdap(id);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(id, retrievedLdap.id, "Retrieved object does not match added object");

        // Update the object
        String updatedServerName = "Updated Ldap";
        retrievedLdap.serverName = updatedServerName;
        assertDoesNotThrow(() -> ldapService.updateLdap(retrievedLdap));

        // Validate the update
        Ldap updatedLdap;
        try {
            updatedLdap = ldapService.getLdap(id);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedServerName, updatedLdap.serverName, "Failed to update object, serverName mismatch");

        // Delete the object
        assertDoesNotThrow(() -> ldapService.deleteLdap(ldap));

        // Validate the deletion
        Ldap deletedLdap;
        try {
            deletedLdap = ldapService.getLdap(id);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
            return;
        }
        assertNull(deletedLdap, "Failed to delete object, it's still retrievable");
    }
}
