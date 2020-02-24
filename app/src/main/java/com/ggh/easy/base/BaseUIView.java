package com.ggh.easy.base;

public interface BaseUIView<T> extends BaseView {
    void onSuccess(T user);

    void onError(String error);
}
