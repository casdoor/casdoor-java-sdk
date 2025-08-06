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

import org.casbin.casdoor.entity.Invitation;
import org.casbin.casdoor.service.InvitationService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InvitationTest {

    private final InvitationService invitationService = new InvitationService(TestDefaultConfig.InitConfig());

    @Test
    public void testInvitation() {
        String name = TestDefaultConfig.getRandomName("Invitation");
        String code = TestDefaultConfig.getRandomName("Code");

        // Add a new object
        Invitation invitation = new Invitation(
        "built-in",
        name,
        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        "display-name",
        code,
        false,
        10,
        "app-built-in",
        "123456"
        );


        assertDoesNotThrow(() -> invitationService.addInvitation(invitation));

        // Get all objects, check if our added object is inside the list
        List<Invitation> invitations;
        try {
            invitations = invitationService.getInvitations();
        } catch (Exception e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        boolean found = invitations.stream().anyMatch(item -> item.name.equals(name));
        assertTrue(found, "Added object not found in list");

        // Get the object
        Invitation retrievedInvitation;
        try {
            retrievedInvitation = invitationService.getInvitation(name);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(name, retrievedInvitation.name, "Retrieved object does not match added object");

        // Update the object
        String updatedDisplayName = "Updated Invitation";
        retrievedInvitation.displayName = updatedDisplayName;
        assertDoesNotThrow(() -> invitationService.updateInvitation(retrievedInvitation));

        // Validate the update
        Invitation updatedInvitation;
        try {
            updatedInvitation = invitationService.getInvitation(name);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedDisplayName, updatedInvitation.displayName, "Failed to update object, displayName mismatch");

        // Delete the object
        assertDoesNotThrow(() -> invitationService.deleteInvitation(invitation));

        // Validate the deletion
        Invitation deletedInvitation;
        try {
            deletedInvitation = invitationService.getInvitation(name);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
            return;
        }
        assertNull(deletedInvitation, "Failed to delete object, it's still retrievable");
    }
}