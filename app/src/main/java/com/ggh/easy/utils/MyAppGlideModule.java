package com.ggh.easy.utils;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    /**
     *  通过GlideBuilder设置默认的结构(Engine,BitmapPool ,ArrayPool,MemoryCache等等).
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //重新设置内存限制
        builder.setMemoryCache(new LruResourceCache(10*1024*1024));

    }

    /**
     * 为App注册一个自定义的GlideUrlLoader
     * @param context
     * @param registry
     */
//    @Override
//    public void registerComponents(Context context, Registry registry) {
//        registry.append(String.class, InputStream.class,new CustomBaseGlideUrlLoader.Factory());
//    }

    /**
     * 清单解析的开启
     *
     * 这里不开启，避免添加相同的modules两次
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
