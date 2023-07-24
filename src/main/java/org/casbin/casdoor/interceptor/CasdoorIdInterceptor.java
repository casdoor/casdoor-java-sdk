package org.casbin.casdoor.interceptor;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.casbin.casdoor.annotation.CasdoorId;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Invocation;

public class CasdoorIdInterceptor implements Interceptor {

    private final String owner;

    public CasdoorIdInterceptor(String owner) {
        this.owner = owner;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Invocation invocation = original.tag(Invocation.class);

        OptionalInt idx = getCasdoorIdIndex(invocation);
        if (hasCasdoorId(idx)) {
            HttpUrl url = original.url().newBuilder()
                    .addQueryParameter("id", String.format("%s/%s", owner, invocation.arguments().get(idx.getAsInt())))
                    .build();
            return chain.proceed(original.newBuilder().url(url).build());
        }

        return chain.proceed(original);
    }

    private OptionalInt getCasdoorIdIndex(Invocation invocation) {
        Parameter[] params = invocation.method().getParameters();
        return IntStream.range(0, params.length).filter(i -> params[i].isAnnotationPresent(CasdoorId.class))
                .findFirst();
    }

    private boolean hasCasdoorId(OptionalInt idx) {
        return owner != null && owner.length() > 0 && idx.isPresent();
    }
}
