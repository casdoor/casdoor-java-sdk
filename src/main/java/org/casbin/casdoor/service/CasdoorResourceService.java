// Copyright 2021 The casbin Authors. All Rights Reserved.
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
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorResource;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.File;
import java.io.IOException;

public class CasdoorResourceService extends CasdoorService {
    public CasdoorResourceService(CasdoorConfig casdoorConfig) {
        super(casdoorConfig);
    }

    public CasdoorResponse<String> uploadResource(String user, String tag, String parent, String fullFilePath, File file) throws IOException {
        return doPost("upload-resource",
                Map.of("owner", casdoorConfig.getOrganizationName(),
                        "user", user,
                        "application", casdoorConfig.getApplicationName(),
                        "tag", tag,
                        "parent", parent,
                        "fullFilePath", fullFilePath),
                file, new TypeReference<CasdoorResponse<String>>() {});
    }

    public CasdoorResponse<String> deleteResource(String name) throws IOException {
        CasdoorResource casdoorResource = new CasdoorResource(casdoorConfig.getOrganizationName(), name);
        String userStr = objectMapper.writeValueAsString(casdoorResource);
        return doPost("delete-resource", null, userStr, new TypeReference<CasdoorResponse<String>>() {});
    }
}
