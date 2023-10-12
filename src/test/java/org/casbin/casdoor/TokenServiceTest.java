package org.casbin.casdoor;

import org.casbin.casdoor.entity.Token;
import org.casbin.casdoor.service.TokenService;
import org.casbin.casdoor.support.ConfigFactory;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class TokenServiceTest extends CasdoorServiceTest {
    private TokenService casdoorTokenService;
    @Before
    public void setUp() throws Exception{
        casdoorTokenService = new TokenService(ConfigFactory.getConfig());
    }
    @Test
    public void testDeleteTokens() throws IOException {
        Token token = new Token();
        token.setAccessToken("string");
        token.setApplication("string");
        token.setCodeChallenge("string");
        token.setCreatedTime("string");
        token.setOrganization("string");
        token.setRefreshToken("string");
        token.setUser("string");
        token.setScope("string");
        token.setTokenType("string");
        token.setExpiresIn(0);
        token.setCodeIsUsed(true);
        token.setExpiresIn(0);

        token.setName("test-delete-token");
        token.setOwner("admin");
        CasdoorResponse response = casdoorTokenService.deleteToken(token);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());
    }

    @Test
    public void testGetTokens() throws IOException {
        CasdoorResponse tokens = casdoorTokenService.getTokens(1, 10);
        assertNotNull(tokens);
    }

}
