package org.casbin.casdoor;

import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorUser;
import org.casbin.casdoor.service.CasdoorUserService;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class CasdoorUserServiceTest {

    private CasdoorConfig casdoorConfig;

    /**
     * You should replace the initConfig() method's content with your own Casdoor instance.
     */
    @Before
    public void initConfig() {
        this.casdoorConfig = new CasdoorConfig(
                "http://localhost:8000",
                "f485cd52dab369c8551a",
                "1d7221b217ed3d12100da5e208aa93c8770e4a81",
                "CasdoorSecret",
                "built-in"
        );
    }

    @Test
    public void testGetUser() throws Exception {
        CasdoorUserService casdoorUserService = new CasdoorUserService(this.casdoorConfig);
        CasdoorUser casdoorUser = casdoorUserService.getUser("admin");
        Assert.assertNotNull(casdoorUser);
    }

    @Test
    public void testGetUsers() throws Exception {
        CasdoorUserService casdoorUserService = new CasdoorUserService(this.casdoorConfig);
        CasdoorUser[] casdoorUsers = casdoorUserService.getUsers();
        Assert.assertNotNull(casdoorUsers);
    }

    @Test
    public void testModifyUser() throws IOException {
        CasdoorUserService casdoorUserService = new CasdoorUserService(this.casdoorConfig);

        CasdoorUser user = new CasdoorUser();
        user.setOwner("built-in");
        user.setName("test-modify-user");
        CasdoorResponse response = casdoorUserService.addUser(user);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        user.setDisplayName("test-display-name");
        response = casdoorUserService.updateUser(user);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        response = casdoorUserService.deleteUser(user);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());
    }


}
