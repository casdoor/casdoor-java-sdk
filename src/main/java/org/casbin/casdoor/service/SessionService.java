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

package org.casbin.casdoor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.Session;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.SessionOperations;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public class SessionService extends Service {
    public SessionService(Config config) {
        super(config);
    }

    public List<Session> getSessions() throws IOException {
        CasdoorResponse<List<Session>, Object> resp = doGet("get-sessions",
                Map.of("owner", getConfig().getOrganizationName()), new TypeReference<CasdoorResponse<List<Session>, Object>>() {});
        return resp.getData();
    }
    public java.util.Map<String, Object> getPaginationSessions(int p, int pageSize, @Nullable java.util.Map<String, String> queryMap) throws IOException {
        CasdoorResponse<Session[], Object> casdoorResponse = doGet("get-sessions",
                Map.mergeMap(Map.of("owner", getConfig().getOrganizationName(),
                        "p", Integer.toString(p),
                        "pageSize", Integer.toString(pageSize)), queryMap), new TypeReference<CasdoorResponse<Session[], Object>>() {});

        return Map.of("casdoorSessions", casdoorResponse.getData(), "data2", casdoorResponse.getData2());
    }
    public Session getSession(String name,String application) throws IOException {
        CasdoorResponse<Session, Object> response = doGet("get-session",
                Map.of("sessionPkId", getConfig().getOrganizationName() + "/" + name + "/" + application), new TypeReference<CasdoorResponse<Session, Object>>() {});
        return response.getData();
    }

    public CasdoorResponse<String, Object> updateSession(Session session) throws IOException {
        return modifySession(SessionOperations.UPDATE_SESSION, session);
    }

    public CasdoorResponse<String, Object> updateSessionForColumns(Session session, String... columns) throws IOException {
        return modifySession(SessionOperations.UPDATE_SESSION, session);
    }

    public CasdoorResponse<String, Object> addSession(Session session) throws IOException {
        return modifySession(SessionOperations.ADD_SESSION, session);
    }

    public CasdoorResponse<String, Object> deleteSession(Session session) throws IOException {
        return modifySession(SessionOperations.DELETE_SESSION, session);
    }

    private <T1, T2> CasdoorResponse<T1, T2> modifySession(SessionOperations method, Session session) throws IOException {
        String id = session.owner + "/" + session.name;
        session.owner = getConfig().getOrganizationName();
        String payload = getObjectMapper().writeValueAsString(session);
        return doPost(method.getOperation(), Map.of("id",id), payload,
                new TypeReference<CasdoorResponse<T1, T2>>() {});
    }
}
