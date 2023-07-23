package org.casbin.casdoor;

import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorPermission;
import org.casbin.casdoor.service.CasdoorPermissionService;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CasdoorPermissionServiceTest extends CasdoorServiceTest {
    @Test
    public void testGetPermission() throws IOException {
        CasdoorPermissionService casdoorPermissionService = new CasdoorPermissionService(this.casdoorConfig);
        CasdoorPermission permission = casdoorPermissionService.getPermission("permission-built-in");
        assertNotNull(permission);

    }

    @Test
    public void testGetPermissions() throws IOException {
        CasdoorPermissionService casdoorPermissionService = new CasdoorPermissionService(this.casdoorConfig);
        List<CasdoorPermission> permissions = casdoorPermissionService.getPermissions();
        assertNotNull(permissions);
    }

    @Test
    public void testModifyPermission() throws IOException {
        CasdoorPermissionService casdoorPermissionService = new CasdoorPermissionService(this.casdoorConfig);

        CasdoorPermission permission = new CasdoorPermission();
        permission.setOwner("built-in");
        permission.setName("test-modify-permission");
        CasdoorResponse response = casdoorPermissionService.addPermission(permission);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        permission.setDisplayName("test-display-name");
        response = casdoorPermissionService.updatePermission(permission);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        response = casdoorPermissionService.deletePermission(permission);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());
    }

    @Test
    public void testGetPaginationPermissions() throws IOException {
        CasdoorPermissionService casdoorPermissionService = new CasdoorPermissionService(this.casdoorConfig);
        Map<String, String> queryMap = new HashMap<>();
        Map<String, Object> result = casdoorPermissionService.getPaginationPermissions(1, 10, queryMap);
        assertNotNull(result);
        assertTrue(result.containsKey("casdoorPermissions"));
        assertTrue(result.containsKey("data2"));

        List<CasdoorPermission> permissions = (List<CasdoorPermission>) result.get("casdoorPermissions");
        int data2 = (int) result.get("data2");

        assertFalse(permissions.isEmpty());
        assertTrue(data2 > 0);

    }
    @Test
    public void testGetPermissionsByRole() throws IOException {
        CasdoorPermissionService casdoorPermissionService = new CasdoorPermissionService(this.casdoorConfig);
        List<CasdoorPermission> permissions = casdoorPermissionService.getPermissionsByRole("permission-built-in");
        assertNotNull(permissions);
    }
}
