// Copyright 2021 The casbin Authors. All Rights Reserved.
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

public class CasdoorSmsForm implements Serializable {
    private String organizationId;
    private String content;
    private String[] receivers;

    public CasdoorSmsForm(String organizationId, String content, String[] receivers) {
        this.organizationId = organizationId;
        this.content = content;
        this.receivers = receivers;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getReceivers() {
        return receivers;
    }

    public void setReceivers(String[] receivers) {
        this.receivers = receivers;
    }

    @Override
    public String toString() {
        return "CasdoorSmsForm{" +
                "organizationId='" + organizationId + '\'' +
                ", content='" + content + '\'' +
                ", receivers=" + Arrays.toString(receivers) +
                '}';
    }
}
