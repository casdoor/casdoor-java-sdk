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

import org.casbin.casdoor.entity.CasdoorCert;
import org.casbin.casdoor.service.CasdoorCertService;
import org.casbin.casdoor.support.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class CasdoorCertServiceTest{

    private CasdoorCertService casdoorCertService;
    @Before
    public void init(){
        casdoorCertService = new CasdoorCertService(ConfigFactory.getConfig());
    }

    @Test
    public void testGetCerts() throws IOException {
        List<CasdoorCert> admin = casdoorCertService.getCerts();
        assertNotNull(admin);

    }

    @Test
    public void testGetGlobalCert() throws IOException {
        List<CasdoorCert> globalCerts = casdoorCertService.getGlobalCerts();
        assertNotNull(globalCerts);

    }
}