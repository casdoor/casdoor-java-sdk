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

// Token has the same definition as https://github.com/casdoor/casdoor/blob/master/object/token.go#L45

import java.io.Serializable;

public class CasdoorToken implements Serializable {

    private String owner;
    private String name;
    private String createdTime;
    private String application;
    private String accessToken;
    private String refreshToken;
    private String code;
    private String scope;
    private String organization;
    private String user;
    private int expiresIn;
    private String tokenType;
    private String codeChallenge;
    private boolean codeIsUsed;
    private long codeExpireIn;

    public CasdoorToken() {

    }

    public CasdoorToken(String owner, String name, String createdTime, String application, String accessToken, String refreshToken, String code, String scope, String organization, String user, int expiresIn, String tokenType, String codeChallenge, boolean codeIsUsed, long codeExpireIn) {
        this.owner = owner;
        this.name = name;
        this.createdTime = createdTime;
        this.application = application;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.code = code;
        this.scope = scope;
        this.organization = organization;
        this.user = user;
        this.expiresIn = expiresIn;
        this.tokenType = tokenType;
        this.codeChallenge = codeChallenge;
        this.codeIsUsed = codeIsUsed;
        this.codeExpireIn = codeExpireIn;
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

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getCodeChallenge() {
        return codeChallenge;
    }

    public void setCodeChallenge(String codeChallenge) {
        this.codeChallenge = codeChallenge;
    }

    public boolean isCodeIsUsed() {
        return codeIsUsed;
    }

    public void setCodeIsUsed(boolean codeIsUsed) {
        this.codeIsUsed = codeIsUsed;
    }

    public long getCodeExpireIn() {
        return codeExpireIn;
    }

    public void setCodeExpireIn(long codeExpireIn) {
        this.codeExpireIn = codeExpireIn;
    }
}