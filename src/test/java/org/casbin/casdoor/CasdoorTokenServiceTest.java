package org.casbin.casdoor;

import org.casbin.casdoor.entity.CasdoorToken;
import org.casbin.casdoor.service.CasdoorTokenService;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class CasdoorTokenServiceTest extends CasdoorServiceTest {
    @Test
    public void testDeleteTokens() throws IOException {
        CasdoorTokenService casdoorTokenService = new CasdoorTokenService(this.casdoorConfig);
        CasdoorToken token = new CasdoorToken();
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
        CasdoorTokenService casdoorTokenService = new CasdoorTokenService(this.casdoorConfig);
        CasdoorResponse tokens = casdoorTokenService.getTokens("admin",1, 10);
        assertNotNull(tokens);
    }

}
