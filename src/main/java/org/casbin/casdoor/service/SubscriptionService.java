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
import org.casbin.casdoor.entity.Subscription;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.SubscriptionOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;
import java.util.List;

public class SubscriptionService extends Service {

    public SubscriptionService(Config config) {
        super(config);
    }

    public Subscription getSubscription(String name) throws IOException {
        CasdoorResponse<Subscription, Object> response = doGet("get-subscription",
                Map.of("id", getConfig().getOrganizationName() + "/" + name), new TypeReference<CasdoorResponse<Subscription, Object>>() {
                });
        return response.getData();
    }

    public List<Subscription> getSubscriptions() throws IOException {
        CasdoorResponse<List<Subscription>, Object> response = doGet("get-subscriptions",
                Map.of("owner", getConfig().getOrganizationName()), new TypeReference<CasdoorResponse<List<Subscription>, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> addSubscription(Subscription subscription) throws IOException {
        return modifySubscription(SubscriptionOperations.ADD_SUBSCRIPTION, subscription, null);
    }

    public CasdoorResponse<String, Object> deleteSubscription(Subscription subscription) throws IOException {
        return modifySubscription(SubscriptionOperations.DELETE_SUBSCRIPTION, subscription, null);
    }

    public CasdoorResponse<String, Object> updateSubscription(Subscription subscription) throws IOException {
        return modifySubscription(SubscriptionOperations.UPDATE_SUBSCRIPTION, subscription, null);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifySubscription(SubscriptionOperations method, Subscription subscription, java.util.Map<String, String> queryMap) throws IOException {
        String id = subscription.owner + "/" + subscription.name;
        subscription.owner = getConfig().getOrganizationName();
        String payload = getObjectMapper().writeValueAsString(subscription);
        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}