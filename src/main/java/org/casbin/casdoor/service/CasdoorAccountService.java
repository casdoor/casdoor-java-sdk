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

package org.casbin.casdoor.service;

import org.casbin.casdoor.annotation.RequireOwnerInQuery;
import org.casbin.casdoor.entity.CasdoorOrganization;
import org.casbin.casdoor.entity.CasdoorUser;
import org.casbin.casdoor.response.CasdoorActionResponse;
import org.casbin.casdoor.response.CasdoorResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CasdoorAccountService {

    /**
     * Set user password by username, old and new passwords
     *
     * @param userOwner   user owner
     * @param userName    username
     * @param oldPassword old password
     * @param newPassword new password
     * @return CasdoorActionResponse
     */
    @FormUrlEncoded
    @RequireOwnerInQuery
    @POST("set-password")
    Call<CasdoorActionResponse> setPassword(@Field("userOwner") String userOwner,
            @Field("userName") String userName, @Field("oldPassword") String oldPassword,
            @Field("newPassword") String newPassword);

    /**
     * Get current user
     *
     * @param token access token of current user
     * @return user and organization info of current user
     */
    @GET("get-account")
    Call<CasdoorResponse<CasdoorUser, CasdoorOrganization>> getAccount(@Query("access_token") String token);

}
