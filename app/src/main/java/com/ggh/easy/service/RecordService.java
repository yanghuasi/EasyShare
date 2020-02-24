package com.ggh.easy.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.os.Environment;
import android.os.IBinder;
import android.view.SurfaceHolder;

import com.apkfuns.logutils.LogUtils;
import com.ggh.easy.constant.Constants;
import com.ggh.easy.ui.screen.ScreenContract;

import java.io.File;
import java.io.IOException;


public class RecordService extends Service {
    private static RecordService recordService;

    public static RecordService getRecordService() {
        return recordService;
    }

    private MediaProjection mMediaProjection;
    private RecordScreenCallback callback;
    private ScreenRecorder mRecorder;
    private SurfaceHolder holder;

    private int width = 720;
    private int height = 1080;
    private int dpi;


    public static void newInstance(Context context, int width, int height, int dpi) {
        Intent intent = new Intent(context, RecordService.class);
        intent.putExtra("width", width);
        intent.putExtra("height", height);
        intent.putExtra("dpi", dpi);
        context.startService(intent);

    }

    public void setRecordScreenCallback(RecordScreenCallback callback) {
        this.callback = callback;
    }

    public void initMediaProject(MediaProjection mMediaProjection,SurfaceHolder surfaceHolder) {
        this.mMediaProjection = mMediaProjection;
        this.holder = surfaceHolder;
        initRecord();
    }

    private void initRecord() {
        File file = new File(Constants.CHACHE_VIDEO
                + System.currentTimeMillis() + width + "x" + height
                + ".mp4");
        File dirs = new File(file.getParent());
        if (!dirs.exists())
            dirs.mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        final int bitrate = 2000000;
        mRecorder = new ScreenRecorder(width, height, bitrate, dpi, mMediaProjection, file.getAbsolutePath(),holder);
        mRecorder.setScreenDataCallback(new ScreenDataCallback() {
            @Override
            public void frameCallback(byte[] data) {
                if (callback!=null){
                    callback.screenData(data);
                }
            }
        });
        mRecorder.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        width = intent.getIntExtra("width", 720);
        height = intent.getIntExtra("height", 1080);
        dpi = intent.getIntExtra("dpi", 0);
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        recordService = this;
        LogUtils.d("开启服务");

    }


    public void startOrStop() {
        if (mRecorder != null) {
            mRecorder.quit();
            mRecorder = null;
        } else {
            initRecord();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (callback != null) {
            callback.statusCallback(ScreenContract.DESTORY);
        }
        if (mRecorder != null) {
            mRecorder.quit();
            mRecorder = null;
        }
        stopSelf();
    }





    public interface RecordScreenCallback {
        void statusCallback(int status);

        void screenData(byte[] data);
    }


}