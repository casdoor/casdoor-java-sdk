package org.casbin.casdoor;

import org.casbin.casdoor.entity.Role;
import org.casbin.casdoor.service.RoleService;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class RoleServiceTest extends CasdoorServiceTest {
    @Test
    public void testGetRole() throws IOException {
        RoleService roleService = new RoleService(this.config);
        Role role = roleService.getRole("role_88");
        assertNotNull(role);
    }

    @Test
    public void testGetRoles() throws IOException {
        RoleService roleService = new RoleService(this.config);
        List<Role> roles = roleService.getRoles();
        assertNotNull(roles);
    }

    @Test
    public void testModifyRole() throws IOException {
        RoleService roleService = new RoleService(this.config);

        Role role = new Role();
        role.setOwner("built-in");
        role.setName("test-modify-role");
        CasdoorResponse<String, Object> response = roleService.addRole(role);
        assertEquals("ok", response.getStatus());
        assertEquals("Affected", response.getData());

        role.setDisplayName("test-display-name");
        response = roleService.updateRole(role);
        assertEquals("ok", response.getStatus());
        assertEquals("Affected", response.getData());

        response = roleService.deleteRole(role);
        assertEquals("ok", response.getStatus());
        assertEquals("Affected", response.getData());
    }

    @Test
    public void testGetPaginationRoles() throws IOException {
        RoleService roleService = new RoleService(this.config);
        Map<String, String> queryMap = new HashMap<>();
        Map<String, Object> result = roleService.getPaginationRoles(1, 10, queryMap);
        assertNotNull(result);
        assertTrue(result.containsKey("casdoorRoles"));
        assertTrue(result.containsKey("data2"));

        List<Role> roles = (List<Role>) result.get("casdoorRoles");
        int data2 = (int) result.get("data2");

        assertTrue(!roles.isEmpty());
        assertTrue(data2 > 0);
    }
}
