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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Permission has the same definition as https://github.com/casbin/casdoor/blob/master/object/permission.go#L29
 */

public class Permission implements Serializable {
    public String owner;
    public String name;
    public String createdTime;
    public String displayName;
    public String description;

    public String[] users;
    public String[] roles;
    public String[] domains;

    public String model;
    public String adapter;
    public String resourceType;
    public String[] resources;
    public String[] actions;
    public String effect;
    @JsonProperty("isEnabled")
    public boolean isEnabled;
    public String submitter;
    public String approver;
    public String approveTime;
    public String state;

    public Permission() {
    }

    public Permission(String owner, String name, String createdTime, String displayName, String description, String[] users, String[] roles, String[] domains, String model, String resourceType, String[] resources, String[] actions, String effect, boolean isEnabled) {
        this.owner = owner;
        this.name = name;
        this.createdTime = createdTime;
        this.displayName = displayName;
        this.description = description;
        this.users = users;
        this.roles = roles;
        this.domains = domains;
        this.model = model;
        this.resourceType = resourceType;
        this.resources = resources;
        this.actions = actions;
        this.effect = effect;
        this.isEnabled = isEnabled;
    }
}
