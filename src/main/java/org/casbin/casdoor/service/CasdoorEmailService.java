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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorEmailForm;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.casbin.casdoor.util.http.HttpClient;

import java.io.IOException;

public class CasdoorEmailService {

    private final CasdoorConfig casdoorConfig;
    final private ObjectMapper objectMapper = new ObjectMapper();

    public CasdoorEmailService(CasdoorConfig casdoorConfig) {
        this.casdoorConfig = casdoorConfig;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public CasdoorResponse sendEmail(String title, String content, String sender, String... receivers) throws IOException {
        String targetUrl = String.format("%s/api/send-email?clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getClientId(), casdoorConfig.getClientSecret());
        CasdoorEmailForm casdoorEmailForm = new CasdoorEmailForm(title, content, sender, receivers);
        String emailFormStr = objectMapper.writeValueAsString(casdoorEmailForm);
        String responseStr = HttpClient.postString(targetUrl, emailFormStr);
        return objectMapper.readValue(responseStr, CasdoorResponse.class);
    }
}
