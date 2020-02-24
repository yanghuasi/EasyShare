package com.ggh.easy.base;

import io.reactivex.ObservableTransformer;

/**
 * Description:
 * Data：3/27/2018-9:19 AM
 *
 * @author: yanzhiwen
 */
public interface BaseView {

    /**
     * 绑定生命周期
     * @param <T>
     * @return
     */
    <T> ObservableTransformer<T, T> bindLifecycle();
}
