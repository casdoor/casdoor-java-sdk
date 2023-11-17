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
import org.casbin.casdoor.entity.Pricing;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.PricingOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;
import java.util.List;

public class PricingService extends Service {

    public PricingService(Config config) {
        super(config);
    }

    public Pricing getPricing(String name) throws IOException {
        CasdoorResponse<Pricing, Object> response = doGet("get-pricing",
                Map.of("id", getConfig().getOrganizationName() + "/" + name), new TypeReference<CasdoorResponse<Pricing, Object>>() {
                });
        return response.getData();
    }

    public List<Pricing> getPricings() throws IOException {
        CasdoorResponse<List<Pricing>, Object> response = doGet("get-pricings",
                Map.of("owner", getConfig().getOrganizationName()), new TypeReference<CasdoorResponse<List<Pricing>, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> addPricing(Pricing pricing) throws IOException {
        return modifyPricing(PricingOperations.ADD_PRICING, pricing, null);
    }

    public CasdoorResponse<String, Object> deletePricing(Pricing pricing) throws IOException {
        return modifyPricing(PricingOperations.DELETE_PRICING, pricing,null);
    }

    public CasdoorResponse<String, Object> updatePricing(Pricing pricing) throws IOException {
        return modifyPricing(PricingOperations.UPDATE_PRICING, pricing, null);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyPricing(PricingOperations method, Pricing pricing, java.util.Map<String, String> queryMap) throws IOException {
        String id = pricing.owner + "/" + pricing.name;
        pricing.owner = getConfig().getOrganizationName();
        String payload = getObjectMapper().writeValueAsString(pricing);
        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}

