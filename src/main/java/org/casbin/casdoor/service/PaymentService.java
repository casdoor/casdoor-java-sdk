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

import jakarta.annotation.Nullable;

import java.io.IOException;

public class PaymentService extends Service {

    public PaymentService(Config config) {
        super(config);
    }

    public Payment getPayment(String name) throws IOException {
        CasdoorResponse<Payment, Object> response = doGet("get-payment",
                Map.of("id", getId(name)), new TypeReference<CasdoorResponse<Payment, Object>>() {
                });
        return response.getData();
    }

    public List<Payment> getPayments() throws IOException {
        CasdoorResponse<List<Payment>, Object> response = doGet("get-payments",
                Map.of("owner", config.organizationName), new TypeReference<CasdoorResponse<List<Payment>, Object>>() {
                });
        return response.getData();
    }

    public java.util.Map<String, Object> getPaginationPayments(int p, int pageSize, @Nullable java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<Payment[], Object> casdoorResponse = doGet("get-payments",
                Map.mergeMap(Map.of("owner", config.organizationName,
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<Payment[], Object>>() {
                });

        return Map.of("casdoorPayments", casdoorResponse.getData(), "data2", casdoorResponse.getData2());
    }

    public List<Payment> getUserPayments(String userName) throws IOException {
        CasdoorResponse<List<Payment>, Object> response = doGet("get-user-payments",
                Map.of("owner", config.organizationName, "organization", config.organizationName, "user", userName),
                new TypeReference<CasdoorResponse<List<Payment>, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> notifyPayment(Payment payment) throws IOException {
        return modifyPaymentAction("notify-payment", payment);
    }

    public CasdoorResponse<String, Object> invoicePayment(Payment payment) throws IOException {
        return modifyPaymentAction("invoice-payment", payment);
    }

    private CasdoorResponse<String, Object> modifyPaymentAction(String action, Payment payment) throws IOException {
        payment.owner = getOwner(payment.owner, config.organizationName);
        return doPost(action, Map.of("id", payment.owner + "/" + payment.name), objectMapper.writeValueAsString(payment),
                new TypeReference<CasdoorResponse<String, Object>>() {
                });
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
        payment.owner = getOwner(payment.owner, config.organizationName);
        String id = payment.owner + "/" + payment.name;
        String payload = objectMapper.writeValueAsString(payment);
        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}
