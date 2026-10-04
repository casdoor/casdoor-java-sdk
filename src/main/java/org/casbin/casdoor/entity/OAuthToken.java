// Copyright 2026 The Casdoor Authors. All Rights Reserved.
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * OAuthToken is the token returned by the OAuth token endpoint of Casdoor.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuthToken implements Serializable {
    @JsonProperty("access_token")
    public String accessToken;
    @JsonProperty("id_token")
    public String idToken;
    @JsonProperty("refresh_token")
    public String refreshToken;
    @JsonProperty("token_type")
    public String tokenType;
    @JsonProperty("expires_in")
    public long expiresIn;
    public String scope;
    public String error;
    @JsonProperty("error_description")
    public String errorDescription;

    public OAuthToken() {
    }
}
