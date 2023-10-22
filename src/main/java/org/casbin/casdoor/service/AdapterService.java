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
// See the License for the specific language governing CasdoorPermissions and
// limitations under the License.

package org.casbin.casdoor.service;

import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.Adapter;
import org.casbin.casdoor.util.AdapterOperations;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

import java.io.IOException;

public class AdapterService extends Service {

    public AdapterService(Config config) {
        super(config);
    }

    public Adapter getAdapter(String name) throws IOException {
        CasdoorResponse<Adapter, Object> response = doGet("get-adapter",
                Map.of("id", config.organizationName + "/" + name), new TypeReference<CasdoorResponse<Adapter, Object>>() {
                });
        return response.getData();
    }

    public List<Adapter> getAdapters() throws IOException {
        CasdoorResponse<List<Adapter>, Object> response = doGet("get-adapters",
                Map.of("owner", config.organizationName), new TypeReference<CasdoorResponse<List<Adapter>, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> addAdapter(Adapter adapter) throws IOException {
        return modifyAdapter(AdapterOperations.ADD_ADAPTER, adapter, null);
    }

    public CasdoorResponse<String, Object> deleteAdapter(Adapter adapter) throws IOException {
        return modifyAdapter(AdapterOperations.DELETE_ADAPTER, adapter, null);
    }

    public CasdoorResponse<String, Object> updateAdapter(Adapter adapter) throws IOException {
        return modifyAdapter(AdapterOperations.UPDATE_ADAPTER, adapter, null);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyAdapter(AdapterOperations method, Adapter adapter, java.util.Map<String, String> queryMap) throws IOException {
        String id = adapter.owner + "/" + adapter.name;
        adapter.owner = config.organizationName;
        String payload = objectMapper.writeValueAsString(adapter);

        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}