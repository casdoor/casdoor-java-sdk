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


import java.io.Serializable;

public class CasdoorRecord implements Serializable {
    private int id;
    private String owner;
    private String name;
    private String createdTime;
    private String organization;
    private String clientIp;
    private String user;
    private String method;
    private String requestUri;
    private String action;
    private String object;
    private boolean isTriggered;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public boolean isTriggered() {
        return isTriggered;
    }

    public void setTriggered(boolean triggered) {
        isTriggered = triggered;
    }

    @Override
    public String toString() {
        return "CasdoorRecord{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", organization='" + organization + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", user='" + user + '\'' +
                ", method='" + method + '\'' +
                ", requestUri='" + requestUri + '\'' +
                ", action='" + action + '\'' +
                ", object='" + object + '\'' +
                ", isTriggered=" + isTriggered +
                '}';
    }
}