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
import org.casbin.casdoor.entity.Cert;
import org.casbin.casdoor.entity.Organization;
import org.casbin.casdoor.util.CertOperations;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;
import java.util.List;

public class CertService extends Service {

    public CertService(Config config) {
        super(config);
    }

    public List<Cert> getCerts() throws IOException {
        CasdoorResponse<List<Cert>, Object> response = doGet("get-certs",
                Map.of("owner", getConfig().getOrganizationName()),
                new TypeReference<CasdoorResponse<List<Cert>, Object>>() {});

        return response.getData();
    }

    public Cert getCert(String name) throws IOException {
        CasdoorResponse<Cert, Object> response = doGet("get-cert",
                Map.of("id", getConfig().getOrganizationName() + "/" + name), new TypeReference<CasdoorResponse<Cert, Object>>() {});
        return response.getData();
    }

    public List<Cert> getGlobalCerts() throws IOException {
        CasdoorResponse<List<Cert>, Object> response = doGet("get-globle-certs",null,
                new TypeReference<CasdoorResponse<List<Cert>, Object>>() {});

        return response.getData();
    }

    public CasdoorResponse<String, Object> updateCert(Cert cert) throws IOException {
        return modifyCert(CertOperations.UPDATE_CERT, cert);
    }

    public CasdoorResponse<String, Object> addCert(Cert cert) throws IOException {
        return modifyCert(CertOperations.ADD_CERT, cert);
    }

    public CasdoorResponse<String, Object> deleteCert(Cert cert) throws IOException {
        return modifyCert(CertOperations.DELETE_CERT, cert);
    }

    /**
     * modifyCert is an encapsulation of Cert CUD(Create, Update, Delete) operations.
     * Possible actions are `add-Cert`, `update-Cert`, `delete-Cert`.
     */
    private <T1, T2> CasdoorResponse<T1, T2> modifyCert(CertOperations method, Cert cert) throws IOException {
        String id = cert.owner + "/" + cert.name;
        cert.owner = getConfig().getOrganizationName();
        String payload = getObjectMapper().writeValueAsString(cert);
        return doPost(method.getOperation(),
                Map.of("id", id),
                payload, new TypeReference<CasdoorResponse<T1, T2>>() {});
    }

}