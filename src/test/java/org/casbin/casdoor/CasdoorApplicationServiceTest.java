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

import org.casbin.casdoor.entity.CasdoorApplication;
import org.casbin.casdoor.service.CasdoorApplicationService;
import org.casbin.casdoor.support.ConfigFactory;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class CasdoorApplicationServiceTest {
    private CasdoorApplicationService casdoorApplicationService;

    @Before
    public void init() {
        casdoorApplicationService = new CasdoorApplicationService(ConfigFactory.getConfig());
    }

    @Test
    public void testGetApplication() throws IOException {
        CasdoorApplication application = casdoorApplicationService.getApplication("app-built-in");
        assertNotNull(application);

    }

    @Test
    public void testGetApplications() throws IOException {
        List<CasdoorApplication> applications = casdoorApplicationService.getApplications();
        assertNotNull(applications);

    }

    @Test
    public void testGetOrganizationApplications() throws IOException {
        List<CasdoorApplication> organizationApplications = casdoorApplicationService.getOrganizationApplications();
        assertNotNull(organizationApplications);

    }

    @Test
    public void testModifyApplication() throws IOException {

        CasdoorApplication application = new CasdoorApplication();
        application.setOwner("admin");
        application.setName("test-modify-application");
        application.setDisplayName("init-display-name");
        CasdoorResponse response = casdoorApplicationService.addApplication(application);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        application.setDisplayName("test-display-name");
       response = casdoorApplicationService.updateApplication(application);
        Assert.assertEquals("ok", response.getStatus());

        response = casdoorApplicationService.deleteApplication("test-modify-application");
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());
    }
}