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

import org.casbin.casdoor.entity.Provier;
import org.casbin.casdoor.service.ProviderService;
import org.casbin.casdoor.support.ConfigFactory;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class ProvierServiceTest {
    private ProviderService providerService;

    @Before
    public void init() {
        providerService = new ProviderService(ConfigFactory.getConfig());
    }

    @Test
    public void testGetCasdoorProvier() throws IOException {
        Provier provier = providerService.getProvider("provider_captcha_default");
        assertNotNull(provier);
    }

    @Test
    public void testGetCasdoorProviers() throws IOException {
        List<Provier> proviers = providerService.getProviders();
        assertNotNull(proviers);
    }

    @Test
    public void testGetPaginationCasdoorProviers() throws IOException {
        Map<String, Object> paginationProviders = providerService.getPaginationProviders(1, 10, null);
        assertNotNull(paginationProviders);
    }

    @Test
    public void testModifyCasdoorProvier() throws IOException {
        Provier provier = new Provier();
        provier.owner = "test-owner";
        provier.name = "test-provier-name";

        CasdoorResponse response = providerService.addProvider(provier);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());


        provier.owner = "test-updated-owner";
        response = providerService.updateProvider(provier);
        Assert.assertEquals("ok", response.getStatus());

        response = providerService.deleteProvider(provier);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());
    }
}
