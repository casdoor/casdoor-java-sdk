package org.casbin.casdoor;

import org.casbin.casdoor.service.CasdoorEnforcerService;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CasdoorEnforcerServiceTest extends CasdoorServiceTest {
    @Test
    public void testEnforce() throws IOException {
        CasdoorEnforcerService casdoorEnforcerService = new CasdoorEnforcerService(this.casdoorConfig);
        String[] casbinRequest = {"example-org/example-user", "example-resource", "example-action"};

        boolean result = casdoorEnforcerService.enforce("permission-built-in", "model-built-in", "Casdoor", casbinRequest);

        assertTrue(result);
    }

    @Test
    public void testBatchEnforce() throws IOException {
        CasdoorEnforcerService casdoorEnforcerService = new CasdoorEnforcerService(this.casdoorConfig);
        String[][] casbinRequests = new String[][]{
                {"example-org/example-user", "example-resource", "example-action"},
                {"example-org/example-user2", "example-resource", "example-action"},
                {"example-org/example-user3", "example-resource", "example-action"}};
        Boolean[][] results = casdoorEnforcerService.batchEnforce("permission-built-in", "model-built-in", "Casdoor", casbinRequests);
        boolean[][] tar = new boolean[][] {
                {true, true, false}
        };
        assertEquals(tar,results);

    }
}