// Copyright 2023 The casbin Authors. All Rights Reserved.
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


import java.io.Serializable;

public class CasdoorSession implements Serializable {
    private String owner;
    private String name;
    private String application;
    private String createdTime;
    private String[] sessionId;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String[] getSessionId() {
        return sessionId;
    }

    public void setSessionId(String[] sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "CasdoorSession{" +
                "owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", application='" + application + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", sessionId=" + sessionId +
                '}';
    }
}