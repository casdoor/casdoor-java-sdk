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

import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.Payment;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.PaymentOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

import java.io.IOException;

public class PaymentService extends Service {

    public PaymentService(Config config) {
        super(config);
    }

    public Payment getPayment(String name) throws IOException {
        CasdoorResponse<Payment, Object> response = doGet("get-payment",
                Map.of("id", config.organizationName + "/" + name), new TypeReference<CasdoorResponse<Payment, Object>>() {
                });
        return response.getData();
    }

    public List<Payment> getPayments() throws IOException {
        CasdoorResponse<List<Payment>, Object> response = doGet("get-payments",
                Map.of("owner", config.organizationName), new TypeReference<CasdoorResponse<List<Payment>, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> addPayment(Payment payment) throws IOException {
        return modifyPayment(PaymentOperations.ADD_PAYMENT, payment, null);
    }

    public CasdoorResponse<String, Object> deletePayment(Payment payment) throws IOException {
        return modifyPayment(PaymentOperations.DELETE_PAYMENT, payment, null);
    }

    public CasdoorResponse<String, Object> updatePayment(Payment payment) throws IOException {
        return modifyPayment(PaymentOperations.UPDATE_PAYMENT, payment, null);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyPayment(PaymentOperations method, Payment payment, java.util.Map<String, String> queryMap) throws IOException {
        String id = payment.owner + "/" + payment.name;
        payment.owner = config.organizationName;
        String payload = objectMapper.writeValueAsString(payment);
        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}
