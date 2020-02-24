package com.ggh.easy.http;

import com.ggh.easy.http.okhttp.OkHttpHelper;
import com.ggh.easy.http.retrofit.RetrofitManager;
import com.ggh.easy.http.retrofit.converter.string.StringConverterFactory;
import com.ggh.easy.model.ApiService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>Description:
 * <p>
 * <p>Created by Devin Sun on 2017/3/29.
 */

public class HttpClient {

    public static ApiService getApiService() {
        return builder(ApiService.class);
    }

    /**
     * 获得对应的ApiServcie对象
     *
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T builder(Class<T> service) {
        return RetrofitManager.getInstance().getRetrofit().create(service);

    }

    /**
     * 获得对应的ApiServcie对象
     *
     * @param baseUrl
     * @return
     */
    public static <T> T builder(Class<T> service, String baseUrl) {
        if (baseUrl == null) {
            throw new RuntimeException("baseUrl is null!");
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpHelper.getClient())
                .addConverterFactory(StringConverterFactory.create()) //String 转换
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .validateEagerly(true)
                .build();
        return retrofit.create(service);

    }

}
