package org.casbin.casdoor;

import org.casbin.casdoor.service.ResourceService;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ResourceServiceTest extends CasdoorServiceTest {
    @Test
    public void testResourceService() throws IOException {
        String user = "admin";
        String tag = "";
        String parent = "";
        File file = new File(this.getClass().getResource("/casbin.svg").getFile());
        String fullFilePath = String.format("%s/%s/%s", tag, config.getOrganizationName(), file.getName());
        ResourceService resourceService = new ResourceService(config);

        CasdoorResponse<String, Object> response = resourceService.uploadResource(user, tag, parent, fullFilePath, file);
        Assert.assertEquals("ok", response.getStatus());

        //response = resourceService.deleteResource(file.getName());
        //Assert.assertEquals("ok", response.getStatus());
    }
}
