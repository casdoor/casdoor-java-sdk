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

import java.util.Arrays;
import java.util.List;

import java.io.Serializable;
import java.util.Map;

/**
 * User has the same definition as https://github.com/casbin/casdoor/blob/master/object/user.go#L24
 * used to obtain user-related information from your Casdoor server.
 */

public class CasdoorUser implements Serializable {
    private String owner;
    private String name;
    private String createdTime;
    private String updatedTime;

    private String id;
    private String type;
    private String password = "";
    private String passwordSalt = "";
    private String displayName = "";
    private String firstName = "";
    private String lastName = "";
    private String avatar = "";
    private String permanentAvatar = "";
    private String email;
    @JsonProperty("emailVerified")
    private boolean emailVerified = false;
    private String phone = "";
    private String location = "";
    private String[] address;
    private String affiliation = "";
    private String title = "";
    private String idCardType = "";
    private String idCard = "";
    private String homepage = "";
    private String bio = "";
    private String tag = "";
    private String region = "";
    private String language;
    private String gender = "";
    private String birthday = "";
    private String education = "";
    private int score;
    private int karma;
    private int ranking;
    @JsonProperty("isDefaultAvatar")
    private boolean isDefaultAvatar;
    @JsonProperty("isOnline")
    private boolean isOnline;
    @JsonProperty("isAdmin")
    private boolean isAdmin;
    @JsonProperty("isGlobalAdmin")
    private boolean isGlobalAdmin;
    @JsonProperty("isForbidden")
    private boolean isForbidden;
    @JsonProperty("isDeleted")
    private boolean isDeleted;
    private String signupApplication;
    private String hash = "";
    private String preHash = "";

    private String createdIp = "";
	private String lastSigninTime = "";
	private String lastSigninIp = "";

    private String github = "";
    private String google = "";
    private String qq = "";
    private String wechat = "";
    private String facebook = "";
    private String dingtalk = "";
    private String weibo = "";
    private String gitee = "";
    private String linkedin = "";
    private String wecom = "";
    private String lark = "";
    private String gitlab = "";
    private String adfs = "";
    private String baidu = "";
    private String alipay = "";
    private String casdoor = "";
    private String infoflow = "";
    private String apple = "";
    private String azuread = "";
    private String slack = "";
    private String steam = "";
    private String bilibili = "";
    private String okta = "";
    private String douyin = "";
    private String custom = "";
    private String ldap = "";
    private Map<String, String> properties;
    private List<CasdoorRole> roles;
    private List<CasdoorPermission> permissions;

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

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPermanentAvatar() {
        return permanentAvatar;
    }

    public void setPermanentAvatar(String permanentAvatar) {
        this.permanentAvatar = permanentAvatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getSignupApplication() {
        return signupApplication;
    }

    public void setSignupApplication(String signupApplication) {
        this.signupApplication = signupApplication;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreHash() {
        return preHash;
    }

    public void setPreHash(String preHash) {
        this.preHash = preHash;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getDingtalk() {
        return dingtalk;
    }

    public void setDingtalk(String dingtalk) {
        this.dingtalk = dingtalk;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getGitee() {
        return gitee;
    }

    public void setGitee(String gitee) {
        this.gitee = gitee;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getWecom() {
        return wecom;
    }

    public void setWecom(String wecom) {
        this.wecom = wecom;
    }

    public String getLark() {
        return lark;
    }

    public void setLark(String lark) {
        this.lark = lark;
    }

    public String getGitlab() {
        return gitlab;
    }

    public void setGitlab(String gitlab) {
        this.gitlab = gitlab;
    }

    public String getAdfs() {
      return adfs;
    }

    public void setAdfs(String adfs) {
      this.adfs = adfs;
    }

    public String getBaidu() {
      return baidu;
    }

    public void setBaidu(String baidu) {
      this.baidu = baidu;
    }

    public String getAlipay() {
      return alipay;
    }

    public void setAlipay(String alipay) {
      this.alipay = alipay;
    }

    public String getCasdoor() {
      return casdoor;
    }

    public void setCasdoor(String casdoor) {
      this.casdoor = casdoor;
    }

    public String getInfoflow() {
      return infoflow;
    }

    public void setInfoflow(String infoflow) {
      this.infoflow = infoflow;
    }

    public String getApple() {
      return apple;
    }

    public void setApple(String apple) {
      this.apple = apple;
    }

    public String getAzuread() {
      return azuread;
    }

    public void setAzuread(String azuread) {
      this.azuread = azuread;
    }

    public String getSlack() {
      return slack;
    }

    public void setSlack(String slack) {
      this.slack = slack;
    }

    public String getSteam() {
      return steam;
    }

    public void setSteam(String steam) {
      this.steam = steam;
    }

    public String getBilibili() {
      return bilibili;
    }

    public void setBilibili(String bilibili) {
      this.bilibili = bilibili;
    }

    public String getOkta() {
      return okta;
    }

    public void setOkta(String okta) {
      this.okta = okta;
    }

    public String getDouyin() {
      return douyin;
    }

    public void setDouyin(String douyin) {
      this.douyin = douyin;
    }

    public String getCustom() {
      return custom;
    }

    public void setCustom(String custom) {
      this.custom = custom;
    }

    public String getLdap() {
        return ldap;
    }

    public void setLdap(String ldap) {
        this.ldap = ldap;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public List<CasdoorRole> getRoles() {
        return roles;
    }

    public void setRoles(List<CasdoorRole> roles) {
        this.roles = roles;
    }

    public List<CasdoorPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<CasdoorPermission> permissions) {
        this.permissions = permissions;
    }

    public String getPasswordSalt() {
      return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
      this.passwordSalt = passwordSalt;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    public String getIdCardType() {
      return idCardType;
    }

    public void setIdCardType(String idCardType) {
      this.idCardType = idCardType;
    }

    public String getIdCard() {
      return idCard;
    }

    public void setIdCard(String idCard) {
      this.idCard = idCard;
    }

    public String getGender() {
      return gender;
    }

    public void setGender(String gender) {
      this.gender = gender;
    }

    public String getBirthday() {
      return birthday;
    }

    public void setBirthday(String birthday) {
      this.birthday = birthday;
    }

    public String getEducation() {
      return education;
    }

    public void setEducation(String education) {
      this.education = education;
    }

    public int getKarma() {
      return karma;
    }

    public void setKarma(int karma) {
      this.karma = karma;
    }

    public String getCreatedIp() {
        return createdIp;
    }

    public void setCreatedIp(String createdIp) {
        this.createdIp = createdIp;
    }

    public String getLastSigninTime() {
        return lastSigninTime;
    }

    public void setLastSigninTime(String lastSigninTime) {
        this.lastSigninTime = lastSigninTime;
    }

    public String getLastSigninIp() {
        return lastSigninIp;
    }

    public void setLastSigninIp(String lastSigninIp) {
        this.lastSigninIp = lastSigninIp;
    }

    @Override
    public String toString() {
        return "CasdoorUser{" +
                "owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", updatedTime='" + updatedTime + '\'' +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", password='" + password + '\'' +
                ", passwordSalt='" + passwordSalt + '\'' +
                ", displayName='" + displayName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", permanentAvatar='" + permanentAvatar + '\'' +
                ", email='" + email + '\'' +
                ", emailVerified=" + emailVerified +
                ", phone='" + phone + '\'' +
                ", location='" + location + '\'' +
                ", address=" + Arrays.toString(address) +
                ", affiliation='" + affiliation + '\'' +
                ", title='" + title + '\'' +
                ", idCardType='" + idCardType + '\'' +
                ", idCard='" + idCard + '\'' +
                ", homepage='" + homepage + '\'' +
                ", bio='" + bio + '\'' +
                ", tag='" + tag + '\'' +
                ", region='" + region + '\'' +
                ", language='" + language + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", education='" + education + '\'' +
                ", score=" + score +
                ", karma=" + karma +
                ", ranking=" + ranking +
                ", isDefaultAvatar=" + isDefaultAvatar +
                ", isOnline=" + isOnline +
                ", isAdmin=" + isAdmin +
                ", isGlobalAdmin=" + isGlobalAdmin +
                ", isForbidden=" + isForbidden +
                ", isDeleted=" + isDeleted +
                ", signupApplication='" + signupApplication + '\'' +
                ", hash='" + hash + '\'' +
                ", preHash='" + preHash + '\'' +
                ", createdIp='" + createdIp + '\'' +
                ", lastSigninTime='" + lastSigninTime + '\'' +
                ", lastSigninIp='" + lastSigninIp + '\'' +
                ", github='" + github + '\'' +
                ", google='" + google + '\'' +
                ", qq='" + qq + '\'' +
                ", wechat='" + wechat + '\'' +
                ", facebook='" + facebook + '\'' +
                ", dingtalk='" + dingtalk + '\'' +
                ", weibo='" + weibo + '\'' +
                ", gitee='" + gitee + '\'' +
                ", linkedin='" + linkedin + '\'' +
                ", wecom='" + wecom + '\'' +
                ", lark='" + lark + '\'' +
                ", gitlab='" + gitlab + '\'' +
                ", adfs='" + adfs + '\'' +
                ", baidu='" + baidu + '\'' +
                ", alipay='" + alipay + '\'' +
                ", casdoor='" + casdoor + '\'' +
                ", infoflow='" + infoflow + '\'' +
                ", apple='" + apple + '\'' +
                ", azuread='" + azuread + '\'' +
                ", slack='" + slack + '\'' +
                ", steam='" + steam + '\'' +
                ", bilibili='" + bilibili + '\'' +
                ", okta='" + okta + '\'' +
                ", douyin='" + douyin + '\'' +
                ", custom='" + custom + '\'' +
                ", ldap='" + ldap + '\'' +
                ", properties=" + properties +
                ", roles=" + roles +
                ", permissions=" + permissions +
                '}';
    }
}
