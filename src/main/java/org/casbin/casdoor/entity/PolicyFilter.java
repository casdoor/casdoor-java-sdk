// Copyright 2026 The Casdoor Authors. All Rights Reserved.
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

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * PolicyFilter filters the policies of an enforcer, see PolicyService.getFilteredPolicies().
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolicyFilter implements Serializable {
    public String ptype;
    public Integer fieldIndex;
    public String[] fieldValues;

    public PolicyFilter() {
    }

    public PolicyFilter(String ptype, Integer fieldIndex, String... fieldValues) {
        this.ptype = ptype;
        this.fieldIndex = fieldIndex;
        this.fieldValues = fieldValues;
    }
}
