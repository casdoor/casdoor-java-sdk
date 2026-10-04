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
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceTest {
    private final ResourceService resourceService = new ResourceService(TestDefaultConfig.InitConfig());

    @Test
    public void testResource() throws IOException {
        String filename = "casbinTest.svg";
        File data = new File(this.getClass().getResource("/" + filename).getFile());
        String tag = String.format("/casdoor/%s", TestDefaultConfig.getRandomName("resource"));
        String owner = TestDefaultConfig.TEST_CASDOOR_ORGANIZATION;

        // Upload a file, data is the file URL and data2 is the name of the resource
        CasdoorResponse<String, Object> response = resourceService.uploadResource(owner, tag, "", filename, data);
        assertEquals("ok", response.getStatus());
        String name = (String) response.getData2();
        assertNotNull(name);

        // Get all objects, check if our added object is inside the list
        List<Resource> resources = resourceService.getResources(owner, owner, "", "", "", "");
        assertTrue(resources.stream().anyMatch(item -> tag.equals(item.tag)), "Added object not found in list");

        List<Resource> page = resourceService.getPaginationResources(owner, owner, "", "", 10, 1, "", "");
        assertFalse(page.isEmpty());

        // Get the object
        Resource resource = resourceService.getResourceEx(owner, name);
        assertEquals(tag, resource.tag);

        // Delete the object
        assertEquals("ok", resourceService.deleteResource(resource).getStatus());
        assertNull(resourceService.getResource(owner + "/" + name));
    }
}
