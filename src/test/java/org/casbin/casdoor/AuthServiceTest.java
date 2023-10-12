package org.casbin.casdoor;


import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.casbin.casdoor.entity.User;
import org.casbin.casdoor.exception.Exception;
import org.casbin.casdoor.service.AuthService;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

public class AuthServiceTest extends CasdoorServiceTest {
    @Test
    public void testAuthService() throws OAuthProblemException, OAuthSystemException, Exception, InvocationTargetException, IllegalAccessException {
        String code = "71b645e73381caeb2c66";
        AuthService authService = new AuthService(this.config);
        String token = authService.getOAuthToken(code, "app-built-in");
        Assert.assertNotNull(token);
        User user = authService.parseJwtToken(token);
        Assert.assertNotNull(user);
    }

    @Test
    public void testGetSigninUel() throws UnsupportedEncodingException {
        AuthService authService = new AuthService(this.config);
        String signinUrl = authService.getSigninUrl("http://localhost:3000/callback");
        System.out.println(signinUrl);
        Assert.assertNotEquals("", signinUrl);
    }
}
