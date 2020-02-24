package com.ggh.easy.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.apkfuns.logutils.LogUtils;
import com.ggh.easy.R;
import com.ggh.easy.base.BaseActivity;
import com.ggh.easy.base.BasePresenter;
import com.ggh.easy.constant.Constants;
import com.ggh.easy.ui.control.ControlActivity;
import com.ggh.easy.ui.screen.ScreenActivity;
import com.ggh.easy.utils.PermissionManager;
import com.ggh.easy.utils.ShareKey;
import com.ggh.easy.utils.ShareUtil;
import com.ggh.easy.utils.ToastUtils;
import com.yanzhenjie.permission.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends BaseActivity {
    @BindView(R.id.ed_ip)
    EditText edIp;
    @BindView(R.id.ed_port)
    EditText edPort;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        edIp.setText(ShareUtil.getString(this,"",ShareKey.KEY_IP));
        edPort.setText(ShareUtil.getInt(this,1234,ShareKey.KEY_PORT)+"");

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.sender, R.id.receiver})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sender:
                String ip = edIp.getText().toString();
                String port = edPort.getText().toString();
                if (ip.matches(Constants.CHECK_IP)) {
                    PermissionManager.requestPermission(TestActivity.this, new PermissionManager.Callback() {
                        @Override
                        public void permissionSuccess() {
                            ShareUtil.saveString(TestActivity.this, ip, ShareKey.KEY_IP);
                            ShareUtil.saveInt(TestActivity.this, Integer.valueOf(port), ShareKey.KEY_PORT);
                            ScreenActivity.newInstance(TestActivity.this);

                            LogUtils.d("读取本地权限获取成功");
                        }

                        @Override
                        public void permissionFailed() {

                        }
                    }, Permission.Group.STORAGE);

                } else {
                    ToastUtils.show("您输入的ip格式有误");
                }
                break;
            case R.id.receiver:
                ControlActivity.newInstance(this);
                break;
        }
    }
}
