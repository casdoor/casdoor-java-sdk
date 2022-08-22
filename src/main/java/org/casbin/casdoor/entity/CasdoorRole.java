// Copyright 2022 The casbin Authors. All Rights Reserved.
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
import java.util.Arrays;

/**
 * Role has the same definition as https://github.com/casbin/casdoor/blob/master/object/role.go#L24
 */
public class CasdoorRole implements Serializable {
    private String owner;
    private String name;
    private String createdTime;
    private String displayName;
    private String[] users;
    private String[] roles;
    private boolean isEnabled;

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

    public String[] getUsers() {
        return users;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override public String toString() {
        return "CasdoorRole{" +
            "owner='" + owner + '\'' +
            ", name='" + name + '\'' +
            ", createdTime='" + createdTime + '\'' +
            ", displayName='" + displayName + '\'' +
            ", users=" + Arrays.toString(users) +
            ", roles=" + Arrays.toString(roles) +
            ", isEnabled=" + isEnabled +
            '}';
    }
}
