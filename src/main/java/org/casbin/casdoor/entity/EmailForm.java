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

package org.casbin.casdoor.entity;

import java.io.Serializable;
import java.util.Arrays;


public class EmailForm implements Serializable {
    public String title;
    public String content;
    public String sender;
    public String[] receivers;

    public EmailForm() {
    }

    public EmailForm(String title, String content, String sender, String[] receivers) {
        this.title = title;
        this.content = content;
        this.sender = sender;
        this.receivers = receivers;
    }
    
}
