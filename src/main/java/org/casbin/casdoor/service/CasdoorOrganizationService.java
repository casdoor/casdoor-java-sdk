// Copyright 2023 The casbin Authors. All Rights Reserved.
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
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorOrganization;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.OrganizationOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;
import java.util.List;

public class CasdoorOrganizationService extends CasdoorService {
    public CasdoorOrganizationService(CasdoorConfig casdoorConfig) {
        super(casdoorConfig);
    }

    public CasdoorOrganization getOrganization(String name) throws IOException {
        CasdoorResponse<CasdoorOrganization, Object> response = doGet("get-organization",
                Map.of("id", casdoorConfig.getOrganizationName() + "/" + name), new TypeReference<CasdoorResponse<CasdoorOrganization, Object>>() {});
        return response.getData();
    }

    public List<CasdoorOrganization> getOrganizations() throws IOException {
        CasdoorResponse<List<CasdoorOrganization>, Object> resp = doGet("get-organizations",
                Map.of("owner", casdoorConfig.getOrganizationName()), new TypeReference<CasdoorResponse<List<CasdoorOrganization>, Object>>() {});
        return resp.getData();
    }

    public List<CasdoorOrganization> getOrganizationNames() throws IOException {
        CasdoorResponse<List<CasdoorOrganization>, Object> response = doGet("get-organization-names",
                Map.of("owner", casdoorConfig.getOrganizationName()), new TypeReference<CasdoorResponse<List<CasdoorOrganization>, Object>>() {});
        return response.getData();
    }

    public CasdoorResponse<String, Object> updateOrganization(CasdoorOrganization organization) throws IOException {
        return modifyOrganization(OrganizationOperations.UPDATE_ORGANIZATION, organization);
    }

    public CasdoorResponse<String, Object> addOrganization(CasdoorOrganization organization) throws IOException {
        return modifyOrganization(OrganizationOperations.ADD_ORGANIZATION, organization);
    }

    public CasdoorResponse<String, Object> deleteOrganization(CasdoorOrganization organization) throws IOException {
        return modifyOrganization(OrganizationOperations.DELETE_ORGANIZATION, organization);
    }

    /**
     * modifyOrganization is an encapsulation of organization CUD(Create, Update, Delete) operations.
     * Possible actions are `add-organization`, `update-organization`, `delete-organization`.
     */
    private <T1, T2> CasdoorResponse<T1, T2> modifyOrganization(OrganizationOperations method, CasdoorOrganization organization) throws IOException {
        return doPost(method.getOperation(),
                Map.of("id", organization.getOwner() + "/" + organization.getName()),
                objectMapper.writeValueAsString(organization), new TypeReference<CasdoorResponse<T1, T2>>() {});
    }
}
