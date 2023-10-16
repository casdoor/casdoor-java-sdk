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

package org.casbin.casdoor.entity;

public class Payment {
    public String owner;
    public String name;
    public String createdTime;
    public String displayName;
    public String provider;
    public String type;
    public String productName;
    public String productDisplayName;
    public String detail;
    public String tag;
    public String currency;
    public double price;
    public String returnUrl;
    public String user;
    public String personName;
    public String personIdCard;
    public String personEmail;
    public String personPhone;
    public String invoiceType;
    public String invoiceTitle;
    public String invoiceTaxId;
    public String invoiceRemark;
    public String invoiceUrl;
    public String outOrderId;
    public String payUrl;
    public String state;
    public String message;

    public Payment() {
    }

    public Payment(String owner, String name, String createdTime, String displayName, String productName) {
        this.owner = owner;
        this.name = name;
        this.createdTime = createdTime;
        this.displayName = displayName;
        this.productName = productName;
    }
}
