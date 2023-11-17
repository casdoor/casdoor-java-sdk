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
import org.casbin.casdoor.entity.SmsForm;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;

public class SmsService extends Service {
    public SmsService(Config config) {
        super(config);
    }

    public CasdoorResponse sendSms(String content, String... receivers) throws IOException {
        SmsForm smsForm = new SmsForm( content, receivers);
        String smsFormStr = getObjectMapper().writeValueAsString(smsForm);

        return doPost("send-sms", Map.of(), smsFormStr, new TypeReference<CasdoorResponse<Object, Object>>() {});
    }
}
