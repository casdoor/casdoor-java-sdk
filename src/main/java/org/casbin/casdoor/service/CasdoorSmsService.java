// Copyright 2021 The casbin Authors. All Rights Reserved.
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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorSmsForm;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.casbin.casdoor.util.http.HttpClient;

import java.io.IOException;
import java.util.Map;

public class CasdoorSmsService extends CasdoorService {
    public CasdoorSmsService(CasdoorConfig casdoorConfig) {
        super(casdoorConfig);
    }

    public CasdoorResponse sendSms(String content, String... receivers) throws IOException {
        CasdoorSmsForm casdoorSmsForm = new CasdoorSmsForm("admin/" + casdoorConfig.getOrganizationName(), content, receivers);
        String smsFormStr = objectMapper.writeValueAsString(casdoorSmsForm);

        return doPost("send-sms", Map.of(), smsFormStr, new TypeReference<>() {});
    }
}
