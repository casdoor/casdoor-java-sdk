package org.casbin.casdoor;

import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorPermission;
import org.casbin.casdoor.entity.CasdoorToken;
import org.casbin.casdoor.service.CasdoorPermissionService;
import org.casbin.casdoor.service.CasdoorTokenService;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CasdoorTokenServiceTest {
    private CasdoorConfig casdoorConfig;

    @Before
    public void init() {
        casdoorConfig = new CasdoorConfig();
        casdoorConfig.setEndpoint("http://localhost:8000");
        casdoorConfig.setClientId("bfe69a0520b1e476e704");
        casdoorConfig.setClientSecret("fdb3cf0a9ccfbaacabc67fec91d15aba62f28432");
        casdoorConfig.setOrganizationName("built-in");
        casdoorConfig.setApplicationName("app-built-in");
        casdoorConfig.setCertificate("-----BEGIN CERTIFICATE-----\n" +
                "MIIE+TCCAuGgAwIBAgIDAeJAMA0GCSqGSIb3DQEBCwUAMDYxHTAbBgNVBAoTFENh\n" +
                "c2Rvb3IgT3JnYW5pemF0aW9uMRUwEwYDVQQDEwxDYXNkb29yIENlcnQwHhcNMjEx\n" +
                "MDE1MDgxMTUyWhcNNDExMDE1MDgxMTUyWjA2MR0wGwYDVQQKExRDYXNkb29yIE9y\n" +
                "Z2FuaXphdGlvbjEVMBMGA1UEAxMMQ2FzZG9vciBDZXJ0MIICIjANBgkqhkiG9w0B\n" +
                "AQEFAAOCAg8AMIICCgKCAgEAsInpb5E1/ym0f1RfSDSSE8IR7y+lw+RJjI74e5ej\n" +
                "rq4b8zMYk7HeHCyZr/hmNEwEVXnhXu1P0mBeQ5ypp/QGo8vgEmjAETNmzkI1NjOQ\n" +
                "CjCYwUrasO/f/MnI1C0j13vx6mV1kHZjSrKsMhYY1vaxTEP3+VB8Hjg3MHFWrb07\n" +
                "uvFMCJe5W8+0rKErZCKTR8+9VB3janeBz//zQePFVh79bFZate/hLirPK0Go9P1g\n" +
                "OvwIoC1A3sarHTP4Qm/LQRt0rHqZFybdySpyWAQvhNaDFE7mTstRSBb/wUjNCUBD\n" +
                "PTSLVjC04WllSf6Nkfx0Z7KvmbPstSj+btvcqsvRAGtvdsB9h62Kptjs1Yn7GAuo\n" +
                "I3qt/4zoKbiURYxkQJXIvwCQsEftUuk5ew5zuPSlDRLoLByQTLbx0JqLAFNfW3g/\n" +
                "pzSDjgd/60d6HTmvbZni4SmjdyFhXCDb1Kn7N+xTojnfaNkwep2REV+RMc0fx4Gu\n" +
                "hRsnLsmkmUDeyIZ9aBL9oj11YEQfM2JZEq+RVtUx+wB4y8K/tD1bcY+IfnG5rBpw\n" +
                "IDpS262boq4SRSvb3Z7bB0w4ZxvOfJ/1VLoRftjPbLIf0bhfr/AeZMHpIKOXvfz4\n" +
                "yE+hqzi68wdF0VR9xYc/RbSAf7323OsjYnjjEgInUtRohnRgCpjIk/Mt2Kt84Kb0\n" +
                "wn8CAwEAAaMQMA4wDAYDVR0TAQH/BAIwADANBgkqhkiG9w0BAQsFAAOCAgEAn2lf\n" +
                "DKkLX+F1vKRO/5gJ+Plr8P5NKuQkmwH97b8CS2gS1phDyNgIc4/LSdzuf4Awe6ve\n" +
                "C06lVdWSIis8UPUPdjmT2uMPSNjwLxG3QsrimMURNwFlLTfRem/heJe0Zgur9J1M\n" +
                "8haawdSdJjH2RgmFoDeE2r8NVRfhbR8KnCO1ddTJKuS1N0/irHz21W4jt4rxzCvl\n" +
                "2nR42Fybap3O/g2JXMhNNROwZmNjgpsF7XVENCSuFO1jTywLaqjuXCg54IL7XVLG\n" +
                "omKNNNcc8h1FCeKj/nnbGMhodnFWKDTsJcbNmcOPNHo6ixzqMy/Hqc+mWYv7maAG\n" +
                "Jtevs3qgMZ8F9Qzr3HpUc6R3ZYYWDY/xxPisuKftOPZgtH979XC4mdf0WPnOBLqL\n" +
                "2DJ1zaBmjiGJolvb7XNVKcUfDXYw85ZTZQ5b9clI4e+6bmyWqQItlwt+Ati/uFEV\n" +
                "XzCj70B4lALX6xau1kLEpV9O1GERizYRz5P9NJNA7KoO5AVMp9w0DQTkt+LbXnZE\n" +
                "HHnWKy8xHQKZF9sR7YBPGLs/Ac6tviv5Ua15OgJ/8dLRZ/veyFfGo2yZsI+hKVU5\n" +
                "nCCJHBcAyFnm1hdvdwEdH33jDBjNB6ciotJZrf/3VYaIWSalADosHAgMWfXuWP+h\n" +
                "8XKXmzlxuHbTMQYtZPDgspS5aK+S4Q9wb8RRAYo=\n" +
                "-----END CERTIFICATE-----");
    }



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
        CasdoorResponse tokens = casdoorTokenService.getTokens(1, 10);
        assertNotNull(tokens);
    }

}
