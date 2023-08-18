// Copyright 2023 The casbin Authors. All Rights Reserved.
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
package org.casbin.casdoor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorSession;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.SessionOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public class CasdoorSessionService extends CasdoorService {
    public CasdoorSessionService(CasdoorConfig casdoorConfig) {
        super(casdoorConfig);
    }

    public List<CasdoorSession> getSessions() throws IOException {
        CasdoorResponse<List<CasdoorSession>, Object> resp = doGet("get-sessions",
                Map.of("owner", casdoorConfig.getOrganizationName()), new TypeReference<CasdoorResponse<List<CasdoorSession>, Object>>() {});
        return resp.getData();
    }
    public java.util.Map<String, Object> getPaginationSessions(int p, int pageSize, @Nullable java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<CasdoorSession[], Object> casdoorResponse = doGet("get-sessions",
                Map.mergeMap(Map.of("owner", casdoorConfig.getOrganizationName(),
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<CasdoorSession[], Object>>() {});

        return Map.of("casdoorSessions", casdoorResponse.getData(), "data2", casdoorResponse.getData2());
    }
    public CasdoorSession getSession(String name) throws IOException {
        CasdoorResponse<CasdoorSession, Object> response = doGet("get-session",
                Map.of("id", casdoorConfig.getOrganizationName() + "/" + name), new TypeReference<CasdoorResponse<CasdoorSession, Object>>() {});
        return response.getData();
    }

    public CasdoorResponse<String, Object> updateSession(CasdoorSession casdoorSession) throws IOException {
        return modifySession(SessionOperations.UPDATE_SESSION, casdoorSession);
    }

    public CasdoorResponse<String, Object> updateSessionForColumns(CasdoorSession casdoorSession, String... columns) throws IOException {
        return modifySession(SessionOperations.UPDATE_SESSION, casdoorSession);
    }

    public CasdoorResponse<String, Object> addSession(CasdoorSession casdoorSession) throws IOException {
        return modifySession(SessionOperations.ADD_SESSION, casdoorSession);
    }

    public CasdoorResponse<String, Object> deleteSession(CasdoorSession casdoorSession) throws IOException {
        return modifySession(SessionOperations.DELETE_SESSION, casdoorSession);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifySession(SessionOperations method, CasdoorSession session) throws IOException {
        String id = session.getOwner() + "/" + session.getName();
        String payload = objectMapper.writeValueAsString(session);
        return doPost(method.getOperation(), null, payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {});
    }
}
