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

package org.casbin.casdoor.util;

public enum PaymentOperations {
    ADD_PAYMENT("add-payment"),
    DELETE_PAYMENT("delete-payment"),
    UPDATE_PAYMENT("update-payment");
    private final String operation;

    PaymentOperations(String op) {
        this.operation = op;
    }

    public String getOperation() {
        return operation;
    }
}

