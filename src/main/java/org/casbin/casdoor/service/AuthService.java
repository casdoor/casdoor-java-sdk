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

package org.casbin.casdoor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.OAuthToken;
import org.casbin.casdoor.entity.User;
import org.casbin.casdoor.exception.AuthException;
import org.casbin.casdoor.util.QueryUtils;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.casbin.casdoor.util.http.HttpClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.interfaces.ECPublicKey;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public class AuthService extends Service {
    public AuthService(Config config) {
        super(config);
    }

    public String getOAuthToken(String code, String state) {
        try {
            OAuthClientRequest oAuthClientRequest = OAuthClientRequest
                    .tokenLocation(String.format("%s/api/login/oauth/access_token", config.endpoint))
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(config.clientId)
                    .setClientSecret(config.clientSecret)
                    .setRedirectURI(String.format("%s/api/login/oauth/authorize", config.endpoint))
                    .setCode(code)
                    .buildQueryMessage();
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(oAuthClientRequest, OAuth.HttpMethod.POST);
            return oAuthResponse.getAccessToken();
        } catch (OAuthSystemException | OAuthProblemException e) {
            throw new AuthException("Cannot get OAuth token.", e);
        }
    }

    /**
     * Gets the OAuth token with the Resource Owner Password Credentials grant.
     * @param username the name of the user
     * @param password the password of the user
     * @return the token
     */
    public OAuthToken getOAuthTokenByPassword(String username, String password) {
        java.util.Map<String, String> form = new HashMap<>();
        form.put("grant_type", "password");
        form.put("client_id", config.clientId);
        form.put("client_secret", config.clientSecret);
        form.put("username", username);
        form.put("password", password);
        return requestOAuthToken("access_token", form);
    }

    /**
     * Signs in as any user of the organization with the organization's master password.
     */
    public OAuthToken impersonateUser(String username, String masterPassword) {
        return getOAuthTokenByPassword(username, masterPassword);
    }

    /**
     * Gets a new token with the refresh token.
     * @param refreshToken the refresh token
     * @return the new token
     */
    public OAuthToken refreshOAuthToken(String refreshToken) {
        java.util.Map<String, String> form = new HashMap<>();
        form.put("grant_type", "refresh_token");
        form.put("client_id", config.clientId);
        form.put("client_secret", config.clientSecret);
        form.put("refresh_token", refreshToken);
        return requestOAuthToken("refresh_token", form);
    }

    private OAuthToken requestOAuthToken(String action, java.util.Map<String, String> form) {
        String url = String.format("%s/api/login/oauth/%s", config.endpoint, action);
        try {
            OAuthToken token = objectMapper.readValue(HttpClient.postForm(url, form, config.customHeaders, false), OAuthToken.class);
            if (token.error != null && !token.error.isEmpty()) {
                throw new AuthException(token.errorDescription == null || token.errorDescription.isEmpty() ? token.error : token.errorDescription);
            }
            if (token.accessToken != null && token.accessToken.startsWith("error:")) {
                throw new AuthException(token.accessToken.substring("error:".length()).trim());
            }
            return token;
        } catch (IOException e) {
            throw new AuthException("Cannot get OAuth token.", e);
        }
    }

    public User parseJwtToken(String token) {
        // parse jwt token
        SignedJWT parseJwt = null;
        try {
            parseJwt = SignedJWT.parse(token);
        } catch (ParseException e) {
            throw new AuthException("Cannot parse jwt token.", e);
        }
        // verify the jwt public key
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(config.certificate.getBytes()));
            PublicKey publicKey = cert.getPublicKey();
            JWSAlgorithm alg = parseJwt.getHeader().getAlgorithm();
            JWSVerifier verifier;
            if (JWSAlgorithm.Family.RSA.contains(alg)) {
                if (!(publicKey instanceof RSAPublicKey)) {
                    throw new AuthException("Public key type mismatch for RSA algorithm.");
                }
                verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
            } else if (JWSAlgorithm.Family.EC.contains(alg)) {
                if (!(publicKey instanceof ECPublicKey)) {
                    throw new AuthException("Public key type mismatch for EC algorithm.");
                }
                verifier = new ECDSAVerifier((ECPublicKey) publicKey);
            } else {
                throw new AuthException("Unsupported jwt algorithm: " + alg.getName());
            }
            boolean verify = parseJwt.verify(verifier);
            if (!verify) {
                throw new AuthException("Cannot verify signature.");
            }
        } catch (CertificateException | JOSEException e) {
            throw new AuthException("Cannot verify signature.", e);
        }

        // read "access_token" from payload and convert to CasdoorUser
        try {
            JWTClaimsSet claimsSet = parseJwt.getJWTClaimsSet();
            String userJson = claimsSet == null ? null : claimsSet.toString();

            if (userJson == null || userJson.isEmpty()) {
                throw new AuthException("Cannot get claims from JWT payload");
            }

            // check if the token has expired
            Date expireTime = claimsSet.getExpirationTime();
            if (expireTime.before(new Date())) {
                throw new AuthException("The token has expired");
            }

            return objectMapper.readValue(userJson, User.class);
        } catch (JsonProcessingException | java.text.ParseException e) {
            throw new AuthException("Cannot convert claims to User", e);
        }
    }

    public String getSigninUrl(String redirectUrl) {
        return this.getSigninUrl(redirectUrl, config.applicationName);
    }

    public String getSigninUrl(String redirectUrl, String state) {
        String scope = "read";
        try {
            return String.format("%s/login/oauth/authorize?client_id=%s&response_type=code&redirect_uri=%s&scope=%s&state=%s",
                    config.endpoint, config.clientId,
                    URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8.toString()),
                    scope, state);
        } catch (UnsupportedEncodingException e) {
            throw new AuthException(e);
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
            return String.format("%s/signup/%s", config.endpoint, config.applicationName);
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
            return String.format("%s/account%s", config.endpoint, params.size() == 0 ? "" : "?" + QueryUtils.buildQuery(params));
        } else {
            return String.format("%s/users/%s/%s%s", config.endpoint, config.organizationName, username, params.size() == 0 ? "" : "?" + QueryUtils.buildQuery(params));
        }
    }

    public String getMyProfileUrl(String accessToken) {
        return this.getMyProfileUrl(accessToken, null);
    }

    public String getMyProfileUrl(String accessToken, String returnUrl) {
        return this.getUserProfileUrl(null, accessToken, returnUrl);
    }

    public String getLogoutUrl(String idToken, String postLogoutRedirectUri) {
        return this.getLogoutUrl(idToken, postLogoutRedirectUri, null);
    }

    /**
     * Get the OIDC RP-Initiated Logout URL of Casdoor ("/api/logout"). Redirect the user's browser
     * to it to end the user's Casdoor session, then Casdoor redirects back to postLogoutRedirectUri,
     * which must be in the application's allowed Redirect URI list.
     *
     * @param idToken the ID token (or access token) returned at login, sent as "id_token_hint", can be null
     * @param postLogoutRedirectUri where to go after logout, can be null
     * @param state an opaque value passed back to postLogoutRedirectUri, can be null
     * @return the logout URL
     */
    public String getLogoutUrl(String idToken, String postLogoutRedirectUri, String state) {
        LinkedHashMap<String, Serializable> params = new LinkedHashMap<>();
        try {
            String charset = StandardCharsets.UTF_8.toString();
            if (idToken != null && idToken.trim().length() > 0) params.put("id_token_hint", URLEncoder.encode(idToken, charset));
            if (postLogoutRedirectUri != null && postLogoutRedirectUri.trim().length() > 0) {
                params.put("post_logout_redirect_uri", URLEncoder.encode(postLogoutRedirectUri, charset));
                params.put("client_id", URLEncoder.encode(config.clientId, charset));
            }
            if (state != null && state.trim().length() > 0) params.put("state", URLEncoder.encode(state, charset));
        } catch (UnsupportedEncodingException e) {
            throw new AuthException(e);
        }
        return String.format("%s/api/logout%s", config.endpoint, params.size() == 0 ? "" : "?" + QueryUtils.buildQuery(params));
    }

    /**
     * Log out the user that owns the accessToken from all applications and all devices (single sign-out)
     * by calling Casdoor's "/api/sso-logout" API: all the user's sessions are deleted and all the access
     * tokens issued to the user are expired.
     *
     * @param accessToken the user's access token returned by getOAuthToken()
     */
    public void logout(String accessToken) {
        this.ssoLogout(accessToken, true);
    }

    /**
     * Like logout(), but only end the session that the accessToken belongs to,
     * so the user stays signed in on other devices and browsers.
     *
     * @param accessToken the user's access token returned by getOAuthToken()
     */
    public void logoutCurrentSession(String accessToken) {
        this.ssoLogout(accessToken, false);
    }

    private void ssoLogout(String accessToken, boolean logoutAll) {
        if (accessToken == null || accessToken.trim().length() == 0) {
            throw new AuthException("The accessToken should not be empty.");
        }

        String url = String.format("%s/api/sso-logout?logoutAll=%s", config.endpoint, logoutAll);
        try {
            // "/api/sso-logout" identifies the user by their own access token,
            // so the Bearer token is used here instead of the application's Basic Auth
            String response = HttpClient.postForm(url, new HashMap<>(), "Bearer " + accessToken);
            CasdoorResponse<Object, Object> resp = objectMapper.readValue(response, new TypeReference<CasdoorResponse<Object, Object>>() {});
            if (!Objects.equals(resp.getStatus(), "ok")) {
                throw new AuthException(String.format("Cannot logout: %s", resp.getMsg()));
            }
        } catch (IOException e) {
            throw new AuthException("Cannot logout.", e);
        }
    }
}
