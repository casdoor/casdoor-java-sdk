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

package org.casbin.casdoor.service;

import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.Group;
import org.casbin.casdoor.util.GroupOperations;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

import java.io.IOException;

public class GroupService extends Service {

    public GroupService(Config config) {
        super(config);
    }

    public Group getGroup(String name) throws IOException {
        CasdoorResponse<Group, Object> response = doGet("get-group",
                Map.of("id", config.organizationName + "/" + name), new TypeReference<CasdoorResponse<Group, Object>>() {
                });
        return response.getData();
    }

    public List<Group> getGroups() throws IOException {
        CasdoorResponse<List<Group>, Object> response = doGet("get-groups",
                Map.of("owner", config.organizationName), new TypeReference<CasdoorResponse<List<Group>, Object>>() {
                });
        return response.getData();
    }

    public List<Group> getGroups(Boolean withTree) throws IOException {
        if (!withTree) {
            return getGroups();
        }

        CasdoorResponse<List<Group>, Object> response = doGet("get-groups",
                Map.of("owner", config.organizationName, "withTree", "true"), new TypeReference<CasdoorResponse<List<Group>, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> addGroup(Group group) throws IOException {
        return modifyGroup(GroupOperations.ADD_GROUP, group, null);
    }

    public CasdoorResponse<String, Object> deleteGroup(Group group) throws IOException {
        return modifyGroup(GroupOperations.DELETE_GROUP, group, null);
    }

    public CasdoorResponse<String, Object> updateGroup(Group group) throws IOException {
        return modifyGroup(GroupOperations.UPDATE_GROUP, group, null);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyGroup(GroupOperations method, Group group, java.util.Map<String, String> queryMap) throws IOException {
        String id = group.owner + "/" + group.name;
        group.owner = config.organizationName;
        String payload = objectMapper.writeValueAsString(group);
        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}
