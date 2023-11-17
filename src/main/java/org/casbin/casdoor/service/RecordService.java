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
import org.casbin.casdoor.entity.Record;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public class RecordService extends Service {
    public RecordService(Config config) {
        super(config);
    }

    public CasdoorResponse<String, Object> addRecord(Record record) throws IOException {
        return modifyRecord("add-record", record, null);
    }

    public List<Record> getRecords() throws IOException {
        CasdoorResponse<List<Record>, Object> response = doGet("get-records",
                Map.of("owner", getConfig().getOrganizationName()),
                new TypeReference<CasdoorResponse<List<Record>, Object>>() {});

        return response.getData();
    }
    public java.util.Map<String, Object> getPaginationRecords(int p, int pageSize, @Nullable java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<Record[], Object> casdoorResponse = doGet("get-sessions",
                Map.mergeMap(Map.of("owner", getConfig().getOrganizationName(),
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<Record[], Object>>() {});

        return Map.of("casdoorRecords", casdoorResponse.getData(), "data2", casdoorResponse.getData2());
    }

    public Record getRecord(String name) throws IOException {
        CasdoorResponse<Record, Object> response = doGet("get-record",
                Map.of("id", getConfig().getOrganizationName() + "/" + name),
                new TypeReference<CasdoorResponse<Record, Object>>() {});

        return response.getData();
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyRecord(String method, Record record, java.util.Map<String, String> queryMap) throws IOException {
        String id = record.owner + "/" + record.name;
        String payload = getObjectMapper().writeValueAsString(record);
        return doPost(method, Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {});
    }

}
