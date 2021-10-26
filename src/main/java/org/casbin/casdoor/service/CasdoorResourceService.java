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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorResource;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.casbin.casdoor.util.http.HttpClient;

import java.io.File;
import java.io.IOException;

public class CasdoorResourceService {

    private final CasdoorConfig casdoorConfig;
    final private ObjectMapper objectMapper = new ObjectMapper();

    public CasdoorResourceService(CasdoorConfig casdoorConfig) {
        this.casdoorConfig = casdoorConfig;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public CasdoorResponse uploadResource(String user, String tag, String parent, String fullFilePath, File file) throws IOException {
        String targetUrl = String.format("%s/api/upload-resource?owner=%s&user=%s&application=%s&tag=%s&parent=%s&fullFilePath=%s&clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getOrganizationName(),
                user, casdoorConfig.getApplicationName(), tag, parent, fullFilePath,
                casdoorConfig.getClientId(), casdoorConfig.getClientSecret());
        String responseStr = HttpClient.postFile(targetUrl, file);
        return objectMapper.readValue(responseStr, CasdoorResponse.class);
    }

    public CasdoorResponse deleteResource(String name) throws IOException {
        String targetUrl = String.format("%s/api/delete-resource?clientId=%s&clientSecret=%s",
                casdoorConfig.getEndpoint(), casdoorConfig.getClientId(), casdoorConfig.getClientSecret());
        CasdoorResource casdoorResource = new CasdoorResource(casdoorConfig.getOrganizationName(), name);
        String userStr = objectMapper.writeValueAsString(casdoorResource);
        String responseStr = HttpClient.postString(targetUrl, userStr);
        return objectMapper.readValue(responseStr, CasdoorResponse.class);
    }
}
