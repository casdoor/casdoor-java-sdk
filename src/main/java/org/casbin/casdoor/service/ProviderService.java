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
import org.casbin.casdoor.entity.Provider;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.ProviderOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;
import jakarta.annotation.Nullable;

import java.io.IOException;
import java.util.List;

public class ProviderService extends Service {

    public ProviderService(Config config) {
        super(config);
    }

    public Provider getProvider(String name) throws IOException {
        CasdoorResponse<Provider, Object> response = doGet("get-provider",
                Map.of("id", config.organizationName + "/" + name),
                new TypeReference<CasdoorResponse<Provider, Object>>() {});
        return response.getData();
    }

    public List<Provider> getProviders() throws IOException {
        CasdoorResponse<List<Provider>, Object> response = doGet("get-providers",
                Map.of("owner", config.organizationName),
                new TypeReference<CasdoorResponse<List<Provider>, Object>>() {});
        return response.getData();
    }

    public java.util.Map<String, Object> getPaginationProviders(int p, int pageSize, @Nullable java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<Provider[], Object> casdoorResponse = doGet("get-providers",
                Map.mergeMap(Map.of("owner", config.organizationName,
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<Provider[], Object>>() {});

        return Map.of("casdoorProviders", casdoorResponse.getData(), "data2", casdoorResponse.getData2());
    }

    public CasdoorResponse<String, Object> updateProvider(Provider provider) throws IOException {
        return modifyProvider(ProviderOperations.UPDATE_PROVIDER, provider, null);
    }

    public CasdoorResponse<String, Object> addProvider(Provider provider) throws IOException {
        return modifyProvider(ProviderOperations.ADD_PROVIDER, provider, null);
    }

    public CasdoorResponse<String, Object> deleteProvider(Provider provider) throws IOException {
        return modifyProvider(ProviderOperations.DELETE_PROVIDER, provider, null);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyProvider(ProviderOperations method, Provider provider, java.util.Map<String, String> queryMap) throws IOException {
        String id = provider.owner + "/" + provider.name;
        provider.owner = config.organizationName;
        String payload = objectMapper.writeValueAsString(provider);
        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {});
    }
}
