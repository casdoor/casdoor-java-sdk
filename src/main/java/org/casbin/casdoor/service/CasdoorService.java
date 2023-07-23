package org.casbin.casdoor.service;

import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Credentials;
import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.exception.CasdoorException;
import org.casbin.casdoor.util.MapUtils;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.casbin.casdoor.util.http.HttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public abstract class CasdoorService {
    protected final ObjectMapper objectMapper = new ObjectMapper(){{
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }};

    protected final CasdoorConfig casdoorConfig;
    protected final String credential;
    protected CasdoorService(CasdoorConfig casdoorConfig) {
        this.casdoorConfig = casdoorConfig;
        this.credential = Credentials.basic(casdoorConfig.getClientId(), casdoorConfig.getClientSecret());
    }

    protected <T> CasdoorResponse<T> doGet(@NotNull String action, @Nullable Map<String, String> queryParams, TypeReference<CasdoorResponse<T>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", casdoorConfig.getEndpoint(), action, MapUtils.mapToUrlParams(queryParams));
        String response = HttpClient.syncGet(url, credential);
        CasdoorResponse<T> resp = objectMapper.readValue(response, typeReference);
        if (!Objects.equals(resp.getStatus(), "ok")) {
            throw new CasdoorException(String.format("Failed fetching %s : %s", url, resp.getMsg()));
        }

        return resp;
    }

    protected <T> CasdoorResponse<T> doPost(@NotNull String action, @Nullable Map<String, String> queryParams, Map<String, String> postForm, TypeReference<CasdoorResponse<T>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", casdoorConfig.getEndpoint(), action, MapUtils.mapToUrlParams(queryParams));
        String response = HttpClient.postForm(url, postForm, credential);
        CasdoorResponse<T> resp = objectMapper.readValue(response, typeReference);
        if (!Objects.equals(resp.getStatus(), "ok")) {
            throw new CasdoorException(String.format("Failed fetching %s : %s", url, resp.getMsg()));
        }

        return resp;
    }

    protected <T> CasdoorResponse<T> doPost(@NotNull String action, @Nullable Map<String, String> queryParams, String postString, TypeReference<CasdoorResponse<T>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", casdoorConfig.getEndpoint(), action, MapUtils.mapToUrlParams(queryParams));
        String response = HttpClient.postString(url, postString, credential);
        CasdoorResponse<T> resp = objectMapper.readValue(response, typeReference);
        if (!Objects.equals(resp.getStatus(), "ok")) {
            throw new CasdoorException(String.format("Failed fetching %s : %s", url, resp.getMsg()));
        }

        return resp;
    }

    protected <T> CasdoorResponse<T> doPost(String action, @Nullable Map<String, String> queryParams, File postFile, TypeReference<CasdoorResponse<T>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", casdoorConfig.getEndpoint(), action, MapUtils.mapToUrlParams(queryParams));
        String response = HttpClient.postFile(url, postFile, credential);
        CasdoorResponse<T> resp = objectMapper.readValue(response, typeReference);
        if (!Objects.equals(resp.getStatus(), "ok")) {
            throw new CasdoorException(String.format("Failed fetching %s : %s", url, resp.getMsg()));
        }

        return resp;
    }
}
