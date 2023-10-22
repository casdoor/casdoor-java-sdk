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

import java.util.List;

public class Product {
    public String owner;
    public String name;
    public String createdTime;
    public String displayName;
    public String image;
    public String detail;
    public String description;
    public String tag;
    public String currency;
    public double price;
    public int quantity;
    public int sold;
    public List<String> providers;
    public String returnUrl;
    public String state;
    public List<Provier> providerObjs;

    public Product() {
    }

    public Product(String owner, String name, String createdTime, String displayName, String image, String description, String tag, int quantity, int sold, String state) {
        this.owner = owner;
        this.name = name;
        this.createdTime = createdTime;
        this.displayName = displayName;
        this.image = image;
        this.description = description;
        this.tag = tag;
        this.quantity = quantity;
        this.sold = sold;
        this.state = state;
    }
}
