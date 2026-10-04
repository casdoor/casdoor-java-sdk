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

package org.casbin.casdoor.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * LdapUser has the same definition as LdapUser of casdoor-go-sdk.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LdapUser implements Serializable {
    public String uidNumber;
    public String uid;
    public String cn;
    public String gidNumber;
    public String uuid;
    public String userPrincipalName;
    public String displayName;
    @JsonProperty("Mail")
    public String mail;
    public String email;
    @JsonProperty("EmailAddress")
    public String emailAddress;
    @JsonProperty("TelephoneNumber")
    public String telephoneNumber;
    public String mobile;
    @JsonProperty("MobileTelephoneNumber")
    public String mobileTelephoneNumber;
    @JsonProperty("RegisteredAddress")
    public String registeredAddress;
    @JsonProperty("PostalAddress")
    public String postalAddress;
    public String country;
    public String countryName;
    public String groupId;
    public String address;
    public String[] memberOf;
    public java.util.Map<String, String> attributes;

    public LdapUser() {
    }
}
