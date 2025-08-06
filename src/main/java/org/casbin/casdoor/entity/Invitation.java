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

public class Invitation {
    public String owner;
    public String name;
    public String createdTime;
    public String updatedTime;
    public String displayName;
    public String code;
    public boolean isRegexp;
    public int quota;
    public int usedCount;
    public String application;
    public String username;
    public String email;
    public String phone;
    public String signupGroup;
    public String defaultCode;
    public String state;

    public Invitation(String owner, String name, String createdTime, String displayName, String code, boolean isRegexp, int quota, String application, String defaultCode) {
        this.owner = owner;
        this.name = name;
        this.createdTime = createdTime;
        this.displayName = displayName;
        this.code = code;
        this.isRegexp = isRegexp;
        this.quota = quota;
        this.application = application;
        this.defaultCode = defaultCode;
    }

    public Invitation() {
    }
}
