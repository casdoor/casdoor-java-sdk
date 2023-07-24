package org.casbin.casdoor.e2e;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.casbin.casdoor.CasdoorClient;
import org.casbin.casdoor.entity.CasdoorRole;
import org.casbin.casdoor.response.CasdoorActionResponse;
import org.casbin.casdoor.response.CasdoorResponse;
import org.casbin.casdoor.service.CasdoorRoleService;
import org.casbin.casdoor.support.CasdoorClientProvider;
import org.junit.Before;
import org.junit.Test;

public class CasdoorRoleServiceTest {
    private CasdoorClient client = CasdoorClientProvider.get();
    private CasdoorRoleService subject;

    @Before
    public void setUp() {
        subject = client.createService(CasdoorRoleService.class);
    }

    @Test
    public void testGetRole() throws IOException {
        CasdoorRole role = subject.getRole("role_88").execute().body().getData();
        assertNotNull(role);
    }

    @Test
    public void testGetRoles() throws IOException {
        List<CasdoorRole> roles = subject.getRoles().execute().body().getData();
        assertNotNull(roles);
    }

    @Test
    public void testModifyRole() throws IOException {
        CasdoorRole role = new CasdoorRole();
        role.setOwner("built-in");
        role.setName("test-modify-role");
        CasdoorActionResponse response = subject.addRole(role).execute().body();
        assertEquals("ok", response.getStatus());
        assertEquals("Affected", response.getData());

        role.setDisplayName("test-display-name");
        response = subject.updateRole("test-modify-role", role).execute().body();
        assertEquals("ok", response.getStatus());
        assertEquals("Affected", response.getData());

        response = subject.deleteRole(role).execute().body();
        assertEquals("ok", response.getStatus());
        assertEquals("Affected", response.getData());
    }

    @Test
    public void testGetPaginationRoles() throws IOException {
        Map<String, String> queryMap = new HashMap<>();
        CasdoorResponse<List<CasdoorRole>, Integer> response = subject.getPaginationRoles(1, 10, queryMap)
                .execute().body();

        List<CasdoorRole> roles = response.getData();
        int count = response.getData2();

        assertFalse(roles.isEmpty());
        assertTrue(count > 0);
    }
}
