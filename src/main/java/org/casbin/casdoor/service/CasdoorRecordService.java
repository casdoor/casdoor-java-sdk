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
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorRecord;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public class CasdoorRecordService extends CasdoorService {
    public CasdoorRecordService(CasdoorConfig casdoorConfig) {
        super(casdoorConfig);
    }

    public CasdoorResponse<String, Object> addRecord(CasdoorRecord record) throws IOException {
        return modifyRecord("add-record", record, null);
    }

    public List<CasdoorRecord> getRecords() throws IOException {
        CasdoorResponse<List<CasdoorRecord>, Object> response = doGet("get-records",
                Map.of("owner", casdoorConfig.getOrganizationName()),
                new TypeReference<CasdoorResponse<List<CasdoorRecord>, Object>>() {});

        return response.getData();
    }
    public java.util.Map<String, Object> getPaginationRecords(int p, int pageSize, @Nullable java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<CasdoorRecord[], Object> casdoorResponse = doGet("get-sessions",
                Map.mergeMap(Map.of("owner", casdoorConfig.getOrganizationName(),
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<CasdoorRecord[], Object>>() {});

        return Map.of("casdoorRecords", casdoorResponse.getData(), "data2", casdoorResponse.getData2());
    }

    public CasdoorRecord getRecord(String name) throws IOException {
        CasdoorResponse<CasdoorRecord, Object> response = doGet("get-record",
                Map.of("id", casdoorConfig.getOrganizationName() + "/" + name),
                new TypeReference<CasdoorResponse<CasdoorRecord, Object>>() {});

        return response.getData();
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyRecord(String method, CasdoorRecord record, java.util.Map<String, String> queryMap) throws IOException {
        String id = record.getOwner() + "/" + record.getName();
        String payload = objectMapper.writeValueAsString(record);
        return doPost(method, Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {});
    }

}
