package org.casbin.casdoor.interceptor;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {

    private final String clientId;
    private final String clientSecret;

    public AuthenticationInterceptor(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Authorization", Credentials.basic(clientId, clientSecret)).build();

        return chain.proceed(request);
    }

}
