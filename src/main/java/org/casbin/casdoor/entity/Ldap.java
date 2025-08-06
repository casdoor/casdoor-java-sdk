// Copyright 2025 The Casdoor Authors. All Rights Reserved.
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

import java.util.List;

public class Ldap {
    public String id;
    public String owner;
    public String createdTime;
    public String serverName;
    public String host;
    public int port;
    public boolean enableSsl;
    public String username;
    public String password;
    public String baseDn;
    public String filter;
    public List<String> filterFields;
    public String defaultGroup;
    public int autoSync;
    public String lastSync;

    public Ldap(String owner, String serverName, String host, int port, String username, String password, String baseDn, int autoSync) {
        this.owner = owner;
        this.serverName = serverName;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.baseDn = baseDn;
        this.autoSync = autoSync;
    }
    public Ldap() {
    }
}
