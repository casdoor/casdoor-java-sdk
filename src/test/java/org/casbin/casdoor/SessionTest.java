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

import org.casbin.casdoor.entity.Session;
import org.casbin.casdoor.service.SessionService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SessionTest {

        private final SessionService sessionService = new SessionService(
                TestDefaultConfig.InitConfig());

        @Test
        public void testSession() {
            String name = TestDefaultConfig.getRandomName("Session");

            // Add a new object
            Session session = new Session(
                    "casbin",
                    name,
                    "app-built-in",
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    new String[]{});

            assertDoesNotThrow(() -> sessionService.addSession(session));

            // Get all objects, check if our added object is inside the list
            List<Session> sessions;
            try {
                sessions = sessionService.getSessions();
            } catch (Exception e) {
                fail("Failed to get objects: " + e.getMessage());
                return;
            }

            boolean found = sessions.stream().anyMatch(item -> item.name.equals(name));
            assertTrue(found, "Added object not found in list");

            // Get the object
            Session retrievedSession;

            try {
                retrievedSession = sessionService.getSession(name,session.application);
            } catch (Exception e) {
                fail("Failed to get object: " + e.getMessage());
                return;
            }
            assertEquals(name, retrievedSession.name, "Retrieved object does not match added object");

            // Update the object
            String updateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            session.createdTime = updateTime;
            assertDoesNotThrow(() -> sessionService.updateSession(session));

            // Validate the update
            Session updatedSession;

            try {
                updatedSession = sessionService.getSession(name,session.application);
            } catch (Exception e) {
                fail("Failed to get updated object: " + e.getMessage());
                return;
            }
            assertEquals(updateTime, updatedSession.createdTime, "Failed to update object, createdTime mismatch");

            // Delete the object
            assertDoesNotThrow(() -> sessionService.deleteSession(session));

            // Validate the deletion
            Session deletedSession;
            try {
                deletedSession = sessionService.getSession(name,session.application);
            } catch (Exception e) {
                fail("Failed to delete object: " + e.getMessage());
                return;
            }
            assertNull(deletedSession, "Failed to delete object, it's still retrievable");
    }

}
