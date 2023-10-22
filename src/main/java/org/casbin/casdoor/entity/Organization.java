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

public class Organization {
    public String owner;
    public String name;
    public String createdTime;
    public String displayName;
    public String websiteUrl;
    public String favicon;
    public String passwordType;
    public String passwordSalt;
    public String[] passwordOptions;
    public String[] countryCodes;
    public String defaultAvatar;
    public String defaultApplication;
    public String[] tags;
    public String[] languages;
    public ThemeData themeData;
    public String masterPassword;
    public int initScore;
    public boolean enableSoftDeletion;
    public boolean isProfilePublic;
    public MfaItem[] mfaItems;
    public AccountItem[] accountItems;

    public Organization(String owner, String name, String createdTime, String displayName, String websiteUrl, String passwordType, String[] passwordOptions, String[] countryCodes, String[] tags, String[] languages, int initScore, boolean enableSoftDeletion, boolean isProfilePublic) {
        this.owner = owner;
        this.name = name;
        this.createdTime = createdTime;
        this.displayName = displayName;
        this.websiteUrl = websiteUrl;
        this.passwordType = passwordType;
        this.passwordOptions = passwordOptions;
        this.countryCodes = countryCodes;
        this.tags = tags;
        this.languages = languages;
        this.initScore = initScore;
        this.enableSoftDeletion = enableSoftDeletion;
        this.isProfilePublic = isProfilePublic;
    }

    public Organization() {
    }
}
