package com.ggh.easy.ui.screen;

import android.app.Activity;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.apkfuns.logutils.LogUtils;
import com.ggh.easy.base.BasePresenter;
import com.ggh.easy.decode.AndroidHradwareDecode;
import com.ggh.easy.net.udp.UDPSender;
import com.ggh.easy.service.RecordService;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MEDIA_PROJECTION_SERVICE;

public class ScreenPresenter extends BasePresenter<ScreenContract.MainView> implements ScreenContract.MainPresenter {
    private static final int RECORD_REQUEST_CODE = 101;

    /**
     * 系统录频管理者
     */
    private MediaProjectionManager projectionManager;
    /**
     * 从录屏管理者中获取实例当前请求实例
     */
    private MediaProjection mediaProjection;
    private Activity mContext;
    private UDPSender sender;
    private SurfaceHolder mHolder;

    private AndroidHradwareDecode decode;

    public ScreenPresenter(Activity mContext) {
        this.mContext = mContext;
        sender = new UDPSender();
    }



    @Override
    public void init(SurfaceHolder surfaceHolder) {
        mHolder = surfaceHolder;
        projectionManager = (MediaProjectionManager) mContext.getSystemService(MEDIA_PROJECTION_SERVICE);
        Intent captureIntent = projectionManager.createScreenCaptureIntent();
        mContext.startActivityForResult(captureIntent, RECORD_REQUEST_CODE);
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        RecordService.newInstance(mContext, metrics.widthPixels, metrics.heightPixels, metrics.densityDpi);

        decode = new AndroidHradwareDecode(mHolder,metrics.widthPixels, metrics.heightPixels);
    }

    @Override
    public void startOrStop() {
        RecordService.getRecordService().startOrStop();
    }


    @Override
    public void destory() {
        RecordService.getRecordService().onDestroy();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECORD_REQUEST_CODE && resultCode == RESULT_OK) {
            mediaProjection = projectionManager.getMediaProjection(resultCode, data);
            if (RecordService.getRecordService() != null) {
                RecordService.getRecordService().initMediaProject(mediaProjection,mHolder);
                RecordService.getRecordService().setRecordScreenCallback(new RecordService.RecordScreenCallback() {
                    @Override
                    public void statusCallback(int status) {
                        getView().screenRecordStatus(status);
                    }

                    @Override
                    public void screenData(byte[] data) {
                        LogUtils.d("回掉数据"+data);
                        decode.onDecodeData(data);
//                        sender.addData(data);
                    }
                });
                getView().screenRecordStatus(ScreenContract.PREPAR);
            }


        }
    }
}
