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

import java.lang.reflect.InvocationTargetException;
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

    public CasdoorUser parseJwtToken(String token) throws ParseException, InvocationTargetException, IllegalAccessException {
        SignedJWT parseJWT = SignedJWT.parse(token);
        Map<String, Object> claims =  parseJWT.getJWTClaimsSet().getClaims();
        CasdoorUser casdoorUser = new CasdoorUser();
        BeanUtils.copyProperties(casdoorUser,claims);
        return casdoorUser;
    }
}
