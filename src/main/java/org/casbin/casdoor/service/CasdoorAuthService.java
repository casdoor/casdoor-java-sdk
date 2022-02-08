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

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.beanutils.BeanUtils;
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
import org.casbin.casdoor.exception.CasdoorException;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Map;

public class CasdoorAuthService {
    private final CasdoorConfig casdoorConfig;

    public CasdoorAuthService(CasdoorConfig casdoorConfig){
        this.casdoorConfig = casdoorConfig;
    }

    public String getOAuthToken(String code, String state) throws OAuthSystemException, OAuthProblemException {
        OAuthClientRequest oAuthClientRequest =
                OAuthClientRequest
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
    }

    public CasdoorUser parseJwtToken(String token) throws CasdoorException, InvocationTargetException, IllegalAccessException {
        // parse jwt token
        SignedJWT parseJWT = null;
        Map<String, Object> claims = null;
        try {
            parseJWT = SignedJWT.parse(token);
            claims = parseJWT.getJWTClaimsSet().getClaims();
        } catch (ParseException e) {
            throw CasdoorException.PARSE_JWT_TOKEN_EXCEPTION;
        }

        // verify the jwt public key
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(casdoorConfig.getJwtPublicKey().getBytes()));
            RSAPublicKey publicKey = (RSAPublicKey) cert.getPublicKey();
            JWSVerifier verifier = new RSASSAVerifier(publicKey);
            boolean verify = parseJWT.verify(verifier);
            if (!verify) {
                throw CasdoorException.VERIFY_JWT_PUBLIC_KEY_EXCEPTION;
            }
        } catch (CertificateException | JOSEException e) {
            throw CasdoorException.VERIFY_JWT_PUBLIC_KEY_EXCEPTION;
        }

        // convert to CasdoorUser
        CasdoorUser casdoorUser = new CasdoorUser();
        BeanUtils.copyProperties(casdoorUser, claims);
        return casdoorUser;
    }

    public String getSigninUrl(String redirectUrl) throws UnsupportedEncodingException {
        String scope = "read";
        String state = casdoorConfig.getApplicationName();
        return String.format("%s/login/oauth/authorize?client_id=%s&response_type=code&redirect_uri=%s&scope=%s&state=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getClientId(),
                URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8.toString()),
                scope, state);
    }

    public String getSignupUrl() throws UnsupportedEncodingException {
        return getSignupUrl(true, "");
    }

    public String getSignupUrl(String redirectUrl) throws UnsupportedEncodingException {
        return getSignupUrl(false, redirectUrl);
    }

    private String getSignupUrl(boolean enablePassword, String redirectUrl) throws UnsupportedEncodingException {
        if (enablePassword) {
            return String.format("%s/signup/%s", casdoorConfig.getEndpoint(), casdoorConfig.getApplicationName());
        } else {
            return getSigninUrl(redirectUrl).replace("/login/oauth/authorize", "/signup/oauth/authorize");
        }
    }

    public String getUserProfileUrl(String username, String accessToken) {
        String param = "";
        if (accessToken != null && accessToken.trim().length() != 0) {
            param = "?access_token=" + accessToken;
        }
        return String.format("%s/users/%s/%s%s", casdoorConfig.getEndpoint(), casdoorConfig.getOrganizationName(), username, param);
    }

    public String getMyProfileUrl(String accessToken) {
        String param = "";
        if (accessToken != null && accessToken.trim().length() != 0) {
            param = "?access_token=" + accessToken;
        }
        return String.format("%s/account%s", casdoorConfig.getEndpoint(), param);
    }
}
