// Copyright 2020 The casbin Authors. All Rights Reserved.
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

package org.casbin.casdoor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.regex.Pattern;

public class Casdoor {

    final private String endpoint;
    final private ObjectMapper objectMapper = new ObjectMapper();
    private User thisUser;

    public Casdoor(String endpoint) throws CasdoorException {
        boolean isValid = Pattern.matches("((https)|(http))://.*[^/]", endpoint);
        if (!isValid) throw CasdoorException.ENDPOINT_EXCEPTION;
        this.endpoint = endpoint;
    }

    public User[] getUsers(String... owner) throws CasdoorException {
        String targetOwner;
        if (owner.length > 0) targetOwner = owner[0];
        else if (thisUser != null) targetOwner = thisUser.getName();
        else targetOwner = "";

        String targetUrl = String.format("%s/api/get-users?owner=%s", endpoint, targetOwner);
        String response = HttpClient.get(targetUrl);
        if (response == null) throw CasdoorException.NETWORK_EXCEPTION;
        try {
            return objectMapper.readValue(response, User[].class);
        } catch (JsonProcessingException e) {
            throw CasdoorException.ENDPOINT_EXCEPTION;
        }
    }

    public Organization[] getOrganizations(String... owner) throws CasdoorException {
        String targetOwner;
        if (owner.length > 0) targetOwner = owner[0];
        else if (thisUser != null) targetOwner = thisUser.getName();
        else targetOwner = "";

        String targetUrl = String.format("%s/api/get-organizations?owner=%s", endpoint, targetOwner);
        String response = HttpClient.get(targetUrl);
        if (response == null) throw CasdoorException.NETWORK_EXCEPTION;
        try {
            return objectMapper.readValue(response, Organization[].class);
        } catch (JsonProcessingException e) {
            throw CasdoorException.ENDPOINT_EXCEPTION;
        }
    }
}
