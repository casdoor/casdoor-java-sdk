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
import org.casbin.casdoor.entity.CasdoorCert;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;
import java.util.List;

/**
 * @Description: 描述信息
 * @author: Dizzy
 */
public class CasdoorCertService extends CasdoorService{

    public CasdoorCertService(CasdoorConfig casdoorConfig) {
        super(casdoorConfig);
    }

    public List<CasdoorCert> getCerts() throws IOException {
        CasdoorResponse<List<CasdoorCert>, Object> response = doGet("get-certs",
                Map.of("owner", casdoorConfig.getOrganizationName()),
                new TypeReference<CasdoorResponse<List<CasdoorCert>, Object>>() {});

        return response.getData();
    }

    public List<CasdoorCert> getGlobalCerts() throws IOException {
        CasdoorResponse<List<CasdoorCert>, Object> response = doGet("get-globle-certs",null,
                new TypeReference<CasdoorResponse<List<CasdoorCert>, Object>>() {});

        return response.getData();
    }

}