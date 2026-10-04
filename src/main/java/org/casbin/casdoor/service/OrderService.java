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
import org.casbin.casdoor.entity.Order;
import org.casbin.casdoor.entity.Payment;
import org.casbin.casdoor.entity.ProductInfo;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;
import jakarta.annotation.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Collections;

public class OrderService extends Service {
    public OrderService(Config config) {
        super(config);
    }

    public List<Order> getOrders() throws IOException {
        CasdoorResponse<List<Order>, Object> response = doGet("get-orders",
                Map.of("owner", config.organizationName), new TypeReference<CasdoorResponse<List<Order>, Object>>() {
                });
        return response.getData();
    }

    public java.util.Map<String, Object> getPaginationOrders(int p, int pageSize, @Nullable java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<Order[], Object> casdoorResponse = doGet("get-orders",
                Map.mergeMap(Map.of("owner", config.organizationName,
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<Order[], Object>>() {
                });
        return Map.of("casdoorOrders", casdoorResponse.getData(), "data2", casdoorResponse.getData2());
    }

    public List<Order> getUserOrders(String userName) throws IOException {
        CasdoorResponse<List<Order>, Object> response = doGet("get-user-orders",
                Map.of("owner", config.organizationName, "user", userName), new TypeReference<CasdoorResponse<List<Order>, Object>>() {
                });
        return response.getData();
    }

    public Order getOrder(String name) throws IOException {
        CasdoorResponse<Order, Object> response = doGet("get-order",
                Map.of("id", getId(name)), new TypeReference<CasdoorResponse<Order, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> addOrder(Order order) throws IOException {
        return modifyOrder("add-order", order);
    }

    public CasdoorResponse<String, Object> updateOrder(Order order) throws IOException {
        return modifyOrder("update-order", order);
    }

    public CasdoorResponse<String, Object> deleteOrder(Order order) throws IOException {
        return modifyOrder("delete-order", order);
    }

    /**
     * Places an order of the products for the user.
     * @param productInfos the products and their quantities
     * @param userName the user, or null for the current user
     * @return the order
     * @throws IOException when the request fails
     */
    public Order placeOrder(ProductInfo[] productInfos, @Nullable String userName) throws IOException {
        java.util.Map<String, String> queryMap = Map.of("owner", config.organizationName);
        if (userName != null && !userName.isEmpty()) {
            queryMap.put("userName", userName);
        }
        String payload = objectMapper.writeValueAsString(Collections.singletonMap("productInfos", productInfos));
        CasdoorResponse<Order, Object> response = doPost("place-order", queryMap, payload,
                new TypeReference<CasdoorResponse<Order, Object>>() {
                });
        return response.getData();
    }

    /**
     * Creates a payment of the order with the payment provider.
     */
    public Payment payOrder(String orderName, String providerName) throws IOException {
        CasdoorResponse<Payment, Object> response = doPost("pay-order",
                Map.of("id", getId(orderName), "providerName", providerName), "",
                new TypeReference<CasdoorResponse<Payment, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> cancelOrder(String name) throws IOException {
        return doPost("cancel-order", Map.of("id", getId(name)), "",
                new TypeReference<CasdoorResponse<String, Object>>() {
                });
    }

    private CasdoorResponse<String, Object> modifyOrder(String action, Order order) throws IOException {
        order.owner = getOwner(order.owner, config.organizationName);
        String payload = objectMapper.writeValueAsString(order);
        return doPost(action, Map.of("id", order.owner + "/" + order.name), payload,
                new TypeReference<CasdoorResponse<String, Object>>() {
                });
    }
}
