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

import org.casbin.casdoor.entity.Application;
import org.casbin.casdoor.service.ApplicationService;
import org.casbin.casdoor.support.ConfigFactory;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class ApplicationServiceTest {
    private ApplicationService applicationService;

    @Before
    public void init() {
        applicationService = new ApplicationService(ConfigFactory.getConfig());
    }

    @Test
    public void testGetApplication() throws IOException {
        Application application = applicationService.getApplication("app-built-in");
        assertNotNull(application);

    }

    @Test
    public void testGetApplications() throws IOException {
        List<Application> applications = applicationService.getApplications();
        assertNotNull(applications);

    }

    @Test
    public void testGetOrganizationApplications() throws IOException {
        List<Application> organizationApplications = applicationService.getOrganizationApplications();
        assertNotNull(organizationApplications);

    }

    @Test
    public void testModifyApplication() throws IOException {

        Application application = new Application();
        application.owner = "admin";
        application.name = "test-modify-application";
        application.displayName = "init-display-name";
        CasdoorResponse response = applicationService.addApplication(application);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        application.displayName = "test-display-name";
        response = applicationService.updateApplication(application);
        Assert.assertEquals("ok", response.getStatus());

        response = applicationService.deleteApplication("test-modify-application");
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());
    }
}