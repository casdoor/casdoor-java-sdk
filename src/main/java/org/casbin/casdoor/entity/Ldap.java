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
    private String id;
    private String owner;
    private String createdTime;
    private String serverName;
    private String host;
    private int port;
    private boolean enableSsl;
    private String username;
    private String password;
    private String baseDn;
    private String filter;
    private List<String> filterFields;
    private String defaultGroup;
    private int autoSync;
    private String lastSync;

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
