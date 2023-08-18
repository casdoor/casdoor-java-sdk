// Copyright 2023 The casbin Authors. All Rights Reserved.
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
import org.casbin.casdoor.entity.CasdoorApplication;
import org.casbin.casdoor.util.ApplicationOperations;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;
import java.util.List;

public class CasdoorApplicationService extends CasdoorService{

    public CasdoorApplicationService(CasdoorConfig casdoorConfig) {
        super(casdoorConfig);
    }

    public CasdoorApplication getApplication(String name) throws IOException {
        CasdoorResponse<CasdoorApplication, Object> response = doGet("get-application",
                Map.of("id", "admin/" + name), new TypeReference<CasdoorResponse<CasdoorApplication, Object>>() {});
        return response.getData();
    }

    public List<CasdoorApplication> getApplications() throws IOException {
        CasdoorResponse<List<CasdoorApplication>, Object> response = doGet("get-applications",
                Map.of("owner", "admin"), new TypeReference<CasdoorResponse<List<CasdoorApplication>, Object>>() {});
        return response.getData();
    }

    public List<CasdoorApplication> getOrganizationApplications() throws IOException {
        CasdoorResponse<List<CasdoorApplication>, Object> response = doGet("get-organization-applications",
                Map.of("owner", "admin", "organization", casdoorConfig.getOrganizationName()),
                new TypeReference<CasdoorResponse<List<CasdoorApplication>, Object>>() {});
        return response.getData();
    }

    public CasdoorResponse<String, Object> addApplication(CasdoorApplication application) throws IOException {
        return modifyApplication(ApplicationOperations.ADD_APPLICATION, application, null);
    }

    public CasdoorResponse<String, Object> deleteApplication(String name) throws IOException {
        CasdoorApplication application = new CasdoorApplication();
        application.setOwner("admin");
        application.setName(name);
        return modifyApplication(ApplicationOperations.DELETE_APPLICATION, application, null);
    }

    public CasdoorResponse<String, Object> updateApplication(CasdoorApplication application) throws IOException {
        return modifyApplication(ApplicationOperations.UPDATE_APPLICATION, application, null);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyApplication(ApplicationOperations method, CasdoorApplication application, java.util.Map<String, String> queryMap) throws IOException {
        String id = application.getOwner() + "/" + application.getName();
        String payload = objectMapper.writeValueAsString(application);
        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {});
    }
}