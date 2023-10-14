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

public class Token implements Serializable {

    public String owner;
    public String name;
    public String createdTime;
    public String application;
    public String accessToken;
    public String refreshToken;
    public String code;
    public String scope;
    public String organization;
    public String user;
    public int expiresIn;
    public String tokenType;
    public String codeChallenge;
    public boolean codeIsUsed;
    public long codeExpireIn;

    public Token() {

    }

    public Token(String owner, String name, String createdTime, String application, String accessToken, String refreshToken, String code, String scope, String organization, String user, int expiresIn, String tokenType, String codeChallenge, boolean codeIsUsed, long codeExpireIn) {
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

}