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

import java.util.List;

public class Group {
    public String owner;
    public String name;
    public String createdTime;
    public String updatedTime;
    public String displayName;
    public String manager;
    public String contactEmail;
    public String type;
    public String parentId;
    public boolean isTopGroup;
    public List<String> users;
    public String title;
    public String key;
    public List<Group> children;
    public boolean isEnabled;

    public Group(String owner, String name, String createdTime, String displayName) {
        this.owner = owner;
        this.name = name;
        this.createdTime = createdTime;
        this.displayName = displayName;
    }

    public Group() {
    }
}