package org.casbin.casdoor;

import org.casbin.casdoor.service.EnforcerService;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EnforcerServiceTest extends CasdoorServiceTest {
    @Test
    public void testEnforce() throws IOException {
        EnforcerService enforcerService = new EnforcerService(this.config);
        String[] casbinRequest = {"example-org/example-user", "example-resource", "example-action"};

        boolean result = enforcerService.enforce("permission-built-in", "model-built-in", "Casdoor", casbinRequest);

        assertTrue(result);
    }

    @Test
    public void testBatchEnforce() throws IOException {
        EnforcerService enforcerService = new EnforcerService(this.config);
        String[][] casbinRequests = new String[][]{
                {"example-org/example-user", "example-resource", "example-action"},
                {"example-org/example-user2", "example-resource", "example-action"},
                {"example-org/example-user3", "example-resource", "example-action"}};
        Boolean[][] results = enforcerService.batchEnforce("permission-built-in", "model-built-in", "Casdoor", casbinRequests);
        boolean[][] tar = new boolean[][] {
                {true, true, false}
        };
        assertEquals(tar,results);

    }
}