// Copyright 2025 The Casdoor Authors. All Rights Reserved.
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
import org.casbin.casdoor.entity.Ldap;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.LdapOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;
import java.util.List;

public class LdapService extends Service {

    public LdapService(Config config) {
        super(config);
    }

    public List<Ldap> getLdaps() throws IOException {
        CasdoorResponse<List<Ldap>, Object> response = doGet("get-ldaps",
                Map.of("owner", "admin"), new TypeReference<CasdoorResponse<List<Ldap>, Object>>() {
                });
        return response.getData();
    }

    public Ldap getLdap(String id) throws IOException {
        CasdoorResponse<Ldap, Object> response = doGet("get-ldap",
                Map.of("id", "admin/" + id), new TypeReference<CasdoorResponse<Ldap, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> addLdap(Ldap ldap) throws IOException {
        return modifyLdap(LdapOperations.ADD_LDAP, ldap, null);
    }

    public CasdoorResponse<String, Object> deleteLdap(Ldap ldap) throws IOException {
        return modifyLdap(LdapOperations.DELETE_LDAP, ldap, null);
    }

    public CasdoorResponse<String, Object> updateLdap(Ldap ldap) throws IOException {
        return modifyLdap(LdapOperations.UPDATE_LDAP, ldap, null);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyLdap(LdapOperations method, Ldap ldap, java.util.Map<String, String> queryMap) throws IOException {
        String id = ldap.owner + "/" + ldap.id;
        ldap.owner = config.organizationName;
        String payload = objectMapper.writeValueAsString(ldap);

        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}