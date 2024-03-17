// Copyright 2024 The Casdoor Authors. All Rights Reserved.
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
// See the License for the specific language governing CasdoorPermissions and
// limitations under the License.

package org.casbin.casdoor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.CasbinRule;
import org.casbin.casdoor.entity.Enforcer;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.PolicyOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;

import java.io.IOException;
import java.util.List;

public class PolicyService  extends Service{
    public PolicyService(Config config) {
        super(config);
    }

    public List<CasbinRule> getPolicies(String enforcerName, String adapterId) throws IOException {
        String id = config.organizationName + "/" + enforcerName;
        CasdoorResponse<List<CasbinRule>, Object> resp = doGet("get-policies",
                Map.of("id", id, "adapterId", adapterId), new TypeReference<CasdoorResponse<List<CasbinRule>, Object>>() {
                });
        return resp.getData();
    }

    public CasdoorResponse<Object, String> addPolicy(Enforcer enforcer, CasbinRule policy) throws IOException {
        CasbinRule[] policies = new CasbinRule[1];
        policies[0] = policy;
        return modifyPolicy(PolicyOperations.ADD_Policy, enforcer, policies);
    }

    public CasdoorResponse<Object, String> removePolicy(Enforcer enforcer, CasbinRule policy) throws IOException {
        CasbinRule[] policies = new CasbinRule[1];
        policies[0] = policy;
        return modifyPolicy(PolicyOperations.DELETE_Policy, enforcer, policies);
    }

    public CasdoorResponse<Object, String> updatePolicy(Enforcer enforcer, CasbinRule oldpolicy, CasbinRule newpolicy) throws IOException {
        CasbinRule[] policies = new CasbinRule[2];
        policies[0] = oldpolicy;
        policies[1] = newpolicy;
        return modifyPolicy(PolicyOperations.UPDATE_Policy, enforcer, policies);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyPolicy(PolicyOperations method, Enforcer enforcer, CasbinRule[] policies) throws IOException {
        enforcer.owner = config.organizationName;
        String id = enforcer.owner + "/" + enforcer.name;
        String payload = "";
        if (method == PolicyOperations.UPDATE_Policy){
            payload = objectMapper.writeValueAsString(policies);
        } else {
            payload = objectMapper.writeValueAsString(policies[0]);
        }
        return doPost(method.getOperation(),
                Map.of("id", id),
                payload, new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}
