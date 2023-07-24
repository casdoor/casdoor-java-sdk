package org.casbin.casdoor.e2e;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.casbin.casdoor.service.CasdoorEnforcerService;
import org.junit.Test;

public class CasdoorEnforcerServiceTest extends CasdoorServiceTestSupport {
    @Test
    public void testEnforce() throws IOException {
        CasdoorEnforcerService casdoorEnforcerService = new CasdoorEnforcerService(this.casdoorConfig);
        String[] casbinRequest = { "example-org/example-user", "example-resource", "example-action" };

        boolean result = casdoorEnforcerService.enforce("permission-built-in", "model-built-in", "Casdoor",
                casbinRequest);

        assertTrue(result);
    }

    @Test
    public void testBatchEnforce() throws IOException {
        CasdoorEnforcerService casdoorEnforcerService = new CasdoorEnforcerService(this.casdoorConfig);
        String[][] casbinRequests = new String[][] {
                { "example-org/example-user", "example-resource", "example-action" },
                { "example-org/example-user2", "example-resource", "example-action" },
                { "example-org/example-user3", "example-resource", "example-action" } };
        Boolean[][] results = casdoorEnforcerService.batchEnforce("permission-built-in", "model-built-in", "Casdoor",
                casbinRequests);
        boolean[][] tar = new boolean[][] {
                { true, true, false }
        };
        assertArrayEquals(tar, results);

    }
}