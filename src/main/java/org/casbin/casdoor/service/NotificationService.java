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

package org.casbin.casdoor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;

public class NotificationService extends Service {
    public NotificationService(Config config) {
        super(config);
    }

    /**
     * Sends the content to the recipient by the notification provider of the organization.
     */
    public CasdoorResponse<Object, Object> sendNotification(String content, String recipient) throws IOException {
        String payload = objectMapper.writeValueAsString(Map.of("content", content, "recipient", recipient));
        return doPost("send-notification", null, payload, new TypeReference<CasdoorResponse<Object, Object>>() {
        });
    }
}
