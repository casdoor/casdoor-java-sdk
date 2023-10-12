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


import org.casbin.casdoor.entity.CasdoorOrganization;
import org.casbin.casdoor.service.CasdoorOrganizationService;
import org.casbin.casdoor.support.ConfigFactory;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;


public class CasdoorOrganizationServiceTest {
    private CasdoorOrganizationService casdoorOrganizationService;
    @Before
    public void init(){
        casdoorOrganizationService = new CasdoorOrganizationService(ConfigFactory.getConfig());
    }

    @Test
    public void testGetOrganization() throws IOException {
        CasdoorOrganization organization = casdoorOrganizationService.getOrganization("built-in");
        assertNotNull(organization);

    }

    @Test
    public void testGetOrganizations() throws IOException {
        List<CasdoorOrganization> organizations = casdoorOrganizationService.getOrganizations();
        assertNotNull(organizations);
    }

    @Test
    public void testGetOrganizationNames() throws IOException {
        List<CasdoorOrganization> organizationNames = casdoorOrganizationService.getOrganizationNames();
        assertNotNull(organizationNames);

    }
    @Test
    public void testModifyOrganization() throws IOException {

        CasdoorOrganization organization = new CasdoorOrganization();
        organization.setOwner("built-in");
        organization.setName("test-modify-organization");
        CasdoorResponse response = casdoorOrganizationService.addOrganization(organization);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        organization.setDisplayName("test-display-name");
        response = casdoorOrganizationService.updateOrganization(organization);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        response = casdoorOrganizationService.deleteOrganization(organization);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());
    }
}