package com.ggh.easy.http.okhttp.interceptor;

import android.text.TextUtils;

import com.ggh.easy.http.okhttp.OkHttpHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    private static final String USER_TOKEN = "token";

    public TokenInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = OkHttpHelper.getToken();
        final Request originalRequest = chain.request();
        if (TextUtils.isEmpty(OkHttpHelper.getToken()) || originalRequest.header(USER_TOKEN) != null) {
            return chain.proceed(originalRequest);
        }
        Request request = originalRequest.newBuilder()
                .removeHeader(USER_TOKEN)
                .addHeader(USER_TOKEN, token)
                .build();
        return chain.proceed(request);
    }
}
