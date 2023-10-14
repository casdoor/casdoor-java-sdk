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

    public Cert() {
    }

    public String privateKey;
    public String authorityPublicKey;
    public String authorityRootPublicKey;

    public Cert(String owner, String name, String createdTime, String displayName, String scope, String type, String cryptoAlgorithm, int bitSize, int expireInYears) {
        this.owner = owner;
        this.name = name;
        this.createdTime = createdTime;
        this.displayName = displayName;
        this.scope = scope;
        this.type = type;
        this.cryptoAlgorithm = cryptoAlgorithm;
        this.bitSize = bitSize;
        this.expireInYears = expireInYears;
    }

}