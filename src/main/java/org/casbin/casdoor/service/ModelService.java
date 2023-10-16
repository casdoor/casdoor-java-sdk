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
// See the License for the specific language governing CasdoorPermissions and
// limitations under the License.

package org.casbin.casdoor.service;

import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.Model;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.ModelOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;

import java.io.IOException;


public class ModelService extends Service {

    public ModelService(Config config) {
        super(config);
    }

    public Model getModel(String name) throws IOException {
        CasdoorResponse<Model, Object> response = doGet("get-model",
                Map.of("id", "casbin/" + name), new TypeReference<CasdoorResponse<Model, Object>>() {
                });
        return response.getData();
    }

    public List<Model> getModels() throws IOException {
        CasdoorResponse<List<Model>, Object> response = doGet("get-models",
                Map.of("owner", "casbin"), new TypeReference<CasdoorResponse<List<Model>, Object>>() {
                });
        return response.getData();
    }

    public CasdoorResponse<String, Object> addModel(Model model) throws IOException {
        return modifyModel(ModelOperations.ADD_MODEL, model, null);
    }

    public CasdoorResponse<String, Object> deleteModel(String name) throws IOException {
        Model model = new Model();
        model.owner = "casbin";
        model.name = name;
        return modifyModel(ModelOperations.DELETE_MODEL, model, null);
    }

    public CasdoorResponse<String, Object> updateModel(Model model) throws IOException {
        return modifyModel(ModelOperations.UPDATE_MODEL, model, null);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifyModel(ModelOperations method, Model model, java.util.Map<String, String> queryMap) throws IOException {
        String id = model.owner + "/" + model.name;
        String payload = objectMapper.writeValueAsString(model);
        return doPost(method.getOperation(), Map.mergeMap(Map.of("id", id), queryMap), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {
                });
    }
}