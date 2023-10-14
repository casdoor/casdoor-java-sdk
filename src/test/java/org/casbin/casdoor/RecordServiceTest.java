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

package org.casbin.casdoor;

import org.casbin.casdoor.entity.Record;
import org.casbin.casdoor.service.RecordService;
import org.casbin.casdoor.support.ConfigFactory;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RecordServiceTest {

    private RecordService recordService;

    @Before
    public void init() throws Exception {
        recordService = new RecordService(ConfigFactory.getConfig());
    }

    @Test
    public void testAddRecord() throws IOException {
        Record record = new Record();
        record.name = "test";
        record.owner = "test-buildt-in";
        CasdoorResponse response = recordService.addRecord(record);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());
    }

    @Test
    public void testGetRecords() throws IOException {
        List<Record> records = recordService.getRecords();
        Assert.assertNotNull(records);
    }

    @Test
    public void testGetPaginationRecords() throws IOException {
        Map<String, Object> paginationRecords = recordService.getPaginationRecords(1, 10, null);
        Assert.assertNotNull(paginationRecords);
    }

    @Test
    public void testGetRecord() throws IOException {
       recordService.getRecord("test");
    }
}