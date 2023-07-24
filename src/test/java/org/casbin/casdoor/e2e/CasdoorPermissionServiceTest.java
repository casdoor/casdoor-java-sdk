package org.casbin.casdoor.e2e;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.casbin.casdoor.CasdoorClient;
import org.casbin.casdoor.entity.CasdoorPermission;
import org.casbin.casdoor.response.CasdoorActionResponse;
import org.casbin.casdoor.response.CasdoorResponse;
import org.casbin.casdoor.service.CasdoorPermissionService;
import org.casbin.casdoor.support.CasdoorClientProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CasdoorPermissionServiceTest {
    private CasdoorClient client = CasdoorClientProvider.get();
    private CasdoorPermissionService subject;

    @Before
    public void setUp() {
        subject = client.createService(CasdoorPermissionService.class);
    }

    @Test
    public void testGetPermission() throws IOException {
        CasdoorPermission permission = subject.getPermission("permission-built-in").execute().body().getData();
        assertNotNull(permission);

    }

    @Test
    public void testGetPermissions() throws IOException {
        List<CasdoorPermission> permissions = subject.getPermissions().execute().body().getData();
        assertNotNull(permissions);
    }

    @Test
    public void testModifyPermission() throws IOException {
        CasdoorPermission permission = new CasdoorPermission();
        permission.setOwner("built-in");
        permission.setName("test-modify-permission");
        CasdoorActionResponse response = subject.addPermission(permission).execute().body();
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        permission.setDisplayName("test-display-name");
        response = subject.updatePermission("test-modify-permission", permission).execute().body();
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        response = subject.deletePermission(permission).execute().body();
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());
    }

    @Test
    public void testGetPaginationPermissions() throws IOException {
        Map<String, String> queryMap = new HashMap<>();
        CasdoorResponse<List<CasdoorPermission>, Integer> response = subject
                .getPaginationPermissions(1, 10, queryMap).execute().body();
        assertNotNull(response);

        List<CasdoorPermission> permissions = response.getData();
        int count = response.getData2();

        assertFalse(permissions.isEmpty());
        assertTrue(count > 0);

    }

    @Test
    public void testGetPermissionsByRole() throws IOException {
        List<CasdoorPermission> permissions = subject
                .getPermissionsByRole("permission-built-in", "permission-built-in").execute().body().getData();
        assertNotNull(permissions);
    }
}
