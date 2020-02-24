package com.ggh.easy.http.okhttp.interceptor;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>Description:
 * <p>Created by Devin Sun on 2017/3/29.
 */

public class HeaderInterceptor implements Interceptor {

    private Map<String, String> headers;

    public HeaderInterceptor() {
    }

    public HeaderInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

//        Request request = chain.request().newBuilder()
//                .addHeader("Content-Type", "application/json;charset=UTF-8")
//                // add header...
//                .build();

        Request.Builder builder = chain.request().newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey));
            }
        }
        builder.addHeader("Content-Type", "application/json;charset=UTF-8");

        return chain.proceed(builder.build());
    }
}
