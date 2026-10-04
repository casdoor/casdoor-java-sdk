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
import java.util.List;

public class ResourceService extends Service {
    public ResourceService(Config config) {
        super(config);
    }

    public Resource getResource(String id) throws IOException {
        CasdoorResponse<Resource, Object> response = doGet("get-resource",
                Map.of("id", getId(id)), new TypeReference<CasdoorResponse<Resource, Object>>() {
                });
        return response.getData();
    }

    public Resource getResourceEx(String owner, String name) throws IOException {
        return getResource(owner + "/" + name);
    }

    public List<Resource> getResources(String owner, String user, String field, String value, String sortField, String sortOrder) throws IOException {
        CasdoorResponse<List<Resource>, Object> response = doGet("get-resources",
                Map.of("owner", owner, "user", user, "field", field, "value", value, "sortField", sortField, "sortOrder", sortOrder),
                new TypeReference<CasdoorResponse<List<Resource>, Object>>() {
                });
        return response.getData();
    }

    public List<Resource> getPaginationResources(String owner, String user, String field, String value, int pageSize, int page, String sortField, String sortOrder) throws IOException {
        CasdoorResponse<List<Resource>, Object> response = doGet("get-resources",
                Map.of("owner", owner, "user", user, "field", field, "value", value,
                        "p", Integer.toString(page), "pageSize", Integer.toString(pageSize),
                        "sortField", sortField, "sortOrder", sortOrder),
                new TypeReference<CasdoorResponse<List<Resource>, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> addResource(Resource resource) throws IOException {
        return modifyResource("add-resource", resource);
    }

    public CasdoorResponse<String, Object> updateResource(Resource resource) throws IOException {
        return modifyResource("update-resource", resource);
    }

    /**
     * Uploads a file, the data of the response is the file URL and data2 is the resource name.
     */
    public CasdoorResponse<String, Object> uploadResource(String user, String tag, String parent, String fullFilePath, File file) throws IOException {
        return doPost("upload-resource",
                Map.of("owner", config.organizationName,
                        "user", user,
                        "application", config.applicationName,
                        "tag", tag,
                        "parent", parent,
                        "fullFilePath", fullFilePath),
                file, new TypeReference<CasdoorResponse<String, Object>>() {});
    }

    public CasdoorResponse<String, Object> uploadResourceEx(String user, String tag, String parent, String fullFilePath, byte[] fileBytes, String createdTime, String description) throws IOException {
        String fileName = fullFilePath.substring(fullFilePath.lastIndexOf('/') + 1);
        return doPostBytes("upload-resource",
                Map.of("owner", config.organizationName,
                        "user", user,
                        "application", config.applicationName,
                        "tag", tag,
                        "parent", parent,
                        "fullFilePath", fullFilePath,
                        "createdTime", createdTime,
                        "description", description),
                fileName, fileBytes, new TypeReference<CasdoorResponse<String, Object>>() {});
    }

    public CasdoorResponse<String, Object> deleteResource(String name) throws IOException {
        return deleteResourceWithTag(new Resource(config.organizationName, name), "");
    }

    public CasdoorResponse<String, Object> deleteResource(Resource resource) throws IOException {
        return deleteResourceWithTag(resource, "");
    }

    /**
     * Deletes the resource, the "Direct" tag also deletes the file from the storage provider.
     */
    public CasdoorResponse<String, Object> deleteResourceWithTag(Resource resource, String tag) throws IOException {
        resource.owner = getOwner(resource.owner, config.organizationName);
        return doPost("delete-resource", Map.of("tag", tag), objectMapper.writeValueAsString(resource),
                new TypeReference<CasdoorResponse<String, Object>>() {});
    }

    private CasdoorResponse<String, Object> modifyResource(String action, Resource resource) throws IOException {
        resource.owner = getOwner(resource.owner, config.organizationName);
        return doPost(action, Map.of("id", resource.owner + "/" + resource.name), objectMapper.writeValueAsString(resource),
                new TypeReference<CasdoorResponse<String, Object>>() {});
    }
}
