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

import java.util.List;
import java.util.Map;

import org.casbin.casdoor.annotation.CasdoorId;
import org.casbin.casdoor.annotation.RequireOwnerInQuery;
import org.casbin.casdoor.entity.CasdoorPermission;
import org.casbin.casdoor.response.CasdoorActionResponse;
import org.casbin.casdoor.response.CasdoorResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Tag;

public interface CasdoorPermissionService {

        @GET("get-permission")
        Call<CasdoorResponse<CasdoorPermission, Object>> getPermission(@CasdoorId @Tag String name);

        @GET("get-permissions")
        @RequireOwnerInQuery
        Call<CasdoorResponse<List<CasdoorPermission>, Object>> getPermissions();

        @GET("get-permissions-by-role")
        Call<CasdoorResponse<List<CasdoorPermission>, Integer>> getPermissionsByRole(@CasdoorId @Tag String name,
                        @Query("owner") String owner);

        @GET("get-permissions")
        @RequireOwnerInQuery
        Call<CasdoorResponse<List<CasdoorPermission>, Integer>> getPaginationPermissions(
                        @Query("p") int p, @Query("pageSize") int pageSize, @QueryMap Map<String, String> query);

        @POST("add-permission")
        Call<CasdoorActionResponse> addPermission(@Body CasdoorPermission permission);

        @POST("update-permission")
        Call<CasdoorActionResponse> updatePermission(@CasdoorId @Tag String name, @Body CasdoorPermission permission);

        @POST("delete-permission")
        Call<CasdoorActionResponse> deletePermission(@Body CasdoorPermission permission);

}
