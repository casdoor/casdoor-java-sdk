package org.casbin.casdoor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Credentials;
import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.exception.Exception;
import org.casbin.casdoor.util.Map;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.casbin.casdoor.util.http.HttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public abstract class Service {
    protected final ObjectMapper objectMapper = new ObjectMapper(){{
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }};

    protected final Config config;
    protected final String credential;
    protected Service(Config config) {
        this.config = config;
        this.credential = Credentials.basic(config.getClientId(), config.getClientSecret());
    }

    protected <T1, T2> CasdoorResponse<T1, T2> doGet(@NotNull String action, @Nullable java.util.Map<String, String> queryParams, TypeReference<CasdoorResponse<T1, T2>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", config.getEndpoint(), action, Map.mapToUrlParams(queryParams));
        String response = HttpClient.syncGet(url, credential);
        CasdoorResponse<T1, T2> resp = objectMapper.readValue(response, typeReference);
        if (!Objects.equals(resp.getStatus(), "ok")) {
            throw new Exception(String.format("Failed fetching %s : %s", url, resp.getMsg()));
        }

        return resp;
    }

    protected <T1, T2> CasdoorResponse<T1, T2> doPost(@NotNull String action, @Nullable java.util.Map<String, String> queryParams, java.util.Map<String, String> postForm, TypeReference<CasdoorResponse<T1, T2>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", config.getEndpoint(), action, Map.mapToUrlParams(queryParams));
        String response = HttpClient.postForm(url, postForm, credential);
        CasdoorResponse<T1, T2> resp = objectMapper.readValue(response, typeReference);
        if (!Objects.equals(resp.getStatus(), "ok")) {
            throw new Exception(String.format("Failed fetching %s : %s", url, resp.getMsg()));
        }

        return resp;
    }

    protected <T1, T2> CasdoorResponse<T1, T2> doPost(@NotNull String action, @Nullable java.util.Map<String, String> queryParams, String postString, TypeReference<CasdoorResponse<T1, T2>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", config.getEndpoint(), action, Map.mapToUrlParams(queryParams));
        String response = HttpClient.postString(url, postString, credential);
        CasdoorResponse<T1, T2> resp = objectMapper.readValue(response, typeReference);
        if (!Objects.equals(resp.getStatus(), "ok")) {
            throw new Exception(String.format("Failed fetching %s : %s", url, resp.getMsg()));
        }

        return resp;
    }

    protected <T1, T2> CasdoorResponse<T1, T2> doPost(String action, @Nullable java.util.Map<String, String> queryParams, File postFile, TypeReference<CasdoorResponse<T1, T2>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", config.getEndpoint(), action, Map.mapToUrlParams(queryParams));
        String response = HttpClient.postFile(url, postFile, credential);
        CasdoorResponse<T1, T2> resp = objectMapper.readValue(response, typeReference);
        if (!Objects.equals(resp.getStatus(), "ok")) {
            throw new Exception(String.format("Failed fetching %s : %s", url, resp.getMsg()));
        }

        return resp;
    }
}
