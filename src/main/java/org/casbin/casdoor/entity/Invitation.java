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
    private String owner;
    private String name;
    private String createdTime;
    private String updatedTime;
    private String displayName;
    private String code;
    private boolean isRegexp;
    private int quota;
    private int usedCount;
    private String application;
    private String username;
    private String email;
    private String phone;
    private String signupGroup;
    private String defaultCode;
    private String state;

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
