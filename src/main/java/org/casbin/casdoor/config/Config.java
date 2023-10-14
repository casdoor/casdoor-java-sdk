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
public class Config {
    public String endpoint;
    public String clientId;
    public String clientSecret;
    public String certificate;
    public String organizationName;
    public String applicationName;

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

}
