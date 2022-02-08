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

package org.casbin.casdoor.exception;

public class CasdoorException extends Exception {

    public static CasdoorException NETWORK_EXCEPTION = new CasdoorException("Connection timeout.");
    public static CasdoorException ENDPOINT_EXCEPTION = new CasdoorException("Unknown response data structure from endpoint. Please check whether the endpoint is a Casdoor instance, and is the same version of the SDK.");
    public static CasdoorException PARSE_JWT_TOKEN_EXCEPTION = new CasdoorException("Cannot parse jwt token.");
    public static CasdoorException VERIFY_JWT_PUBLIC_KEY_EXCEPTION = new CasdoorException("Cannot verify signature.");

    private CasdoorException(String reason) {
        super(reason);
    }
}
