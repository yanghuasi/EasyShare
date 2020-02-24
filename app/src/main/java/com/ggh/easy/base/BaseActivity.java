package com.ggh.easy.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggh.easy.utils.LanguageUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.ObservableTransformer;

/**
 * Description:
 * Data：3/27/2018-9:20 AM
 *
 * @author: yanzhiwen
 */
public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity implements BaseView {
    private P mPresenter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        //创建Presenter,交给子类去实现
        mPresenter = createPresenter();
        //让P层去绑定V
        if (mPresenter != null)
            mPresenter.attach(this);
        initView();
        initData();

    }

    /**
     * 返回Activity的布局Id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 创建Presenter
     *
     * @return
     */
    protected abstract P createPresenter();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 在这里去请求数据
     */
    protected abstract void initData();

    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
//        return this.bindUntilEvent(ActivityEvent.PAUSE);//绑定到Activity的pause生命周期（在pause销毁请求）
        return this.bindToLifecycle();//绑定activity，与activity生命周期一样
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageUtils.attachBaseContext(base, LanguageUtils.getAppLanguage(base)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detach();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    /**
     * 初始化 Toolbar
     *
     * @param toolbar
     */
    protected void initToolBar(Toolbar toolbar) {
        initToolBar(toolbar, false, false, "");
    }

    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, boolean showTitleEnabled, int resTitle) {
        initToolBar(toolbar, homeAsUpEnabled, showTitleEnabled, resTitle != 0 ? getString(resTitle) : "");
    }

    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, boolean showTitleEnabled, String title) {
        if (!TextUtils.isEmpty(title))
            toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
        getSupportActionBar().setDisplayShowTitleEnabled(showTitleEnabled);
    }

    protected void setToolBarBack(@IdRes int id, @DrawableRes int resId) {
        ImageView toolbarBack = findViewById(id);
        toolbarBack.setImageResource(resId);
        setVisibility(toolbarBack, true);
    }

    protected void setToolBarRight(@IdRes int id, @StringRes int resId) {
        TextView toolbarRight = findViewById(id);
        toolbarRight.setText(resId);
        setVisibility(toolbarRight, true);
    }

    protected void setToolBarRightText(@IdRes int id, String right) {
        TextView toolbarRight = findViewById(id);
        toolbarRight.setText(right);
        setVisibility(toolbarRight, true);
    }
    protected void setToolBarRightText(@IdRes int id, int stringId) {
        TextView toolbarRight = findViewById(id);
        toolbarRight.setText(stringId);
        setVisibility(toolbarRight, true);
    }


    protected void setToolBarRightButton(@IdRes int id, @DrawableRes int resId) {
        ImageView toolbarRightBtn = findViewById(id);
        toolbarRightBtn.setImageResource(resId);
        setVisibility(toolbarRightBtn, true);
    }

    protected void setVisibility(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}
