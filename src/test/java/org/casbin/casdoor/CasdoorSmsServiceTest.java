package org.casbin.casdoor;

import org.casbin.casdoor.service.SmsService;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

public class CasdoorSmsServiceTest extends CasdoorServiceTest {
    @Test
    public void testSendSms() throws IOException {
        String receiver = "";
        SmsService casdoorSmsService = new SmsService(config);
        CasdoorResponse response = casdoorSmsService.sendSms(randomCode(), receiver);
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
