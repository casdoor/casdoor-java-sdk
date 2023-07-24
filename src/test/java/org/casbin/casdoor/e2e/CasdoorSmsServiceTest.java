package org.casbin.casdoor.e2e;

import java.io.IOException;
import java.util.Random;

import org.casbin.casdoor.CasdoorClient;
import org.casbin.casdoor.command.SendSmsCommand;
import org.casbin.casdoor.response.CasdoorActionResponse;
import org.casbin.casdoor.service.CasdoorSmsService;
import org.casbin.casdoor.support.CasdoorClientProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CasdoorSmsServiceTest {
        private CasdoorClient client = CasdoorClientProvider.get();
    private CasdoorSmsService subject;

    @Before
    public void setUp() {
        subject = client.createService(CasdoorSmsService.class);
    }

    @Test
    public void testSendSms() throws IOException {
        String receiver = "";
        CasdoorActionResponse response = subject.sendSms(new SendSmsCommand("admin/" + randomCode(), receiver)).execute().body();
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
