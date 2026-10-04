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
import org.casbin.casdoor.entity.Plan;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.PlanOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

import jakarta.annotation.Nullable;

import java.io.IOException;

public class PlanService extends Service {

    public PlanService(Config config) {
        super(config);
    }

    public Plan getPlan(String name) throws IOException {
        CasdoorResponse<Plan, Object> response = doGet("get-plan",
                Map.of("id", getId(name)), new TypeReference<CasdoorResponse<Plan, Object>>() {
                });
        return response.getData();
    }

    public List<Plan> getPlans() throws IOException {
        CasdoorResponse<List<Plan>, Object> response = doGet("get-plans",
                Map.of("owner", config.organizationName), new TypeReference<CasdoorResponse<List<Plan>, Object>>() {
                });
        return response.getData();
    }

    public java.util.Map<String, Object> getPaginationPlans(int p, int pageSize, @Nullable java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<Plan[], Object> casdoorResponse = doGet("get-plans",
                Map.mergeMap(Map.of("owner", config.organizationName,
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<Plan[], Object>>() {
                });

        return Map.of("casdoorPlans", casdoorResponse.getData(), "data2", casdoorResponse.getData2());
    }

    public CasdoorResponse<String, Object> addPlan(Plan plan) throws IOException {
        return modifyPlan(PlanOperations.ADD_PLAN, plan, null);
    }

    public CasdoorResponse<String, Object> deletePlan(Plan plan) throws IOException {
        return modifyPlan(PlanOperations.DELETE_PLAN, plan, null);
    }

    public CasdoorResponse<String, Object> updatePlan(Plan plan) throws IOException {
        return modifyPlan(PlanOperations.UPDATE_PLAN, plan, null);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyPlan(PlanOperations method, Plan plan, java.util.Map<String, String> queryMap) throws IOException {
        plan.owner = getOwner(plan.owner, config.organizationName);
        String id = plan.owner + "/" + plan.name;
        String payload = objectMapper.writeValueAsString(plan);
        return doPost(method.getOperation(),
                Map.of("id", id), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}
