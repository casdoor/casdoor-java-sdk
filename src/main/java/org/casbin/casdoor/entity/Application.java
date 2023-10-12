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

    public Application() {
    }

    /**
     * create test constructor
     */
    public Application(String owner, String name, String createdTime, String displayName, String logo, String homepageUrl, String description, String organization) {
        this.owner = owner;
        this.name = name;
        this.createdTime = createdTime;
        this.displayName = displayName;
        this.logo = logo;
        this.homepageUrl = homepageUrl;
        this.description = description;
        this.organization = organization;
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


}