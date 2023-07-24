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
// See the License for the specific language governing CasdoorPermissions and
// limitations under the License.
package org.casbin.casdoor.service;

import java.util.List;
import java.util.Map;

import org.casbin.casdoor.annotation.RequireOwnerInQuery;
import org.casbin.casdoor.entity.CasdoorToken;
import org.casbin.casdoor.response.CasdoorActionResponse;
import org.casbin.casdoor.response.CasdoorResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface CasdoorTokenService {
    @GET("get-tokens")
    @RequireOwnerInQuery
    Call<CasdoorResponse<List<CasdoorToken>, Object>> getTokens();

    @GET("get-tokens")
    @RequireOwnerInQuery
    Call<CasdoorResponse<List<CasdoorToken>, Integer>> getPaginationTokens(@Query("p") int page,
            @Query("pageSize") int pageSize, @QueryMap Map<String, Object> query);

    @POST("delete-token")
    Call<CasdoorActionResponse> deleteToken(@Body CasdoorToken token);
}