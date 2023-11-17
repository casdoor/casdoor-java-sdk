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

import com.fasterxml.jackson.core.type.TypeReference;
import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.Syncer;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.SyncerOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;
import java.util.List;

public class SyncerService extends Service {
    public SyncerService(Config config) {
        super(config);
    }

    public Syncer getSyncer(String name) throws IOException {
        CasdoorResponse<Syncer, Object> response = doGet("get-syncer",
                Map.of("id", getConfig().getOrganizationName() + "/" + name), new TypeReference<CasdoorResponse<Syncer, Object>>() {
                });
        return response.getData();
    }

    public List<Syncer> getSyncers() throws IOException {
        CasdoorResponse<List<Syncer>, Object> response = doGet("get-syncers",
                Map.of("owner", getConfig().getOrganizationName()), new TypeReference<CasdoorResponse<List<Syncer>, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> addSyncer(Syncer syncer) throws IOException {
        return modifySyncer(SyncerOperations.ADD_SYNCER, syncer, null);
    }

    public CasdoorResponse<String, Object> deleteSyncer(Syncer syncer) throws IOException {
        return modifySyncer(SyncerOperations.DELETE_SYNCER, syncer, null);
    }

    public CasdoorResponse<String, Object> updateSyncer(Syncer syncer) throws IOException {
        return modifySyncer(SyncerOperations.UPDATE_SYNCER, syncer, null);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifySyncer(SyncerOperations method, Syncer syncer, java.util.Map<String, String> queryMap) throws IOException {
        String id = syncer.owner + "/" + syncer.name;
        syncer.owner = getConfig().getOrganizationName();
        String payload = getObjectMapper().writeValueAsString(syncer);
        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}
