// Copyright 2024 The Casdoor Authors. All Rights Reserved.
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
import org.casbin.casdoor.entity.Transaction;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.TransactionOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;
import java.util.List;

public class TransactionService extends Service {

    public TransactionService(Config config) {
        super(config);
    }

    public List<Transaction> getTransactions() throws IOException {
        CasdoorResponse<List<Transaction>, Object> response = doGet("get-transactions",
                Map.of("owner", config.organizationName), new TypeReference<CasdoorResponse<List<Transaction>, Object>>() {
                });
        return response.getData();
    }

    public Transaction getTransaction(String name) throws IOException {
        CasdoorResponse<Transaction, Object> response = doGet("get-transaction",
                Map.of("id", config.organizationName + "/" + name), new TypeReference<CasdoorResponse<Transaction, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> addTransaction(Transaction transaction) throws IOException {
        return modifyTransaction(TransactionOperations.ADD_TRANSACTION, transaction, null);
    }

    public CasdoorResponse<String, Object> deleteTransaction(Transaction transaction) throws IOException {
        return modifyTransaction(TransactionOperations.DELETE_TRANSACTION, transaction, null);
    }

    public CasdoorResponse<String, Object> updateTransaction(Transaction transaction) throws IOException {
        return modifyTransaction(TransactionOperations.UPDATE_TRANSACTION, transaction, null);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyTransaction(TransactionOperations method, Transaction transaction, java.util.Map<String, String> queryMap) throws IOException {
        String id = transaction.owner + "/" + transaction.name;
        transaction.owner = config.organizationName;
        String payload = objectMapper.writeValueAsString(transaction);

        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}