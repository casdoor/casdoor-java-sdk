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

package org.casbin.casdoor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.Organization;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.OrganizationOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;
import java.util.List;

public class OrganizationService extends Service {
    public OrganizationService(Config config) {
        super(config);
    }

    public Organization getOrganization(String name) throws IOException {
        CasdoorResponse<Organization, Object> response = doGet("get-organization",
                Map.of("id", "admin/" + name), new TypeReference<CasdoorResponse<Organization, Object>>() {
                });
        return response.getData();
    }

    public List<Organization> getOrganizations() throws IOException {
        CasdoorResponse<List<Organization>, Object> resp = doGet("get-organizations",
                Map.of("owner", getConfig().getOrganizationName()), new TypeReference<CasdoorResponse<List<Organization>, Object>>() {
                });
        return resp.getData();
    }

    public List<Organization> getOrganizationNames() throws IOException {
        CasdoorResponse<List<Organization>, Object> response = doGet("get-organization-names",
                Map.of("owner", getConfig().getOrganizationName()), new TypeReference<CasdoorResponse<List<Organization>, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> updateOrganization(Organization organization) throws IOException {
        organization.owner = "admin";
        return modifyOrganization(OrganizationOperations.UPDATE_ORGANIZATION, organization);
    }

    public CasdoorResponse<String, Object> addOrganization(Organization organization) throws IOException {
        organization.owner = "admin";
        return modifyOrganization(OrganizationOperations.ADD_ORGANIZATION, organization);
    }

    public CasdoorResponse<String, Object> deleteOrganization(Organization organization) throws IOException {
        organization.owner = "admin";
        return modifyOrganization(OrganizationOperations.DELETE_ORGANIZATION, organization);
    }

    /**
     * modifyOrganization is an encapsulation of organization CUD(Create, Update, Delete) operations.
     * Possible actions are `add-organization`, `update-organization`, `delete-organization`.
     */
    private <T1, T2> CasdoorResponse<T1, T2> modifyOrganization(OrganizationOperations method, Organization organization) throws IOException {
        String id = organization.owner + "/" + organization.name;
        String payload = getObjectMapper().writeValueAsString(organization);
        return doPost(method.getOperation(),
                Map.of("id", id), payload
                , new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}
