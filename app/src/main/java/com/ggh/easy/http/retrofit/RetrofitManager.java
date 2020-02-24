package com.ggh.easy.http.retrofit;

import com.ggh.easy.http.okhttp.OkHttpHelper;
import com.ggh.easy.http.retrofit.converter.string.StringConverterFactory;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private Retrofit retrofit;

    private String mBaseUrl;

    private RetrofitManager() {
    }

    private static RetrofitManager mRetrofitManager;

    public synchronized static RetrofitManager getInstance() {
        if (mRetrofitManager == null)
            mRetrofitManager = new RetrofitManager();
        return mRetrofitManager;
    }

    public RetrofitManager setBaseUrl(String baseUrl) {
        this.mBaseUrl = baseUrl;
        return this;
    }

    public RetrofitManager setHeaders(Map<String, String> headers) {
        OkHttpHelper.setHeaders(headers);
        return this;
    }

    public void init() {
        retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(OkHttpHelper.getClient())
                .addConverterFactory(StringConverterFactory.create()) //String 转换
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .validateEagerly(true)
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}
