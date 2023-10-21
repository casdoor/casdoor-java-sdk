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
                Map.of("id", config.organizationName + "/" + name), new TypeReference<CasdoorResponse<Pricing, Object>>() {
                });
        return response.getData();
    }

    public List<Pricing> getPricings() throws IOException {
        CasdoorResponse<List<Pricing>, Object> response = doGet("get-pricings",
                Map.of("owner", config.organizationName), new TypeReference<CasdoorResponse<List<Pricing>, Object>>() {
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
        pricing.owner = config.organizationName;
        String payload = objectMapper.writeValueAsString(pricing);
        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}

