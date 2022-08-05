// Copyright 2021 The casbin Authors. All Rights Reserved.
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * User has the same definition as https://github.com/casbin/casdoor/blob/master/object/user.go#L24
 * used to obtain user-related information from your Casdoor server.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CasdoorUser implements Serializable {
    private String owner;
    private String name;
    private String createdTime;
    private String updatedTime;
    private String id;
    private String type;
    private String password;
    private String displayName;
    private String avatar;
    private String permanentAvatar;
    private String email;
    private String phone;
    private String location;
    private String[] address;
    private String affiliation;
    private String title;
    private String homepage;
    private String bio;
    private String tag;
    private String region;
    private String language;
    private int score;
    private int ranking;
    @JsonProperty("isOnline")
    private boolean isOnline;
    @JsonProperty("isAdmin")
    private boolean isAdmin;
    @JsonProperty("isGlobalAdmin")
    private boolean isGlobalAdmin;
    @JsonProperty("isForbidden")
    private boolean isForbidden;
    private String signupApplication;
    private String hash;
    private String preHash;
    private String github;
    private String google;
    private String qq;
    private String wechat;
    private String facebook;
    private String dingtalk;
    private String weibo;
    private String gitee;
    private String linkedin;
    private String wecom;
    private String lark;
    private String gitlab;
    private String ldap;
    private Map<String, String> properties;
    private CasdoorRole[] roles;
    private CasdoorPermission[] permissions;

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
}
