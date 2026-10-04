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

package org.casbin.casdoor.config;

/**
 * CasdoorConfig is the core configuration.
 * The first step to use this SDK is to initialize the global casdoorConfig.
 */
import java.util.HashMap;

public class Config {
    public String endpoint;
    public String clientId;
    public String clientSecret;
    public String certificate;
    public String organizationName;
    public String applicationName;
    /**
     * The HTTP headers added to all the API requests, e.g. "Accept-Language".
     */
    public java.util.Map<String, String> customHeaders = new HashMap<>();
    /**
     * When set (see withAccessToken()), the APIs are called as the user who owns the access token
     * (Authorization: Bearer) instead of as the application (client ID and secret).
     */
    public String accessToken;

    public Config() {
    }

    public Config(String endpoint, String clientId, String clientSecret, String certificate, String organizationName, String applicationName) {
        this.endpoint = endpoint;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.certificate = certificate;
        this.organizationName = organizationName;
        this.applicationName = applicationName;
    }

    /**
     * Returns a copy of this config that calls the APIs as the user who owns the access token,
     * the services created with it only have the user's own permissions. This config is not changed.
     * @param accessToken the access token of the user
     * @return the new config
     */
    public Config withAccessToken(String accessToken) {
        Config config = new Config(endpoint, clientId, clientSecret, certificate, organizationName, applicationName);
        config.customHeaders = new HashMap<>(customHeaders);
        config.accessToken = accessToken;
        return config;
    }

    public java.util.Map<String, String> getCustomHeaders() {
        return customHeaders;
    }

    public void setCustomHeaders(java.util.Map<String, String> customHeaders) {
        this.customHeaders = customHeaders;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
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

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
