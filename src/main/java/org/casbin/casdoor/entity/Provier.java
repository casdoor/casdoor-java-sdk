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
import java.util.Map;

public class Provier implements Serializable {
    public String owner;
    public String name;
    public String createdTime;
    public String displayName;
    public String category;
    public String type;
    public String subType;
    public String method;
    public String clientId;
    public String clientSecret;
    public String clientId2;
    public String clientSecret2;
    public String cert;
    public String customAuthUrl;
    public String customTokenUrl;
    public String customUserInfoUrl;
    public String customLogo;
    public String scopes;
    public Map<String, String> userMapping;
    public String host;
    public int port;
    public boolean disableSsl;
    public String title;
    public String content;
    public String receiver;
    public String regionId;
    public String signName;
    public String templateCode;
    public String appId;
    public String endpoint;
    public String intranetEndpoint;
    public String domain;
    public String bucket;
    public String pathPrefix;
    public String metadata;
    public String idP;
    public String issuerUrl;
    public boolean enableSignAuthnRequest;
    public String providerUrl;

    public Provier() {
    }

    public Provier(String owner, String name, String createdTime, String displayName, String category, String type) {
        this.owner = owner;
        this.name = name;
        this.createdTime = createdTime;
        this.displayName = displayName;
        this.category = category;
        this.type = type;
    }
}