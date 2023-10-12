package org.casbin.casdoor;

import org.casbin.casdoor.service.EmailService;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

public class EmailServiceTest extends CasdoorServiceTest {
    @Test
    public void testSendEmail() throws IOException {
        String title = "Casdoor Verification Code";
        String content = String.format("You have requested a verification code at Casdoor. Here is your code: %s, please enter in 5 minutes.", randomCode());
        String sender = "";
        String receiver = "";
        EmailService emailService = new EmailService(config);
        CasdoorResponse response = emailService.sendEmail(title, content, sender, receiver);
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
