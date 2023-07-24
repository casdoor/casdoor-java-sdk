// Copyright 2022 The Casdoor Authors. All Rights Reserved.
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

import java.io.IOException;

import org.casbin.casdoor.entity.CasdoorOrganization;
import org.casbin.casdoor.entity.CasdoorUser;
import org.casbin.casdoor.service.CasdoorAccountService;
import org.casbin.casdoor.support.ConfigFactory;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CasdoorAccountServiceTest {
    private CasdoorAccountService subject;

    @Before
    public void setUp() {
        /* Given */
        subject = new CasdoorAccountService(ConfigFactory.getConfig());
    }

    @Test
    public void setPassword() throws Exception {
        CasdoorResponse response = subject.setPassword("admin", "123456", "123456");
        Assert.assertEquals("ok", response.getStatus());
    }

    @Test
    public void getAccount() throws IOException {
        /* When */
        CasdoorResponse<CasdoorUser, CasdoorOrganization> response = subject.getAccount("eyJhbGciOiJSUzI1NiIsImtpZCI6ImNlcnQtYnVpbHQtaW4iLCJ0eXAiOiJKV1QifQ." +
                "eyJvd25lciI6InlpY2VueXVuIiwibmFtZSI6InljYWRtaW4iLCJjcmVhdGVkVGltZSI6IjIwMjMtMDctMjFUMDE6MDA6MDErM" +
                "Dg6MDAiLCJ1cGRhdGVkVGltZSI6IiIsImlkIjoiYmE3Mjk0N2QtZWEyYS00NjVhLWE0NGEtZGFlZTdhN2M2MWRkIiwidHlwZS" +
                "I6Im5vcm1hbC11c2VyIiwicGFzc3dvcmQiOiIiLCJwYXNzd29yZFNhbHQiOiIiLCJkaXNwbGF5TmFtZSI6IuW8iOWykeeuoee" +
                "QhuWRmCIsImZpcnN0TmFtZSI6IiIsImxhc3ROYW1lIjoiIiwiYXZhdGFyIjoiaHR0cDovL2xvY2FsaG9zdDo4MDAwL2ltYWdl" +
                "cy9kZWZhdWx0LWF2YXRhci5zdmciLCJwZXJtYW5lbnRBdmF0YXIiOiIiLCJlbWFpbCI6ImFAZXhhbXBsZS5jb20iLCJlbWFpb" +
                "FZlcmlmaWVkIjpmYWxzZSwicGhvbmUiOiIxMzYxMjM0NTY3OCIsImxvY2F0aW9uIjoi5YyX5LqsIiwiYWRkcmVzcyI6W10sIm" +
                "FmZmlsaWF0aW9uIjoi5byI5bKR5LqRIiwidGl0bGUiOiLlt6XnqIvluIgiLCJpZENhcmRUeXBlIjoiIiwiaWRDYXJkIjoiIiw" +
                "iaG9tZXBhZ2UiOiJodHRwOi8vYS5jb20iLCJiaW8iOiIiLCJyZWdpb24iOiJDTiIsImxhbmd1YWdlIjoiIiwiZ2VuZGVyIjoi" +
                "IiwiYmlydGhkYXkiOiIiLCJlZHVjYXRpb24iOiIiLCJzY29yZSI6MjAwMCwia2FybWEiOjAsInJhbmtpbmciOjEsImlzRGVmY" +
                "XVsdEF2YXRhciI6ZmFsc2UsImlzT25saW5lIjpmYWxzZSwiaXNBZG1pbiI6dHJ1ZSwiaXNHbG9iYWxBZG1pbiI6ZmFsc2UsIm" +
                "lzRm9yYmlkZGVuIjpmYWxzZSwiaXNEZWxldGVkIjpmYWxzZSwic2lnbnVwQXBwbGljYXRpb24iOiJqYWd1YXItYXBwY2VudGV" +
                "yIiwiaGFzaCI6IiIsInByZUhhc2giOiIiLCJjcmVhdGVkSXAiOiIiLCJsYXN0U2lnbmluVGltZSI6IiIsImxhc3RTaWduaW5J" +
                "cCI6IiIsImxkYXAiOiIiLCJwcm9wZXJ0aWVzIjp7fSwicm9sZXMiOltdLCJwZXJtaXNzaW9ucyI6W10sImxhc3RTaWduaW5Xc" +
                "m9uZ1RpbWUiOiIiLCJzaWduaW5Xcm9uZ1RpbWVzIjowLCJ0b2tlblR5cGUiOiJhY2Nlc3MtdG9rZW4iLCJzY29wZSI6Im9wZW" +
                "5pZCBwcm9maWxlIGVtYWlsIGFkZHJlc3MgcGhvbmUiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjcwMDEiLCJzdWIiOiJiYTc" +
                "yOTQ3ZC1lYTJhLTQ2NWEtYTQ0YS1kYWVlN2E3YzYxZGQiLCJhdWQiOlsiNjM2OGExMDQxYjQ2ZTVkMTU2YWIiXSwiZXhwIjox" +
                "NjkwNTE2NzQxLCJuYmYiOjE2ODk5MTE5NDEsImlhdCI6MTY4OTkxMTk0MSwianRpIjoiYWRtaW4vNjliMGM0NjUtZGU1MC00N" +
                "GUwLWI4NzItMWQxODE4YWNjYTZhIn0.wcXfd-CgMzfSPX0Q8Pl9e1ncS_dQ19I0etNeRAZatXhBGLK6m1scolMEIR2bDoPUfT" +
                "4c5dgpFJhF4WOwdFMZhedtAqQMn1G6UE7Jneq9afXDiVXjNpLPHu3-SJnseBI1OCTkAkXpKs7qXa-Crj_aRXI03Q0EjuKozrV" +
                "nV4y1Pnmt30puu_JN215v0vzOMcLAF6V8ek1XfICoPS8o0XWavkLP0T9OP9k-1dErrzMWHu71gbZKpqFwIa8suaz3lFLM2_MY" +
                "-RvH59h2XrtiMtA35ECvnUVfUzzU5NAlp1Spa4o9QPZGko6zaNvbvNLQp4X5-vYcRJiX5p7rqqxZ-0Di5UpfPhwMlQYtST-Wl" +
                "iiRGDVno_WS5SvlSSyPGT8vVbtxNTbkRMOy_D6BhI0fIISCSOEd0gSRgtbg6RajStsc60WeoScl8JUiJoNQ2w2YV45wWtuKKc" +
                "MHbG4Ugu9HdnxFh7Rd9MDbrT2HEEbxz_PmuE9zSjisPGLyBCVPv-QmRzEjBRj0QgnRRaxzTCaYP76K6YXv2nHRYQtI598WM4U" +
                "AjQ-D82GmQNTQvUTMsBUxI5ZF0OgI3QxLgNav3OSwNSZ0Zf1-U49W5EtIt0BuuIQo_y0Y_KP-edO-M9NSdc9HfNS1n36PX0iG" +
                "Q6OfkKwRSb_UEFh0WH0rINA0IFGOOQAF1YY");
        /* Then */
        Assert.assertEquals("ok", response.getStatus());
        Assert.assertNotNull(response.getData());
        Assert.assertNotNull(response.getData2());
    }
}
