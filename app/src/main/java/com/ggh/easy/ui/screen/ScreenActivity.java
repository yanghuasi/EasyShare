package com.ggh.easy.ui.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Chronometer;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.ggh.easy.R;
import com.ggh.easy.base.BaseActivity;
import com.ggh.easy.utils.PermissionManager;
import com.yanzhenjie.permission.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScreenActivity extends BaseActivity<ScreenPresenter> implements ScreenContract.MainView {
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.surface)
    SurfaceView surface;
    @BindView(R.id.tv_second)
    Chronometer tvSecond;

    private SurfaceHolder mHoder;

    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, ScreenActivity.class));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected ScreenPresenter createPresenter() {
        return new ScreenPresenter(this);
    }

    @Override
    protected void initView() {
        initSurface(surface);
        tvSecond.setBase(SystemClock.elapsedRealtime());//计时器清零
        tvSecond.start();

    }

    @Override
    protected void initData() {

    }


    @Override
    public void screenRecordStatus(int status) {
        if (status == ScreenContract.PREPAR) {
        } else if (status == ScreenContract.STARD_RECORD) {
            tvRecord.setText("暂停录制");
        } else if (status == ScreenContract.STOP_RECORD) {
            tvRecord.setText("开始录制");

        } else if (status == ScreenContract.RECORDING) {
        } else if (status == ScreenContract.DESTORY) {
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getPresenter().onActivityResult(requestCode, resultCode, data);
    }


    @OnClick(R.id.tv_record)
    public void onViewClicked() {
        PermissionManager.requestPermission(ScreenActivity.this, new PermissionManager.Callback() {
            @Override
            public void permissionSuccess() {
                getPresenter().startOrStop();

                LogUtils.d("读取本地权限获取成功");
            }

            @Override
            public void permissionFailed() {

            }
        }, Permission.Group.STORAGE);

    }

    /**
     * 初始化预览界面
     *
     * @param mSurfaceView
     */
    private void initSurface(SurfaceView mSurfaceView) {
        mHoder = mSurfaceView.getHolder();
        mHoder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHoder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                getPresenter().init(surfaceHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//                destroy();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
