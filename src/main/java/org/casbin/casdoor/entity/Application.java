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
import java.util.List;

public class Application implements Serializable {
    public String owner;
    public String name;
    public String createdTime;
    public String displayName;
    public String logo;
    public String homepageUrl;
    public String description;
    public String organization;
    public String cert;
    public boolean enablePassword;
    public boolean enableSignUp;
    public boolean enableSigninSession;
    public boolean enableAutoSignin;
    public boolean enableCodeSignin;
    public boolean enableSamlCompress;
    public boolean enableWebAuthn;
    public boolean enableLinkWithEmail;
    public String orgChoiceMode;
    public String samlReplyUrl;
    public List<String> grantTypes;
    public List<String> tags;
    public String clientId;
    public String clientSecret;
    public List<String> redirectUris;
    public String tokenFormat;
    public int expireInHours;
    public int refreshExpireInHours;
    public String signupUrl;
    public String signinUrl;
    public String forgetUrl;
    public String affiliationUrl;
    public String termsOfUse;
    public String signupHtml;
    public String signinHtml;
    public String formCss;
    public String formCssMobile;
    public int formOffset;
    public String formSideHtml;
    public String formBackgroundUrl;
}