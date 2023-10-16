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

package org.casbin.casdoor;

import org.casbin.casdoor.entity.Model;
import org.casbin.casdoor.service.ModelService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class ModelTest {
    private final ModelService modelService = new ModelService(TestDefaultConfig.InitConfig());

    @Test
    public void testModel() {
        String name = TestDefaultConfig.getRandomName("Model");

        // Add a new object
        Model model = new Model(
                "casbin",
                name,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                name,
                "[request_definition]\n" +
                        "r = sub, obj, act\n" +
                        "\n" +
                        "[policy_definition]\n" +
                        "p = sub, obj, act\n" +
                        "\n" +
                        "[role_definition]\n" +
                        "g = _, _\n" +
                        "\n" +
                        "[policy_effect]\n" +
                        "e = some(where (p.eft == allow))\n" +
                        "\n" +
                        "[matchers]\n" +
                        "m = g(r.sub, p.sub) && r.obj == p.obj && r.act == p.act"
        );
        assertDoesNotThrow(() -> modelService.addModel(model));

        // Get all objects, check if our added object is inside the list
        List<Model> models;
        try {
            models = modelService.getModels();
        } catch (Exception e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        boolean found = models.stream().anyMatch(item -> item.name.equals(name));
        assertTrue(found, "Added object not found in list");

        // Get the object
        Model retrievedModel;
        try {
            retrievedModel = modelService.getModel(name);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(name, retrievedModel.name, "Retrieved object does not match added object");

        // Update the object
        String updatedDisplayName = "UpdatedName";
        retrievedModel.displayName = updatedDisplayName;
        assertDoesNotThrow(() -> modelService.updateModel(retrievedModel));

        // Validate the update
        Model updatedModel;
        try {
            updatedModel = modelService.getModel(name);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedDisplayName, updatedModel.displayName, "Failed to update object, displayName mismatch");

        // Delete the object
        assertDoesNotThrow(() -> modelService.deleteModel(name));

        // Validate the deletion
        Model deletedModel;
        try {
            deletedModel = modelService.getModel(name);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
            return;
        }
        assertNull(deletedModel, "Failed to delete object, it's still retrievable");
    }
}