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
    private String owner, name, createdTime, updatedTime;
    private String id, type, password, displayName, avatar, permanentAvatar, email, phone, location;
    private String[] address;
    private String affiliation, title, homepage, bio, tag, region, language;
    private int score, ranking;
    private boolean isOnline, isAdmin, isGlobalAdmin, isForbidden;
    private String signupApplication, hash, preHash;
    private String github, google, qq, wechat, facebook, dingtalk, weibo, gitee, linkedin, wecom, lark, gitlab;
    private String ldap;
    private Map<String, String> properties;
}
