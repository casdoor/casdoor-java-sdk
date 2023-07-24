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
import org.casbin.casdoor.entity.CasdoorResource;
import org.casbin.casdoor.response.CasdoorActionResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface CasdoorResourceService {

    @POST
    @RequireOwnerInQuery
    Call<CasdoorActionResponse> uploadResource(@Query("user") String user,
            @Query("application") String applicationName, @Query("tag") String tag, @Query("parent") String parent,
            @Query("fullFilePath") String fullFilePath, @Part MultipartBody.Part file);

    @POST("delete-resource")
    Call<CasdoorActionResponse> deleteResource(@Body CasdoorResource resource);
}
