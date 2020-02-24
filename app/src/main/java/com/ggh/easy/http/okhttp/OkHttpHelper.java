package com.ggh.easy.http.okhttp;

import com.ggh.easy.http.okhttp.interceptor.HeaderInterceptor;
import com.ggh.easy.http.okhttp.interceptor.LoggingInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * <p>Description:
 * <p>
 * <p>Created by Devin Sun on 2017/3/24.
 */

public class OkHttpHelper {

    private static OkHttpClient okHttpClient;

    /**
     * 连接超时
     */
    private static final int CONNECT_TIMEOUT = 10;
    /**
     * 读取超时
     */
    private static final int READ_TIMEOUT = 25;
    /**
     * 写入超时
     */
    private static final int WRITE_TIMEOUT = 25;
    private static Map<String, String> mHeaders;
    private static String mToken = "";


    private OkHttpHelper() {
    }

    public static void setHeaders(Map<String, String> headers) {
        mHeaders = headers;
    }

    public static void setToken(String token) {
        mToken = token;
        mHeaders.put("token", token);
    }

    public static String getToken() {
        return mToken;
    }

    public static void init(){
            // CustomHttpsTrust customHttpsTrust = new CustomHttpsTrust(CertificateManager.trustedCertificatesInputStream());
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(new HeaderInterceptor(mHeaders)) //添加 header
                    .addInterceptor(new LoggingInterceptor())  //请求信息的打印 ，可在 release 时关闭
//                    .addInterceptor(new TokenInterceptor())  //添加ToKen信息
                    // .sslSocketFactory(customHttpsTrust.sSLSocketFactory, customHttpsTrust.x509TrustManager)// https 配置
                    .build();

    }

    public static OkHttpClient getClient() {
        if (okHttpClient == null)
            init();
        return okHttpClient;
    }

}
