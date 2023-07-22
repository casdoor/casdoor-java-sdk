package org.casbin.casdoor;

import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.service.CasdoorResourceService;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class CasdoorResourceServiceTest {

    private CasdoorConfig casdoorConfig;

    /**
     * You should replace the initConfig() method's content with your own Casdoor instance.
     */
    @Before
    public void initConfig() {
        this.casdoorConfig = new CasdoorConfig(
                "http://localhost:8000",
                "f485cd52dab369c8551a",
                "1d7221b217ed3d12100da5e208aa93c8770e4a81",
                "CasdoorSecret",
                "built-in",
                "app-built-in"
        );
    }

    @Test
    public void testResourceService() throws IOException {
        String user = "admin";
        String tag = "";
        String parent = "";
        File file = new File(this.getClass().getResource("/casbin.svg").getFile());
        String fullFilePath = String.format("%s/%s/%s", tag, casdoorConfig.getOrganizationName(), file.getName());
        CasdoorResourceService casdoorResourceService = new CasdoorResourceService(casdoorConfig);

        CasdoorResponse response = casdoorResourceService.uploadResource(user, tag, parent, fullFilePath, file);
        Assert.assertEquals("ok", response.getStatus());

        response = casdoorResourceService.deleteResource(file.getName());
        Assert.assertEquals("ok", response.getStatus());
    }
}
