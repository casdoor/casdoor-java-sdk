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

import org.casbin.casdoor.entity.Session;
import org.casbin.casdoor.service.SessionService;
import org.casbin.casdoor.support.ConfigFactory;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class SessionServiceTest {
    private SessionService casdoorSessionService;

    @Before
    public void init() {
        casdoorSessionService = new SessionService(ConfigFactory.getConfig());
    }

    @Test
    public void testGetSession() throws IOException {
        Session session = casdoorSessionService.getSession("example-session-name");
        assertNotNull(session);
    }

    @Test
    public void testGetSessions() throws IOException {
        List<Session> sessions = casdoorSessionService.getSessions();
        assertNotNull(sessions);
    }

    @Test
    public void testGetPaginationSessions() throws IOException {
        Map<String, Object> paginationSessions = casdoorSessionService.getPaginationSessions(1, 10, null);
        assertNotNull(paginationSessions);
    }

    @Test
    public void testModifySession() throws IOException {
        Session session = new Session();
        session.setOwner("test-owner");
        session.setName("test-session-name");
        session.setApplication("built-in");
        CasdoorResponse response = casdoorSessionService.addSession(session);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());

        session.setName("test-updated-session-name");
        response = casdoorSessionService.updateSession(session);
        Assert.assertEquals("ok", response.getStatus());

        response = casdoorSessionService.deleteSession(session);
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertEquals("Affected", response.getData());
    }
}
