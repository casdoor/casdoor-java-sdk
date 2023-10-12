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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientId2() {
        return clientId2;
    }

    public void setClientId2(String clientId2) {
        this.clientId2 = clientId2;
    }

    public String getClientSecret2() {
        return clientSecret2;
    }

    public void setClientSecret2(String clientSecret2) {
        this.clientSecret2 = clientSecret2;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getCustomAuthUrl() {
        return customAuthUrl;
    }

    public void setCustomAuthUrl(String customAuthUrl) {
        this.customAuthUrl = customAuthUrl;
    }

    public String getCustomTokenUrl() {
        return customTokenUrl;
    }

    public void setCustomTokenUrl(String customTokenUrl) {
        this.customTokenUrl = customTokenUrl;
    }

    public String getCustomUserInfoUrl() {
        return customUserInfoUrl;
    }

    public void setCustomUserInfoUrl(String customUserInfoUrl) {
        this.customUserInfoUrl = customUserInfoUrl;
    }

    public String getCustomLogo() {
        return customLogo;
    }

    public void setCustomLogo(String customLogo) {
        this.customLogo = customLogo;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public Map<String, String> getUserMapping() {
        return userMapping;
    }

    public void setUserMapping(Map<String, String> userMapping) {
        this.userMapping = userMapping;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isDisableSsl() {
        return disableSsl;
    }

    public void setDisableSsl(boolean disableSsl) {
        this.disableSsl = disableSsl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getIntranetEndpoint() {
        return intranetEndpoint;
    }

    public void setIntranetEndpoint(String intranetEndpoint) {
        this.intranetEndpoint = intranetEndpoint;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getPathPrefix() {
        return pathPrefix;
    }

    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getIdP() {
        return idP;
    }

    public void setIdP(String idP) {
        this.idP = idP;
    }

    public String getIssuerUrl() {
        return issuerUrl;
    }

    public void setIssuerUrl(String issuerUrl) {
        this.issuerUrl = issuerUrl;
    }

    public boolean isEnableSignAuthnRequest() {
        return enableSignAuthnRequest;
    }

    public void setEnableSignAuthnRequest(boolean enableSignAuthnRequest) {
        this.enableSignAuthnRequest = enableSignAuthnRequest;
    }

    public String getProviderUrl() {
        return providerUrl;
    }

    public void setProviderUrl(String providerUrl) {
        this.providerUrl = providerUrl;
    }
}