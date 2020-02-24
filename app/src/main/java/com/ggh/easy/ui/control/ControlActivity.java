package com.ggh.easy.ui.control;

import android.content.Context;
import android.content.Intent;

import com.ggh.easy.R;
import com.ggh.easy.base.BaseActivity;
import com.ggh.easy.base.BasePresenter;
import com.ggh.easy.service.UdpReceiverService;

public class ControlActivity extends BaseActivity {

    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, ControlActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_control;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Intent intent = new Intent(this, UdpReceiverService.class);
        startService(intent);
    }
}
