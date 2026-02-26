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
// See the License for the specific language governing permissions and
// limitations under the License.

package org.casbin.casdoor.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProviderItem implements Serializable {
    public String owner;
    public String name;

    public boolean canSignUp;
    public boolean canSignIn;
    public boolean canUnlink;
    public List<String> countryCodes;
    public boolean prompted;
    public String signupGroup;
    public String rule;
    public Provider provider;

    public ProviderItem() {
    }

    public ProviderItem(String owner, String name) {
        this.owner = owner;
        this.name = name;
    }
}
