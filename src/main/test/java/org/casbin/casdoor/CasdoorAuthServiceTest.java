package org.casbin.casdoor;


import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorUser;
import org.casbin.casdoor.service.CasdoorAuthService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

public class CasdoorAuthServiceTest {

    private CasdoorConfig casdoorConfig;

    /**
     * You should replace the initConfig() method's content and code with your own Casdoor instance.
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
    public void testAuthService() throws OAuthProblemException, OAuthSystemException, ParseException, InvocationTargetException, IllegalAccessException {
        String code = "71b645e73381caeb2c66";
        CasdoorAuthService casdoorAuthService = new CasdoorAuthService(this.casdoorConfig);
        String token = casdoorAuthService.getOAuthToken(code, "app-built-in");
        Assert.assertNotNull(token);
        CasdoorUser casdoorUser = casdoorAuthService.parseJwtToken(token);
        Assert.assertNotNull(casdoorUser);
    }
}
