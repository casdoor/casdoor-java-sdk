// Copyright 2026 The Casdoor Authors. All Rights Reserved.
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

import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.OAuthToken;
import org.casbin.casdoor.entity.Record;
import org.casbin.casdoor.service.AuthService;
import org.casbin.casdoor.service.RecordService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecordTest {

    private final Config config = TestDefaultConfig.InitConfig();
    private final RecordService recordService = new RecordService(config);

    @Test
    public void testRecord() throws Exception {
        String name = TestDefaultConfig.getRandomName("Record");

        // Add a new object
        Record record = new Record();
        record.owner = TestDefaultConfig.TEST_CASDOOR_ORGANIZATION;
        record.name = name;
        record.createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        record.organization = TestDefaultConfig.TEST_CASDOOR_ORGANIZATION;
        record.user = "admin";
        record.action = "test-record";
        assertDoesNotThrow(() -> recordService.addRecord(record));

        // Reading the records needs the access token of an admin user
        OAuthToken token = new AuthService(config).getOAuthTokenByPassword("admin", "123");
        RecordService adminRecordService = new RecordService(config.withAccessToken(token.accessToken));

        // Get all objects, check if our added object is inside the list
        List<Record> records = adminRecordService.getRecords();
        assertTrue(records.stream().anyMatch(item -> name.equals(item.name)), "Added object not found in list");

        // Get the object
        Record retrievedRecord = adminRecordService.getRecord(name);
        assertNotNull(retrievedRecord, "Added object not found");
        assertEquals(name, retrievedRecord.name, "Retrieved object does not match added object");

        // Get an object that doesn't exist
        assertNull(adminRecordService.getRecord(name + "_missing"));
    }
}
