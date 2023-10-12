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

public class Cert implements Serializable {
    public String owner;
    public String name;
    public String createdTime;
    public String displayName;
    public String scope;
    public String type;
    public String cryptoAlgorithm;
    public int bitSize;
    public int expireInYears;
    public String certificate;
    public String privateKey;
    public String authorityPublicKey;
    public String authorityRootPublicKey;

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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCryptoAlgorithm() {
        return cryptoAlgorithm;
    }

    public void setCryptoAlgorithm(String cryptoAlgorithm) {
        this.cryptoAlgorithm = cryptoAlgorithm;
    }

    public int getBitSize() {
        return bitSize;
    }

    public void setBitSize(int bitSize) {
        this.bitSize = bitSize;
    }

    public int getExpireInYears() {
        return expireInYears;
    }

    public void setExpireInYears(int expireInYears) {
        this.expireInYears = expireInYears;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getAuthorityPublicKey() {
        return authorityPublicKey;
    }

    public void setAuthorityPublicKey(String authorityPublicKey) {
        this.authorityPublicKey = authorityPublicKey;
    }

    public String getAuthorityRootPublicKey() {
        return authorityRootPublicKey;
    }

    public void setAuthorityRootPublicKey(String authorityRootPublicKey) {
        this.authorityRootPublicKey = authorityRootPublicKey;
    }


}