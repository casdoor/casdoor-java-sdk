package org.casbin.casdoor.e2e;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.casbin.casdoor.CasdoorClient;
import org.casbin.casdoor.entity.CasdoorToken;
import org.casbin.casdoor.response.CasdoorActionResponse;
import org.casbin.casdoor.response.CasdoorResponse;
import org.casbin.casdoor.service.CasdoorTokenService;
import org.casbin.casdoor.support.CasdoorClientProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CasdoorTokenServiceTest {
    private CasdoorClient client = CasdoorClientProvider.get();
    private CasdoorTokenService subject;

    @Before
    public void setUp() {
        subject = client.createService(CasdoorTokenService.class);
    }

    @Test
    public void testDeleteTokens() throws IOException {
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
        CasdoorActionResponse response = subject.deleteToken(token).execute().body();
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());
    }

    @Test
    public void testGetTokens() throws IOException {
        CasdoorResponse<List<CasdoorToken>, Integer> response = subject
                .getPaginationTokens(1, 10, new HashMap<>()).execute().body();
        assertFalse(response.getData().isEmpty());
        assertTrue(response.getData2() > 0);
    }

}
