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
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

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
        this.credential = config.accessToken != null && !config.accessToken.isEmpty()
                ? "Bearer " + config.accessToken
                : Credentials.basic(config.clientId, config.clientSecret);
    }

    /**
     * Returns name as is if it's already an "owner/name" ID, otherwise prefixes it with the organization of the config.
     * @param name the name or the "owner/name" ID of an object
     * @return the "owner/name" ID
     */
    public String getId(String name) {
        return getId(name, config.organizationName);
    }

    protected static String getId(String name, String defaultOwner) {
        if (name != null && name.contains("/")) {
            return name;
        }
        return defaultOwner + "/" + name;
    }

    /**
     * getId for the object types that are owned by "admin" instead of an organization.
     */
    protected static String getAdminId(String name) {
        return getId(name, "admin");
    }

    /**
     * Keeps the caller-provided owner and only falls back to defaultOwner when it's empty.
     */
    protected static String getOwner(String owner, String defaultOwner) {
        return owner == null || owner.isEmpty() ? defaultOwner : owner;
    }

    protected static String joinColumns(String... columns) {
        return columns == null || columns.length == 0 ? null : String.join(",", columns);
    }

    protected java.util.Map<String, String> headers() {
        java.util.HashMap<String, String> headers = new java.util.HashMap<>();
        if (config.customHeaders != null) {
            headers.putAll(config.customHeaders);
        }
        headers.put("Authorization", credential);
        return headers;
    }

    private <T1, T2> CasdoorResponse<T1, T2> parse(String url, String response, TypeReference<CasdoorResponse<T1, T2>> typeReference) throws IOException {
        CasdoorResponse<T1, T2> resp = objectMapper.readValue(response, typeReference);
        if (!Objects.equals(resp.getStatus(), "ok")) {
            throw new Exception(String.format("Failed fetching %s : %s", url, resp.getMsg()));
        }
        return resp;
    }

    protected <T1, T2> CasdoorResponse<T1, T2> doPostMultipartForm(@Nonnull String action, @Nullable java.util.Map<String, String> queryParams, java.util.Map<String, String> form, TypeReference<CasdoorResponse<T1, T2>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", config.endpoint, action, Map.mapToUrlParams(queryParams));
        return parse(url, HttpClient.postMultipartForm(url, form, headers()), typeReference);
    }

    protected <T1, T2> CasdoorResponse<T1, T2> doPostBytes(@Nonnull String action, @Nullable java.util.Map<String, String> queryParams, String fileName, byte[] fileBytes, TypeReference<CasdoorResponse<T1, T2>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", config.endpoint, action, Map.mapToUrlParams(queryParams));
        return parse(url, HttpClient.postFile(url, fileName, fileBytes, headers()), typeReference);
    }

    protected <T1, T2> CasdoorResponse<T1, T2> doGet(@Nonnull String action, @Nullable java.util.Map<String, String> queryParams, TypeReference<CasdoorResponse<T1, T2>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", config.endpoint, action, Map.mapToUrlParams(queryParams));
        return parse(url, HttpClient.get(url, headers()), typeReference);
    }

    protected <T1, T2> CasdoorResponse<T1, T2> doPost(@Nonnull String action, @Nullable java.util.Map<String, String> queryParams, java.util.Map<String, String> postForm, TypeReference<CasdoorResponse<T1, T2>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", config.endpoint, action, Map.mapToUrlParams(queryParams));
        return parse(url, HttpClient.postForm(url, postForm, headers(), false), typeReference);
    }

    protected <T1, T2> CasdoorResponse<T1, T2> doPost(@Nonnull String action, @Nullable java.util.Map<String, String> queryParams, String postString, TypeReference<CasdoorResponse<T1, T2>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", config.endpoint, action, Map.mapToUrlParams(queryParams));
        return parse(url, HttpClient.postString(url, postString, headers()), typeReference);
    }

    protected <T1, T2> CasdoorResponse<T1, T2> doPost(String action, @Nullable java.util.Map<String, String> queryParams, File postFile, TypeReference<CasdoorResponse<T1, T2>> typeReference) throws IOException {
        String url = String.format("%s/api/%s?%s", config.endpoint, action, Map.mapToUrlParams(queryParams));
        return parse(url, HttpClient.postFile(url, postFile.getName(), java.nio.file.Files.readAllBytes(postFile.toPath()), headers()), typeReference);
    }
}
