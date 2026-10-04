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

package org.casbin.casdoor.util.http;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class HttpClient {
    private static OkHttpClient okHttpClient = new OkHttpClient();

    private static Map<String, String> authorization(String credential) {
        java.util.HashMap<String, String> headers = new java.util.HashMap<>();
        headers.put("Authorization", credential);
        return headers;
    }

    private static String execute(Request.Builder builder, Map<String, String> headers) throws IOException {
        if (headers != null) {
            headers.forEach(builder::header);
        }
        try (Response response = okHttpClient.newCall(builder.build()).execute()) {
            String body = response.body() == null ? "" : response.body().string();
            // Casdoor returns its JSON error response with 403 when the caller has no permission
            if (!response.isSuccessful() && response.code() != 403) {
                throw new IOException(String.format("status code: %d, body: %s", response.code(), body));
            }
            return body;
        }
    }

    public static String get(String url, Map<String, String> headers) throws IOException {
        return execute(new Request.Builder().url(url), headers);
    }

    public static String postString(String url, String objStr, Map<String, String> headers) throws IOException {
        MediaType mediaType = MediaType.parse("text/plain;charset=UTF-8");
        return execute(new Request.Builder().url(url).post(RequestBody.create(mediaType, objStr == null ? "" : objStr)), headers);
    }

    public static String postFile(String url, String fileName, byte[] fileBytes, Map<String, String> headers) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName,
                        RequestBody.create(MediaType.parse("multipart/form-data"), fileBytes))
                .build();
        return execute(new Request.Builder().url(url).post(requestBody), headers);
    }

    public static String postMultipartForm(String url, Map<String, String> form, Map<String, String> headers) throws IOException {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        form.forEach((key, value) -> builder.addFormDataPart(key, value == null ? "" : value));
        return execute(new Request.Builder().url(url).post(builder.build()), headers);
    }

    public static String postForm(String url, Map<String, String> formData, Map<String, String> headers, boolean encoded) throws IOException {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formData.forEach((key, value) -> formBodyBuilder.add(key, value == null ? "" : value));
        return execute(new Request.Builder().url(url).post(formBodyBuilder.build()), headers);
    }

    public static String syncGet(String url, String credential) throws IOException {
        return get(url, authorization(credential));
    }

    public static String postString(String url, String objStr, String credential) throws IOException {
        return postString(url, objStr, authorization(credential));
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
     * @param credential credential
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
     * @param customClient custom http client
     */
    public static void setHttpClient(OkHttpClient customClient) {
        okHttpClient = customClient;
    }
}
