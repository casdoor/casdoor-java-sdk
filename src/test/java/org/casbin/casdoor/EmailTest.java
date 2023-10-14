// Copyright 2023 The Casdoor Authors. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.casbin.casdoor;

import org.casbin.casdoor.service.EmailService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class EmailTest {

    private final EmailService emailService = new EmailService(TestDefaultConfig.InitConfig());


    @Test
    public void EmailTest() throws IOException {
        try {
            CasdoorResponse response = emailService.sendEmail(
                    "casbin",
                    "casdoor-java-sdk website test",
                    "admin",
                    new String[]{"TestSmtpServer"});
        } catch (Exception e) {
            if (!e.getMessage().contains("535 Error: authentication failed, system busy")) {
                fail("Failed to get objects: " + e.getMessage());
            }
        }
    }
}
