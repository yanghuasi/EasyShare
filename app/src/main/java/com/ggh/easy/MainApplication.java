package com.ggh.easy;

import android.content.Context;
import android.content.res.Configuration;

import com.ggh.easy.constant.Constants;
import com.ggh.easy.http.retrofit.RetrofitManager;
import com.ggh.easy.utils.AppUtils;
import com.ggh.easy.utils.DeviceUtils;
import com.ggh.easy.utils.LanguageUtils;
import com.ggh.easy.utils.Utils;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.HashMap;


public class MainApplication extends App {



    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context)/*.setTimeFormat(new DynamicTimeFormat("更新于 %s"))*/;
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageUtils.attachBaseContext(base, LanguageUtils.getAppLanguage(base)));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        Hawk.init(this).build();
        initRetrofitManager();
        LanguageUtils.setChangeAppLanguage(true);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LanguageUtils.onLanguageChange(this);
    }



    /**
     * 初始化Retrofit，并设置Header
     */
    private void initRetrofitManager() {
        HashMap map = new HashMap<String, String>();
        map.put("os_type", "android");
        map.put("os_version", DeviceUtils.getSDKVersionName());
        map.put("app_version", AppUtils.getAppVersionCode(this) + "");
        map.put("_local", "zh_CN");//所有接口请求的header中传递 _local 字段作为国际化标识
        map.put("abroad", "0");//接口中， 如果是海外版需在header中传递 abroad=1 标识
        //map.put("token", "android");//TokenInterceptor内设置，除了注册和登录接口，其余所有的接口都需要在header中传递token

//      在此处初始化Retrofit
        RetrofitManager
                .getInstance()
                .setBaseUrl(Constants.BASE_URL)   //设置baseUrl
                .setHeaders(map)
                .init();
    }



}
