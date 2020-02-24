package com.ggh.easy.http.rxjava.observer;

import android.support.annotation.CallSuper;
import android.util.Log;
import android.widget.Toast;

import com.ggh.easy.constant.AppDataManager;
import com.ggh.easy.http.result.HttpResponseException;
import com.ggh.easy.utils.ActivityLaunchUtils;
import com.ggh.easy.utils.Utils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * <p>Description:
 * <p>
 * <p>Created by Devin Sun on 2017/3/29.
 */

public abstract class BaseObserver<T> implements Observer<T> {


    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {

        Log.e("onError： ", "" + e.getLocalizedMessage());
        HttpResponseException responseException;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            responseException = new HttpResponseException("网络请求出错", httpException.code());
        } else if (e instanceof HttpResponseException) {
            responseException = (HttpResponseException) e;
        } else {//其他或者没网会走这里
            responseException = new HttpResponseException("网络异常,请稍后重试", -1024);
        }

        onFailed(responseException);
    }

    @Override
    public void onComplete() {
    }

    protected abstract void onSuccess(T t);

    @CallSuper
    protected void onFailed(HttpResponseException responseException) {
//         Toast.makeText(responseException.getMessage() + "(" + responseException.getStatus() + ")").show();
        if (responseException.getStatus() == 100) {
            Toast.makeText(Utils.getApp(),responseException.getMessage() + "(" + responseException.getStatus() + ")", Toast.LENGTH_SHORT).show();
//            AppDataManager.getInstance().setLogin(false);
//            ActivityLaunchUtils.startActivity(Utils.getApp(), LoginActivity.class);
        }
    }
}
