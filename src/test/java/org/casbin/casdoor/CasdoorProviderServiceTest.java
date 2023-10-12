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

import org.casbin.casdoor.entity.CasdoorProvier;
import org.casbin.casdoor.service.CasdoorProviderService;
import org.casbin.casdoor.support.ConfigFactory;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class CasdoorProviderServiceTest {
    private CasdoorProviderService casdoorProviderService;

    @Before
    public void init() {
        casdoorProviderService = new CasdoorProviderService(ConfigFactory.getConfig());
    }

    @Test
    public void testGetCasdoorProvier() throws IOException {
        CasdoorProvier provider = casdoorProviderService.getProvider("provider_captcha_default");
        assertNotNull(provider);
    }

    @Test
    public void testGetCasdoorProviers() throws IOException {
        List<CasdoorProvier> providers = casdoorProviderService.getProviders();
        assertNotNull(providers);
    }

    @Test
    public void testGetPaginationCasdoorProviers() throws IOException {
        Map<String, Object> paginationProviders = casdoorProviderService.getPaginationProviders(1, 10, null);
        assertNotNull(paginationProviders);
    }

    @Test
    public void testModifyCasdoorProvier() throws IOException {
        CasdoorProvier provider = new CasdoorProvier();
        provider.setOwner("test-owner");
        provider.setName("test-provider-name");

        CasdoorResponse response = casdoorProviderService.addProvider(provider);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        provider.setOwner("test-updated-owner");
        response = casdoorProviderService.updateProvider(provider);
        Assert.assertEquals("ok", response.getStatus());

        response = casdoorProviderService.deleteProvider(provider);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());
    }
}
