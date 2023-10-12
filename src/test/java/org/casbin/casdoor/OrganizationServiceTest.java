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
package org.casbin.casdoor;


import org.casbin.casdoor.entity.Organization;
import org.casbin.casdoor.service.OrganizationService;
import org.casbin.casdoor.support.ConfigFactory;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;


public class OrganizationServiceTest {
    private OrganizationService organizationService;
    @Before
    public void init(){
        organizationService = new OrganizationService(ConfigFactory.getConfig());
    }

    @Test
    public void testGetOrganization() throws IOException {
        Organization organization = organizationService.getOrganization("built-in");
        assertNotNull(organization);

    }

    @Test
    public void testGetOrganizations() throws IOException {
        List<Organization> organizations = organizationService.getOrganizations();
        assertNotNull(organizations);
    }

    @Test
    public void testGetOrganizationNames() throws IOException {
        List<Organization> organizationNames = organizationService.getOrganizationNames();
        assertNotNull(organizationNames);

    }
    @Test
    public void testModifyOrganization() throws IOException {

        Organization organization = new Organization();
        organization.setOwner("built-in");
        organization.setName("test-modify-organization");
        CasdoorResponse response = organizationService.addOrganization(organization);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        organization.setDisplayName("test-display-name");
        response = organizationService.updateOrganization(organization);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        response = organizationService.deleteOrganization(organization);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());
    }
}