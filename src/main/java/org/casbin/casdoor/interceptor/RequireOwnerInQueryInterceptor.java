package org.casbin.casdoor.interceptor;

import java.io.IOException;

import org.casbin.casdoor.annotation.RequireOwnerInQuery;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Invocation;

public class RequireOwnerInQueryInterceptor implements Interceptor {

    private final String owner;

    public RequireOwnerInQueryInterceptor(String owner) {
        this.owner = owner;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Invocation invocation = original.tag(Invocation.class);

        if (isOwnerRequired(invocation)) {
            HttpUrl url = original.url().newBuilder().addQueryParameter("owner", owner).build();
            return chain.proceed(original.newBuilder().url(url).build());
        }

        return chain.proceed(original);
    }

    private boolean isOwnerRequired(Invocation invocation) {
        return owner != null && owner.length() > 0
                && invocation.method().isAnnotationPresent(RequireOwnerInQuery.class);
    }
}
