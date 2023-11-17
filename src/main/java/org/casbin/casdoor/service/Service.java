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

    private final ObjectMapper objectMapper;
    private final Config config;
    private final String credential;

    protected Service(Config config) {
        this.config = getConfig();
        this.credential = Credentials.basic(config.getClientId(), getConfig().getClientSecret());
        objectMapper = new ObjectMapper() {{
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }};
    }

    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    protected Config getConfig() {
        return config;
    }

    protected String getCredential() {
        return credential;
    }

    protected <T1, T2> CasdoorResponse<T1, T2> doGet(@NotNull String action, @Nullable java.util.Map<String, String> queryParams, TypeReference<CasdoorResponse<T1, T2>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", getConfig().getEndpoint(), action, Map.mapToUrlParams(queryParams));
        String response = HttpClient.syncGet(url, getCredential());
        CasdoorResponse<T1, T2> resp = getObjectMapper().readValue(response, typeReference);
        if (!Objects.equals(resp.getStatus(), "ok")) {
            throw new Exception(String.format("Failed fetching %s : %s", url, resp.getMsg()));
        }

        return resp;
    }

    protected <T1, T2> CasdoorResponse<T1, T2> doPost(@NotNull String action, @Nullable java.util.Map<String, String> queryParams, java.util.Map<String, String> postForm, TypeReference<CasdoorResponse<T1, T2>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", getConfig().getEndpoint(), action, Map.mapToUrlParams(queryParams));
        String response = HttpClient.postForm(url, postForm, getCredential());
        CasdoorResponse<T1, T2> resp = getObjectMapper().readValue(response, typeReference);
        if (!Objects.equals(resp.getStatus(), "ok")) {
            throw new Exception(String.format("Failed fetching %s : %s", url, resp.getMsg()));
        }

        return resp;
    }

    protected <T1, T2> CasdoorResponse<T1, T2> doPost(@NotNull String action, @Nullable java.util.Map<String, String> queryParams, String postString, TypeReference<CasdoorResponse<T1, T2>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", getConfig().getEndpoint(), action, Map.mapToUrlParams(queryParams));
        String response = HttpClient.postString(url, postString, getCredential());
        CasdoorResponse<T1, T2> resp = getObjectMapper().readValue(response, typeReference);
        if (!Objects.equals(resp.getStatus(), "ok")) {
            throw new Exception(String.format("Failed fetching %s : %s", url, resp.getMsg()));
        }
        return resp;
    }

    protected <T1, T2> CasdoorResponse<T1, T2> doPost(String action, @Nullable java.util.Map<String, String> queryParams, File postFile, TypeReference<CasdoorResponse<T1, T2>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", getConfig().getEndpoint(), action, Map.mapToUrlParams(queryParams));
        String response = HttpClient.postFile(url, postFile, getCredential());
        CasdoorResponse<T1, T2> resp = getObjectMapper().readValue(response, typeReference);
        if (!Objects.equals(resp.getStatus(), "ok")) {
            throw new Exception(String.format("Failed fetching %s : %s", url, resp.getMsg()));
        }

        return resp;
    }
}
