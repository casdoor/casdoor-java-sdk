package org.casbin.casdoor;

import org.casbin.casdoor.entity.Permission;
import org.casbin.casdoor.service.PermissionService;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class PermissionServiceTest extends CasdoorServiceTest {
    @Test
    public void testGetPermission() throws IOException {
        PermissionService permissionService = new PermissionService(this.config);
        Permission permission = permissionService.getPermission("permission-built-in");
        assertNotNull(permission);

    }

    @Test
    public void testGetPermissions() throws IOException {
        PermissionService permissionService = new PermissionService(this.config);
        List<Permission> permissions = permissionService.getPermissions();
        assertNotNull(permissions);
    }

    @Test
    public void testModifyPermission() throws IOException {
        PermissionService permissionService = new PermissionService(this.config);

        Permission permission = new Permission();
        permission.owner = "built-in";
        permission.name = "test-modify-permission";

        CasdoorResponse response = permissionService.addPermission(permission);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        permission.displayName = "test-display-name";
        response = permissionService.updatePermission(permission);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        response = permissionService.deletePermission(permission);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());
    }

    @Test
    public void testGetPaginationPermissions() throws IOException {
        PermissionService permissionService = new PermissionService(this.config);
        Map<String, String> queryMap = new HashMap<>();
        Map<String, Object> result = permissionService.getPaginationPermissions(1, 10, queryMap);
        assertNotNull(result);
        assertTrue(result.containsKey("casdoorPermissions"));
        assertTrue(result.containsKey("data2"));

        List<Permission> permissions = (List<Permission>) result.get("casdoorPermissions");
        int data2 = (int) result.get("data2");

        assertFalse(permissions.isEmpty());
        assertTrue(data2 > 0);

    }
    @Test
    public void testGetPermissionsByRole() throws IOException {
        PermissionService permissionService = new PermissionService(this.config);
        List<Permission> permissions = permissionService.getPermissionsByRole("permission-built-in");
        assertNotNull(permissions);
    }
}
