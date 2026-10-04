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

package org.casbin.casdoor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.Invitation;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.InvitationOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;

import jakarta.annotation.Nullable;

import java.io.IOException;
import java.util.List;

public class InvitationService extends Service {

    public InvitationService(Config config) {
        super(config);
    }

    public List<Invitation> getInvitations() throws IOException {
        CasdoorResponse<List<Invitation>, Object> response = doGet("get-invitations",
                Map.of("owner", config.organizationName), new TypeReference<CasdoorResponse<List<Invitation>, Object>>() {
                });
        return response.getData();
    }

    public java.util.Map<String, Object> getPaginationInvitations(int p, int pageSize, @Nullable java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<Invitation[], Object> casdoorResponse = doGet("get-invitations",
                Map.mergeMap(Map.of("owner", config.organizationName,
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<Invitation[], Object>>() {
                });

        return Map.of("casdoorInvitations", casdoorResponse.getData(), "data2", casdoorResponse.getData2());
    }

    public Invitation getInvitation(String name) throws IOException {
        CasdoorResponse<Invitation, Object> response = doGet("get-invitation",
                Map.of("id", getId(name)), new TypeReference<CasdoorResponse<Invitation, Object>>() {
                });
        return response.getData();
    }

    /**
     * Gets the invitation of the invitation code for the application.
     */
    public Invitation getInvitationInfo(String code, String applicationName) throws IOException {
        CasdoorResponse<Invitation, Object> response = doGet("get-invitation-info",
                Map.of("applicationId", "admin/" + applicationName, "code", code),
                new TypeReference<CasdoorResponse<Invitation, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> updateInvitationForColumns(Invitation invitation, String... columns) throws IOException {
        return modifyInvitation(InvitationOperations.UPDATE_INVITATION, invitation, Map.of("columns", joinColumns(columns)));
    }

    public CasdoorResponse<String, Object> addInvitation(Invitation invitation) throws IOException {
        return modifyInvitation(InvitationOperations.ADD_INVITATION, invitation, null);
    }

    public CasdoorResponse<String, Object> deleteInvitation(Invitation invitation) throws IOException {
        return modifyInvitation(InvitationOperations.DELETE_INVITATION, invitation, null);
    }

    public CasdoorResponse<String, Object> updateInvitation(Invitation invitation) throws IOException {
        return modifyInvitation(InvitationOperations.UPDATE_INVITATION, invitation, null);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyInvitation(InvitationOperations method, Invitation invitation, java.util.Map<String, String> queryMap) throws IOException {
        invitation.owner = getOwner(invitation.owner, config.organizationName);
        String id = invitation.owner + "/" + invitation.name;
        String payload = objectMapper.writeValueAsString(invitation);

        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}