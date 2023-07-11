package org.casbin.casdoor;

import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorRole;
import org.casbin.casdoor.service.CasdoorRoleService;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CasdoorRoleServiceTest {
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
                "-----END CERTIFICATE-----");
    }

    @Test
    public void testGetRole() throws IOException {
        CasdoorRoleService casdoorRoleService = new CasdoorRoleService(this.casdoorConfig);
        CasdoorRole role = casdoorRoleService.getRole("role_88");
        assertNotNull(role);
    }

    @Test
    public void testGetRoles() throws IOException {
        CasdoorRoleService casdoorRoleService = new CasdoorRoleService(this.casdoorConfig);
        CasdoorRole[] roles = casdoorRoleService.getRoles();
        assertNotNull(roles);
    }

    @Test
    public void testModifyRole() throws IOException {
        CasdoorRoleService casdoorRoleService = new CasdoorRoleService(this.casdoorConfig);

        CasdoorRole role = new CasdoorRole();
        role.setOwner("built-in");
        role.setName("test-modify-role");
        CasdoorResponse response = casdoorRoleService.addRole(role);
        assertEquals("ok", response.getStatus());
        assertEquals("Affected", response.getData());

        role.setDisplayName("test-display-name");
        response = casdoorRoleService.updateRole(role);
        assertEquals("ok", response.getStatus());
        assertEquals("Affected", response.getData());

        response = casdoorRoleService.deleteRole(role);
        assertEquals("ok", response.getStatus());
        assertEquals("Affected", response.getData());
    }

    @Test
    public void testGetPaginationRoles() throws IOException {
        CasdoorRoleService casdoorRoleService = new CasdoorRoleService(this.casdoorConfig);
        Map<String, String> queryMap = new HashMap<>();
        Map<String, Object> result = casdoorRoleService.getPaginationRoles(1, 10, queryMap);
        assertNotNull(result);
        assertTrue(result.containsKey("casdoorRoles"));
        assertTrue(result.containsKey("data2"));

        List<CasdoorRole> roles = (List<CasdoorRole>) result.get("casdoorRoles");
        int data2 = (int) result.get("data2");

        assertTrue(!roles.isEmpty());
        assertTrue(data2 > 0);
    }
}
