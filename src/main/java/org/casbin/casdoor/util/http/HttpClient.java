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

package org.casbin.casdoor.util.http;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class HttpClient {
    private static OkHttpClient okHttpClient = new OkHttpClient();

    public static String syncGet(String url, String credential) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", credential)
                .build();
        Response casdoorResponse = okHttpClient.newCall(request).execute();
        if (casdoorResponse.isSuccessful()) {
            return casdoorResponse.body().string();
        }
        return null;
    }

    public static String postString(String url, String objStr, String credential) throws IOException {
        MediaType MEDIA_TYPE = MediaType.parse("text/plain;charset=UTF-8");
        Request request = new Request.Builder().url(url)
                .post(RequestBody.create(MEDIA_TYPE, objStr))
                .header("Authorization", credential)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        }
        return null;
    }

    public static String postFile(String url, File file, String credential) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .header("Authorization", credential)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        return response.body().string();
    }

    /**
     * Post a request of type "application/x-www-form-urlencoded"
     * @param url url
     * @param fromData form data stored in Map
     * @return result as String
     * @throws IOException when request fails
     */
    public static String postForm(String url, Map<String, String> fromData, String credential) throws IOException {

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        fromData.forEach(formBodyBuilder::addEncoded);
        RequestBody formBody = formBodyBuilder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .header("Authorization", credential)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return Objects.requireNonNull(response.body()).string();
        }

    }

    /**
     * SetHttpClient sets custom http Client.
     */
    public static void setHttpClient(OkHttpClient customClient) {
        okHttpClient = customClient;
    }
}
