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

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * User has the same definition as https://github.com/casbin/casdoor/blob/master/object/user.go#L24
 * used to obtain user-related information from your Casdoor server.
 */

public class User implements Serializable {
    public String owner;
    public String name;
    public String createdTime;
    public String updatedTime;

    public String id;
    public String externalId = "";
    public String type;
    public String password = "";
    public String passwordSalt = "";
    public String passwordType = "";
    public String displayName = "";
    public String firstName = "";
    public String lastName = "";
    public String avatar = "";
    public String avatarType = "";
    public String permanentAvatar = "";
    public String email;
    @JsonProperty("emailVerified")
    public boolean emailVerified = false;
    public String phone = "";
    public String countryCode = "";
    public String region = "";
    public String location = "";
    public String[] address;
    public String affiliation = "";
    public String title = "";
    public String idCardType = "";
    public String idCard = "";
    public String homepage = "";
    public String bio = "";
    public String tag = "";
    public String language;
    public String gender = "";
    public String birthday = "";
    public String education = "";
    public int score;
    public int karma;
    public int ranking;
    @JsonProperty("isDefaultAvatar")
    public boolean isDefaultAvatar;
    @JsonProperty("isOnline")
    public boolean isOnline;
    @JsonProperty("isAdmin")
    public boolean isAdmin;
    @JsonProperty("isGlobalAdmin")
    public boolean isGlobalAdmin;
    @JsonProperty("isForbidden")
    public boolean isForbidden;
    @JsonProperty("isDeleted")
    public boolean isDeleted;
    public String signupApplication;
    public String hash = "";
    public String preHash = "";
    public String accessKey = "";
    public String accessSecret = "";

    public String createdIp = "";
    public String lastSigninTime = "";
    public String lastSigninIp = "";

    public String github = "";
    public String google = "";
    public String qq = "";
    public String wechat = "";
    public String facebook = "";
    public String dingtalk = "";
    public String weibo = "";
    public String gitee = "";
    public String linkedin = "";
    public String wecom = "";
    public String lark = "";
    public String gitlab = "";
    public String adfs = "";
    public String baidu = "";
    public String alipay = "";
    public String casdoor = "";
    public String infoflow = "";
    public String apple = "";
    public String azuread = "";
    public String slack = "";
    public String steam = "";
    public String bilibili = "";
    public String okta = "";
    public String douyin = "";
    public String line = "";
    public String amazon = "";
    public String auth0 = "";
    public String battleNet = "";
    public String bitbucket = "";
    public String box = "";
    public String cloudFoundry = "";
    public String dailymotion = "";
    public String deezer = "";
    public String digitalOcean = "";
    public String discord = "";
    public String dropbox = "";
    public String eveOnline = "";
    public String fitbit = "";
    public String gitea = "";
    public String heroku = "";
    public String influxCloud = "";
    public String instagram = "";
    public String intercom = "";
    public String kakao = "";
    public String lastfm = "";
    public String mailru = "";
    public String meetup = "";
    public String microsoftOnline = "";
    public String naver = "";
    public String nextcloud = "";
    public String oneDrive = "";
    public String oura = "";
    public String patreon = "";
    public String paypal = "";
    public String salesForce = "";
    public String shopify = "";
    public String soundcloud = "";
    public String spotify = "";
    public String strava = "";
    public String stripe = "";
    public String tikTok = "";
    public String tumblr = "";
    public String twitch = "";
    public String twitter = "";
    public String typetalk = "";
    public String uber = "";
    public String vK = "";
    public String wepay = "";
    public String xero = "";
    public String yahoo = "";
    public String yammer = "";
    public String yandex = "";
    public String zoom = "";
    public String metaMask = "";
    public String web3Onboard = "";
    public String custom = "";

    public String preferredMfaType = "";
    public List<String> recoveryCodes;
    public String totpSecret = "";
    public boolean mfaPhoneEnabled;
    public boolean mfaEmailEnabled;

    public String ldap = "";
    public Map<String, String> properties;

    public List<Role> roles;
    public List<Permission> permissions;
    public List<String> groups;

    public String lastSigninWrongTime = "";
    public int signinWrongTimes;


    @JsonGetter("isOnline")
    public boolean isOnline() {
        return isOnline;
    }

    @JsonSetter("setOnline")
    public void setOnline(boolean online) {
        isOnline = online;
    }

    @JsonGetter("isAdmin")
    public boolean isAdmin() {
        return isAdmin;
    }

    @JsonSetter("isAdmin")
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @JsonGetter("isGlobalAdmin")
    public boolean isGlobalAdmin() {
        return isGlobalAdmin;
    }

    @JsonSetter("setGlobalAdmin")
    public void setGlobalAdmin(boolean globalAdmin) {
        isGlobalAdmin = globalAdmin;
    }

    @JsonGetter("isForbidden")
    public boolean isForbidden() {
        return isForbidden;
    }

    @JsonSetter("setForbidden")
    public void setForbidden(boolean forbidden) {
        isForbidden = forbidden;
    }

    @JsonGetter("isEmailVerified")
    public boolean isEmailVerified() {
        return emailVerified;
    }

    @JsonSetter("setEmailVerified")
    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    @JsonGetter("isDefaultAvatar")
    public boolean isDefaultAvatar() {
        return isDefaultAvatar;
    }

    @JsonSetter("setDefaultAvatar")
    public void setDefaultAvatar(boolean isDefaultAvatar) {
        this.isDefaultAvatar = isDefaultAvatar;
    }

    @JsonGetter("isDeleted")
    public boolean isDeleted() {
        return isDeleted;
    }

    @JsonSetter("setDeleted")
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public User() {
    }

    public User(String owner, String name, String createdTime, String displayName) {
        this.owner = owner;
        this.name = name;
        this.createdTime = createdTime;
        this.displayName = displayName;
    }
}
