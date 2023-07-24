package org.casbin.casdoor;

import org.casbin.casdoor.service.CasdoorResourceService;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class CasdoorResourceServiceTest extends CasdoorServiceTest {
    @Test
    public void testResourceService() throws IOException {
        String user = "admin";
        String tag = "";
        String parent = "";
        File file = new File(this.getClass().getResource("/casbin.svg").getFile());
        String fullFilePath = String.format("%s/%s/%s", tag, casdoorConfig.getOrganizationName(), file.getName());
        CasdoorResourceService casdoorResourceService = new CasdoorResourceService(casdoorConfig);

        CasdoorResponse<String, Object> response = casdoorResourceService.uploadResource(user, tag, parent, fullFilePath, file);
        Assert.assertEquals("ok", response.getStatus());

        //response = casdoorResourceService.deleteResource(file.getName());
        //Assert.assertEquals("ok", response.getStatus());
    }
}
