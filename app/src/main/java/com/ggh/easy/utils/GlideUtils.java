package com.ggh.easy.utils;


import android.content.Context;
import android.widget.ImageView;

import java.io.File;

/**
 * Glide封装类
 * 添加注解提醒、返回类型、优化代码
 */
public class GlideUtils {

    /**
     * 正常加载图片
     *
     * @param context
     * @param iv       ImageView
     * @param url      图片地址
     */
    public static void displayImage(Context context, ImageView iv, String url) {
        GlideApp.with(context)
                .load(url)
                .into(iv);
    }

    /**
     * 正常加载图片
     *
     * @param context
     * @param iv       ImageView
     * @param url      图片地址
     * @param emptyImg 默认占位图
     */
    public static void displayImage(Context context, ImageView iv, String url, int emptyImg) {
        GlideApp.with(context)
                .load(url)
                .placeholder(emptyImg)
                .into(iv);
    }


    public static void displayLocal(Context context, ImageView iv, String url){
        File file = new File(url);
        GlideApp.with(context).load(file).into(iv);
    }

}