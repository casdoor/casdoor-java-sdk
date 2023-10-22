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

public class Pricing {
    public String owner;

    public String name;

    public String createdTime;

    public String displayName;

    public String description;

    public List<String> plans;

    public boolean isEnabled;

    public int trialDuration;

    public String application;

    public String submitter;

    public String approver;

    public String approveTime;

    public String state;

    public Pricing() {

    }

    public Pricing(String owner, String name, String createdTime, String displayName, String application, String description) {

        this.owner = owner;

        this.name = name;

        this.createdTime = createdTime;

        this.displayName = displayName;

        this.description = description;

        this.application = application;

    }
}
