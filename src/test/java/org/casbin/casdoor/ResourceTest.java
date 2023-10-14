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

import org.casbin.casdoor.entity.Resource;
import org.casbin.casdoor.service.ResourceService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class ResourceTest {

    private ResourceService resourceService = new ResourceService(
            TestDefaultConfig.InitConfig());

    @Test
    public void testResource() throws IOException {
        // uploadResource
        String filename = "/casbinTest.svg";
        File data = new File(this.getClass().getResource(filename).getFile());
        String name = String.format("/casdoor/%s", filename);
        Resource resource = new Resource(
                "casbin",
                name
        );
        CasdoorResponse<String, Object> response = resourceService.uploadResource(resource.owner, name, "", filename, data);
        Assertions.assertEquals("ok", response.getStatus());

        // Delete the resource
        CasdoorResponse<String, Object> deleteResource = resourceService.deleteResource(name);
        Assertions.assertEquals("ok", deleteResource.getStatus());
        // There is no get method
        // so there is no way to test the effect of deletion, only to assert the returned status code
    }
}