// Copyright 2026 The Casdoor Authors. All Rights Reserved.
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

import com.fasterxml.jackson.core.type.TypeReference;
import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;

/**
 * MfaService sets up the multi-factor authentication of the users, the MFA types are "app", "email" and "sms".
 */
public class MfaService extends Service {
    public static final String EMAIL = "email";
    public static final String SMS = "sms";
    public static final String APP = "app";

    public MfaService(Config config) {
        super(config);
    }

    /**
     * Starts setting up the MFA of the user, the data of the response contains the secret and the recovery codes.
     */
    public CasdoorResponse<java.util.Map<String, Object>, Object> initiate(String owner, String mfaType, String name) throws IOException {
        return doPostMultipartForm("mfa/setup/initiate", null,
                Map.of("owner", owner, "mfaType", mfaType, "name", name),
                new TypeReference<CasdoorResponse<java.util.Map<String, Object>, Object>>() {
                });
    }

    public CasdoorResponse<Object, Object> verify(String owner, String mfaType, String name, String secret, String passcode) throws IOException {
        return doPostMultipartForm("mfa/setup/verify", null,
                Map.of("owner", owner, "mfaType", mfaType, "name", name, "secret", secret, "passcode", passcode),
                new TypeReference<CasdoorResponse<Object, Object>>() {
                });
    }

    public CasdoorResponse<Object, Object> enable(String owner, String mfaType, String name, String secret, String recoveryCode) throws IOException {
        return doPostMultipartForm("mfa/setup/enable", null,
                Map.of("owner", owner, "mfaType", mfaType, "name", name, "secret", secret, "recoveryCode", recoveryCode),
                new TypeReference<CasdoorResponse<Object, Object>>() {
                });
    }

    public CasdoorResponse<Object, Object> setPreferred(String owner, String mfaType, String name, String secret) throws IOException {
        return doPostMultipartForm("set-preferred-mfa", null,
                Map.of("owner", owner, "mfaType", mfaType, "name", name, "secret", secret),
                new TypeReference<CasdoorResponse<Object, Object>>() {
                });
    }

    /**
     * Deletes all the MFA settings of the user.
     */
    public CasdoorResponse<Object, Object> delete(String owner, String name) throws IOException {
        return doPost("delete-mfa", Map.of("owner", owner, "name", name), "",
                new TypeReference<CasdoorResponse<Object, Object>>() {
                });
    }
}
