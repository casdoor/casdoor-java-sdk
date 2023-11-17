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
import org.casbin.casdoor.entity.Application;
import org.casbin.casdoor.util.ApplicationOperations;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;
import java.util.List;

public class ApplicationService extends Service {

    public ApplicationService(Config config) {
        super(config);
    }

    public Application getApplication(String name) throws IOException {
        CasdoorResponse<Application, Object> response = doGet("get-application",
                Map.of("id", "admin/" + name), new TypeReference<CasdoorResponse<Application, Object>>() {
                });
        return response.getData();
    }

    public List<Application> getApplications() throws IOException {
        CasdoorResponse<List<Application>, Object> response = doGet("get-applications",
                Map.of("owner", "admin"), new TypeReference<CasdoorResponse<List<Application>, Object>>() {
                });
        return response.getData();
    }

    public List<Application> getOrganizationApplications() throws IOException {
        CasdoorResponse<List<Application>, Object> response = doGet("get-organization-applications",
                Map.of("owner", "admin", "organization", getConfig().getOrganizationName()),
                new TypeReference<CasdoorResponse<List<Application>, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> addApplication(Application application) throws IOException {
        application.owner = "admin";
        return modifyApplication(ApplicationOperations.ADD_APPLICATION, application, null);
    }

    public CasdoorResponse<String, Object> deleteApplication(Application application) throws IOException {
        application.owner = "admin";
        return modifyApplication(ApplicationOperations.DELETE_APPLICATION, application, null);
    }

    public CasdoorResponse<String, Object> updateApplication(Application application) throws IOException {
        application.owner = "admin";
        return modifyApplication(ApplicationOperations.UPDATE_APPLICATION, application, null);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyApplication(ApplicationOperations method, Application application, java.util.Map<String, String> queryMap) throws IOException {
        String id = application.owner + "/" + application.name;
        String payload = getObjectMapper().writeValueAsString(application);
        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}