// Copyright 2025 The Casdoor Authors. All Rights Reserved.
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

package org.casbin.casdoor.entity;

public class Transaction {
    public String owner;
    public String name;
    public String createdTime;
    public String displayName;
    public String provider;
    public String category;
    public String type;
    public String productName;
    public String productDisplayName;
    public String detail;
    public String tag;
    public String currency;
    public double amount;
    public String returnUrl;
    public String user;
    public String application;
    public String payment;
    public String state;

    public Transaction(String owner, String name, String displayName, String provider, String category, String type, String productName, String productDisplayName, String detail) {
        this.owner = owner;
        this.name = name;
        this.displayName = displayName;
        this.provider = provider;
        this.category = category;
        this.type = type;
        this.productName = productName;
        this.productDisplayName = productDisplayName;
        this.detail = detail;
    }

    public Transaction() {
    }
}
