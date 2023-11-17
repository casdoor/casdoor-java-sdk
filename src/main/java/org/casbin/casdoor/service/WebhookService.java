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
import org.casbin.casdoor.entity.Webhook;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.WebhookOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;
import java.util.List;

public class WebhookService extends Service {
    public WebhookService(Config config) {
        super(config);
    }

    public Webhook getWebhook(String name) throws IOException {
        CasdoorResponse<Webhook, Object> response = doGet("get-webhook",
                Map.of("id", getConfig().getOrganizationName() + "/" + name), new TypeReference<CasdoorResponse<Webhook, Object>>() {
                });
        return response.getData();
    }

    public List<Webhook> getWebhooks() throws IOException {
        CasdoorResponse<List<Webhook>, Object> response = doGet("get-webhooks",
                Map.of("owner", getConfig().getOrganizationName()), new TypeReference<CasdoorResponse<List<Webhook>, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> addWebhook(Webhook webhook) throws IOException {
        return modifyWebhook(WebhookOperations.ADD_WEBHOOK, webhook, null);
    }

    public CasdoorResponse<String, Object> deleteWebhook(Webhook webhook) throws IOException {
        return modifyWebhook(WebhookOperations.DELETE_WEBHOOK, webhook, null);
    }

    public CasdoorResponse<String, Object> updateWebhook(Webhook webhook) throws IOException {
        return modifyWebhook(WebhookOperations.UPDATE_WEBHOOK, webhook, null);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyWebhook(WebhookOperations method, Webhook webhook, java.util.Map<String, String> queryMap) throws IOException {
        String id = webhook.owner + "/" + webhook.name;
        webhook.owner = getConfig().getOrganizationName();
        String payload = getObjectMapper().writeValueAsString(webhook);
        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}
