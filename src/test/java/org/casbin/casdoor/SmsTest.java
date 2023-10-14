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

import org.casbin.casdoor.entity.SmsForm;
import org.casbin.casdoor.service.SmsService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class SmsTest {

    private final SmsService smsService = new SmsService(
            TestDefaultConfig.InitConfig());

    @Test
    public void testSms() {
        SmsForm smsForm = new SmsForm(
                "casdoor",
                new String[]{"+8613854673829", "+441932567890"});
        try {
            smsService.sendSms(smsForm.content, smsForm.receivers);
        } catch (Exception e) {
            fail("Failed to send sms:" + e.getMessage());
        }

    }

}
