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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Permission has the same definition as https://github.com/casbin/casdoor/blob/master/object/permission.go#L29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CasdoorPermission implements Serializable {
    private String owner;
    private String name;
    private String createdTime;
    private String displayName;
    private String[] users;
    private String[] roles;
    private String model;
    private String resourceType;
    private String[] resources;
    private String[] actions;
    private String effect;
    private boolean isEnabled;
}
