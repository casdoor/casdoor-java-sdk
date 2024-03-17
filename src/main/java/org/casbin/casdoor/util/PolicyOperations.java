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

package org.casbin.casdoor.util;

public enum PolicyOperations {

    ADD_Policy("add-policy"),
    DELETE_Policy("remove-policy"),
    UPDATE_Policy("update-policy");

    private final String operation;

    PolicyOperations(String op) {
        this.operation = op;
    }

    public String getOperation() {
        return operation;
    }

}
