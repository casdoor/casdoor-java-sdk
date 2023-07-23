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

package org.casbin.casdoor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorUser;
import org.casbin.casdoor.exception.CasdoorAuthException;
import org.casbin.casdoor.util.QueryUtils;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.LinkedHashMap;

public class CasdoorAuthService extends CasdoorService {
    public CasdoorAuthService(CasdoorConfig casdoorConfig) {
        super(casdoorConfig);
    }

    public String getOAuthToken(String code, String state) {
        try {
            OAuthClientRequest oAuthClientRequest = OAuthClientRequest
                    .tokenLocation(String.format("%s/api/login/oauth/access_token", casdoorConfig.getEndpoint()))
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(casdoorConfig.getClientId())
                    .setClientSecret(casdoorConfig.getClientSecret())
                    .setRedirectURI(String.format("%s/api/login/oauth/authorize", casdoorConfig.getEndpoint()))
                    .setCode(code)
                    .buildQueryMessage();
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(oAuthClientRequest, OAuth.HttpMethod.POST);
            return oAuthResponse.getAccessToken();
        } catch (OAuthSystemException | OAuthProblemException e) {
            throw new CasdoorAuthException("Cannot get OAuth token.", e);
        }
    }

    public CasdoorUser parseJwtToken(String token) {
        // parse jwt token
        SignedJWT parseJwt = null;
        try {
            parseJwt = SignedJWT.parse(token);
        } catch (ParseException e) {
            throw new CasdoorAuthException("Cannot parse jwt token.", e);
        }
        // verify the jwt public key
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(casdoorConfig.getCertificate().getBytes()));
            RSAPublicKey publicKey = (RSAPublicKey) cert.getPublicKey();
            JWSVerifier verifier = new RSASSAVerifier(publicKey);
            boolean verify = parseJwt.verify(verifier);
            if (!verify) {
                throw new CasdoorAuthException("Cannot verify signature.");
            }
        } catch (CertificateException | JOSEException e) {
            throw new CasdoorAuthException("Cannot verify signature.", e);
        }

        // convert to CasdoorUser
        try {
            Payload payloadJson = parseJwt.getPayload();
            return objectMapper.readValue(payloadJson.toString(), CasdoorUser.class);
        } catch (JsonProcessingException e) {
            throw new CasdoorAuthException("Cannot convert claims to CasdoorUser", e);
        }
    }

    public String getSigninUrl(String redirectUrl) {
        return this.getSigninUrl(redirectUrl, casdoorConfig.getApplicationName());
    }

    public String getSigninUrl(String redirectUrl, String state) {
        String scope = "read";
        try {
            return String.format("%s/login/oauth/authorize?client_id=%s&response_type=code&redirect_uri=%s&scope=%s&state=%s",
                    casdoorConfig.getEndpoint(), casdoorConfig.getClientId(),
                    URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8.toString()),
                    scope, state);
        } catch (UnsupportedEncodingException e) {
            throw new CasdoorAuthException(e);
        }
    }

    public String getSignupUrl() {
        return getSignupUrl(true, "");
    }

    public String getSignupUrl(String redirectUrl) {
        return getSignupUrl(false, redirectUrl);
    }

    private String getSignupUrl(boolean enablePassword, String redirectUrl) {
        if (enablePassword) {
            return String.format("%s/signup/%s", casdoorConfig.getEndpoint(), casdoorConfig.getApplicationName());
        } else {
            return getSigninUrl(redirectUrl).replace("/login/oauth/authorize", "/signup/oauth/authorize");
        }
    }

    public String getUserProfileUrl(String username, String accessToken) {
        return this.getUserProfileUrl(username, accessToken, null);
    }

    public String getUserProfileUrl(String username, String accessToken, String returnUrl) {
        LinkedHashMap<String, Serializable> params = new LinkedHashMap<>();
        if (accessToken != null && accessToken.trim().length() > 0) params.put("access_token", accessToken);
        if (returnUrl != null && returnUrl.trim().length() > 0) params.put("returnUrl", returnUrl);
        if (username == null || username.trim().length() == 0) {
            return String.format("%s/account%s", casdoorConfig.getEndpoint(), params.size() == 0 ? "" : "?" + QueryUtils.buildQuery(params));
        } else {
            return String.format("%s/users/%s/%s%s", casdoorConfig.getEndpoint(), casdoorConfig.getOrganizationName(), username, params.size() == 0 ? "" : "?" + QueryUtils.buildQuery(params));
        }
    }

    public String getMyProfileUrl(String accessToken) {
        return this.getMyProfileUrl(accessToken, null);
    }

    public String getMyProfileUrl(String accessToken, String returnUrl) {
        return this.getUserProfileUrl(null, accessToken, returnUrl);
    }
}
