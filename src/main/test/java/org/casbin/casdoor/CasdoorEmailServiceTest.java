package org.casbin.casdoor;

import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.service.CasdoorEmailService;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

public class CasdoorEmailServiceTest {

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
                "built-in",
                "app-built-in"
        );
    }

    @Test
    public void testSendEmail() throws IOException {
        String title = "Casdoor Verification Code";
        String content = String.format("You have requested a verification code at Casdoor. Here is your code: %s, please enter in 5 minutes.", randomCode());
        String sender = "";
        String receiver = "";
        CasdoorEmailService casdoorEmailService = new CasdoorEmailService(casdoorConfig);
        CasdoorResponse response = casdoorEmailService.sendEmail(title, content, sender, receiver);
        Assert.assertEquals("ok", response.getStatus());
    }

    private static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
}
