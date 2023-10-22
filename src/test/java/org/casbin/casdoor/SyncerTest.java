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

import org.casbin.casdoor.entity.Syncer;
import org.casbin.casdoor.service.SyncerService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SyncerTest {
    private final SyncerService syncerService = new SyncerService(
            TestDefaultConfig.InitConfig());

    @Test
    public void testSyncer() {
        String name = TestDefaultConfig.getRandomName("Syncer");

        // Add a new object
        Syncer syncer = new Syncer(
                "admin",
                name,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                "casbin",
                "localhost",
                3306,
                "root",
                "123",
                "mysql",
                "syncer_db",
                "user-table",
                1
        );
        assertDoesNotThrow(() -> syncerService.addSyncer(syncer));

        // Get all objects, check if our added object is inside the list
        List<Syncer> syncers;
        try {
            syncers = syncerService.getSyncers();
        } catch (Exception e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        boolean found = syncers.stream().anyMatch(item -> item.name.equals(name));
        assertTrue(found, "Added object not found in list");

        // Get the object
        Syncer retrievedSyncer;
        try {
            retrievedSyncer = syncerService.getSyncer(name);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(name, retrievedSyncer.name, "Retrieved object does not match added object");

        // Update the object
        String updatedPassword = "123456";
        retrievedSyncer.password = updatedPassword;
        assertDoesNotThrow(() -> syncerService.updateSyncer(retrievedSyncer));

        // Validate the update
        Syncer updatedSyncer;
        try {
            updatedSyncer = syncerService.getSyncer(name);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedPassword, updatedSyncer.password, "Failed to update object, description mismatch");

        // Delete the object
        assertDoesNotThrow(() -> syncerService.deleteSyncer(syncer));

        // Validate the deletion
        Syncer deletedSyncer;
        try {
            deletedSyncer = syncerService.getSyncer(name);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
            return;
        }
        assertNull(deletedSyncer, "Failed to delete object, it's still retrievable");
    }
}
