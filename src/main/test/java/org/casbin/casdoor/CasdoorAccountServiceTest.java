package org.casbin.casdoor;

import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.service.CasdoorAccountService;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CasdoorAccountServiceTest {

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
    public void setPassword() throws Exception {
        CasdoorAccountService casdoorAccountService = new CasdoorAccountService(casdoorConfig);
        CasdoorResponse response = casdoorAccountService.setPassword("admin", "123456", "123456");
        Assert.assertEquals("ok", response.getStatus());
    }
}