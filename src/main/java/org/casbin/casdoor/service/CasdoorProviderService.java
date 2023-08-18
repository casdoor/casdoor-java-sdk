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
import org.casbin.casdoor.entity.CasdoorProvier;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.ProviderOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public class CasdoorProviderService extends CasdoorService {

    public CasdoorProviderService(CasdoorConfig casdoorConfig) {
        super(casdoorConfig);
    }

    public CasdoorProvier getProvider(String name) throws IOException {
        CasdoorResponse<CasdoorProvier, Object> response = doGet("get-provider",
                Map.of("id", casdoorConfig.getOrganizationName() + "/" + name),
                new com.fasterxml.jackson.core.type.TypeReference<CasdoorResponse<CasdoorProvier, Object>>() {});
        return response.getData();
    }
    public List<CasdoorProvier> getProviders() throws IOException {
        CasdoorResponse<List<CasdoorProvier>, Object> response = doGet("get-providers",
                Map.of("owner", casdoorConfig.getOrganizationName()),
                new com.fasterxml.jackson.core.type.TypeReference<CasdoorResponse<List<CasdoorProvier>, Object>>() {});
        return response.getData();
    }

    public java.util.Map<String, Object> getPaginationProviders(int p, int pageSize, @Nullable java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<CasdoorProvier[], Object> casdoorResponse = doGet("get-providers",
                Map.mergeMap(Map.of("owner", casdoorConfig.getOrganizationName(),
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<CasdoorProvier[], Object>>() {});

        return Map.of("casdoorProviders", casdoorResponse.getData(), "data2", casdoorResponse.getData2());
    }
    public CasdoorResponse<String, Object> updateProvider(CasdoorProvier provider) throws IOException {
        return modifyProvider(ProviderOperations.UPDATE_PROVIDER, provider, null);
    }

    public CasdoorResponse<String, Object> addProvider(CasdoorProvier provider) throws IOException {
        return modifyProvider(ProviderOperations.ADD_PROVIDER, provider, null);
    }

    public CasdoorResponse<String, Object> deleteProvider(CasdoorProvier provider) throws IOException {
        return modifyProvider(ProviderOperations.DELETE_PROVIDER, provider, null);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyProvider(ProviderOperations method, CasdoorProvier provider, java.util.Map<String, String> queryMap) throws IOException {
        String id = provider.getOwner() + "/" + provider.getName();
        String payload = objectMapper.writeValueAsString(provider);
        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {});
    }
}
