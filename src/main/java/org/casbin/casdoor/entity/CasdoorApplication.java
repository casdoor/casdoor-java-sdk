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


public class CasdoorApplication implements Serializable {
    private String owner;
    private String name;
    private String createdTime;
    private String displayName;
    private String logo;
    private String homepageUrl;
    private String description;
    private String organization;
    private String cert;
    private boolean enablePassword;
    private boolean enableSignUp;
    private boolean enableSigninSession;
    private boolean enableAutoSignin;
    private boolean enableCodeSignin;
    private boolean enableSamlCompress;
    private boolean enableWebAuthn;
    private boolean enableLinkWithEmail;
    private String orgChoiceMode;
    private String samlReplyUrl;
    private List<String> grantTypes;
    private List<String> tags;
    private String clientId;
    private String clientSecret;
    private List<String> redirectUris;
    private String tokenFormat;
    private int expireInHours;
    private int refreshExpireInHours;
    private String signupUrl;
    private String signinUrl;
    private String forgetUrl;
    private String affiliationUrl;
    private String termsOfUse;
    private String signupHtml;
    private String signinHtml;
    private String formCss;
    private String formCssMobile;
    private int formOffset;
    private String formSideHtml;
    private String formBackgroundUrl;

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getHomepageUrl() {
        return homepageUrl;
    }

    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public boolean isEnablePassword() {
        return enablePassword;
    }

    public void setEnablePassword(boolean enablePassword) {
        this.enablePassword = enablePassword;
    }

    public boolean isEnableSignUp() {
        return enableSignUp;
    }

    public void setEnableSignUp(boolean enableSignUp) {
        this.enableSignUp = enableSignUp;
    }

    public boolean isEnableSigninSession() {
        return enableSigninSession;
    }

    public void setEnableSigninSession(boolean enableSigninSession) {
        this.enableSigninSession = enableSigninSession;
    }

    public boolean isEnableAutoSignin() {
        return enableAutoSignin;
    }

    public void setEnableAutoSignin(boolean enableAutoSignin) {
        this.enableAutoSignin = enableAutoSignin;
    }

    public boolean isEnableCodeSignin() {
        return enableCodeSignin;
    }

    public void setEnableCodeSignin(boolean enableCodeSignin) {
        this.enableCodeSignin = enableCodeSignin;
    }

    public boolean isEnableSamlCompress() {
        return enableSamlCompress;
    }

    public void setEnableSamlCompress(boolean enableSamlCompress) {
        this.enableSamlCompress = enableSamlCompress;
    }

    public boolean isEnableWebAuthn() {
        return enableWebAuthn;
    }

    public void setEnableWebAuthn(boolean enableWebAuthn) {
        this.enableWebAuthn = enableWebAuthn;
    }

    public boolean isEnableLinkWithEmail() {
        return enableLinkWithEmail;
    }

    public void setEnableLinkWithEmail(boolean enableLinkWithEmail) {
        this.enableLinkWithEmail = enableLinkWithEmail;
    }

    public String getOrgChoiceMode() {
        return orgChoiceMode;
    }

    public void setOrgChoiceMode(String orgChoiceMode) {
        this.orgChoiceMode = orgChoiceMode;
    }

    public String getSamlReplyUrl() {
        return samlReplyUrl;
    }

    public void setSamlReplyUrl(String samlReplyUrl) {
        this.samlReplyUrl = samlReplyUrl;
    }

    public List<String> getGrantTypes() {
        return grantTypes;
    }

    public void setGrantTypes(List<String> grantTypes) {
        this.grantTypes = grantTypes;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public List<String> getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(List<String> redirectUris) {
        this.redirectUris = redirectUris;
    }

    public String getTokenFormat() {
        return tokenFormat;
    }

    public void setTokenFormat(String tokenFormat) {
        this.tokenFormat = tokenFormat;
    }

    public int getExpireInHours() {
        return expireInHours;
    }

    public void setExpireInHours(int expireInHours) {
        this.expireInHours = expireInHours;
    }

    public int getRefreshExpireInHours() {
        return refreshExpireInHours;
    }

    public void setRefreshExpireInHours(int refreshExpireInHours) {
        this.refreshExpireInHours = refreshExpireInHours;
    }

    public String getSignupUrl() {
        return signupUrl;
    }

    public void setSignupUrl(String signupUrl) {
        this.signupUrl = signupUrl;
    }

    public String getSigninUrl() {
        return signinUrl;
    }

    public void setSigninUrl(String signinUrl) {
        this.signinUrl = signinUrl;
    }

    public String getForgetUrl() {
        return forgetUrl;
    }

    public void setForgetUrl(String forgetUrl) {
        this.forgetUrl = forgetUrl;
    }

    public String getAffiliationUrl() {
        return affiliationUrl;
    }

    public void setAffiliationUrl(String affiliationUrl) {
        this.affiliationUrl = affiliationUrl;
    }

    public String getTermsOfUse() {
        return termsOfUse;
    }

    public void setTermsOfUse(String termsOfUse) {
        this.termsOfUse = termsOfUse;
    }

    public String getSignupHtml() {
        return signupHtml;
    }

    public void setSignupHtml(String signupHtml) {
        this.signupHtml = signupHtml;
    }

    public String getSigninHtml() {
        return signinHtml;
    }

    public void setSigninHtml(String signinHtml) {
        this.signinHtml = signinHtml;
    }

    public String getFormCss() {
        return formCss;
    }

    public void setFormCss(String formCss) {
        this.formCss = formCss;
    }

    public String getFormCssMobile() {
        return formCssMobile;
    }

    public void setFormCssMobile(String formCssMobile) {
        this.formCssMobile = formCssMobile;
    }

    public int getFormOffset() {
        return formOffset;
    }

    public void setFormOffset(int formOffset) {
        this.formOffset = formOffset;
    }

    public String getFormSideHtml() {
        return formSideHtml;
    }

    public void setFormSideHtml(String formSideHtml) {
        this.formSideHtml = formSideHtml;
    }

    public String getFormBackgroundUrl() {
        return formBackgroundUrl;
    }

    public void setFormBackgroundUrl(String formBackgroundUrl) {
        this.formBackgroundUrl = formBackgroundUrl;
    }

    @Override
    public String toString() {
        return "CasdoorApplication{" +
                "owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", displayName='" + displayName + '\'' +
                ", logo='" + logo + '\'' +
                ", homepageUrl='" + homepageUrl + '\'' +
                ", description='" + description + '\'' +
                ", organization='" + organization + '\'' +
                ", cert='" + cert + '\'' +
                ", enablePassword=" + enablePassword +
                ", enableSignUp=" + enableSignUp +
                ", enableSigninSession=" + enableSigninSession +
                ", enableAutoSignin=" + enableAutoSignin +
                ", enableCodeSignin=" + enableCodeSignin +
                ", enableSamlCompress=" + enableSamlCompress +
                ", enableWebAuthn=" + enableWebAuthn +
                ", enableLinkWithEmail=" + enableLinkWithEmail +
                ", orgChoiceMode='" + orgChoiceMode + '\'' +
                ", samlReplyUrl='" + samlReplyUrl + '\'' +
                ", grantTypes=" + grantTypes +
                ", tags=" + tags +
                ", clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", redirectUris=" + redirectUris +
                ", tokenFormat='" + tokenFormat + '\'' +
                ", expireInHours=" + expireInHours +
                ", refreshExpireInHours=" + refreshExpireInHours +
                ", signupUrl='" + signupUrl + '\'' +
                ", signinUrl='" + signinUrl + '\'' +
                ", forgetUrl='" + forgetUrl + '\'' +
                ", affiliationUrl='" + affiliationUrl + '\'' +
                ", termsOfUse='" + termsOfUse + '\'' +
                ", signupHtml='" + signupHtml + '\'' +
                ", signinHtml='" + signinHtml + '\'' +
                ", formCss='" + formCss + '\'' +
                ", formCssMobile='" + formCssMobile + '\'' +
                ", formOffset=" + formOffset +
                ", formSideHtml='" + formSideHtml + '\'' +
                ", formBackgroundUrl='" + formBackgroundUrl + '\'' +
                '}';
    }
}