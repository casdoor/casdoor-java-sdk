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

import com.fasterxml.jackson.core.type.TypeReference;
import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.Resource;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.File;
import java.io.IOException;

public class ResourceService extends Service {
    public ResourceService(Config config) {
        super(config);
    }

    public CasdoorResponse<String, Object> uploadResource(String user, String tag, String parent, String fullFilePath, File file) throws IOException {
        return doPost("upload-resource",
                Map.of("owner", getConfig().getOrganizationName(),
                        "user", user,
                        "application", getConfig().getApplicationName(),
                        "tag", tag,
                        "parent", parent,
                        "fullFilePath", fullFilePath),
                file, new TypeReference<CasdoorResponse<String, Object>>() {});
    }

    public CasdoorResponse<String, Object> deleteResource(String name) throws IOException {
        Resource resource = new Resource(getConfig().getOrganizationName(), name);
        String userStr = getObjectMapper().writeValueAsString(resource);
        return doPost("delete-resource", null, userStr, new TypeReference<CasdoorResponse<String, Object>>() {});
    }
}