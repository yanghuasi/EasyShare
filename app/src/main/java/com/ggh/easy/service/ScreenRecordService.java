package com.ggh.easy.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.media.projection.MediaProjection;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import com.ggh.easy.constant.Constants;

/**
 * 录屏直播
 * 包括音频 通过ImageReader获取视频帧
 *
 * @author lichen2
 */
@SuppressLint("SdCardPath")
public class ScreenRecordService extends Thread {
    private static final String TAG = "ScreenAudioRecordService";

    private int mWidth;
    private int mHeight;
    private String rtmpUrl;
    private int mDpi;
    private boolean ifSave = false;
    private String mPath; // 文件保存路径
    private String tmpPath = "/sdcard/csspublisher/tmp";  //文件暂存路径
    private MediaProjection mMediaProjection;
    private Context mContext;

    private VirtualDisplay mVirtualDisplay;
    private ImageReader mReader;


    public ScreenRecordService(int width, int height, String url, int dpi, MediaProjection mp,
                               Context context) {
        mWidth = width;
        mHeight = height;
        rtmpUrl = url;
        mDpi = dpi;
        mContext = context;
        mMediaProjection = mp;
        if (width > 800) {
            mWidth /= 2;
            mHeight /= 2;
        }
    }

    public ScreenRecordService(int width, int height, String url, int dpi, MediaProjection mp,
                               Context context, String filePath) {
        mWidth = width;
        mHeight = height;
        rtmpUrl = url;
        mDpi = dpi;
        mContext = context;
        mMediaProjection = mp;
        mPath = filePath;
        if (width > 800) {
            mWidth /= 2;
            mHeight /= 2;
        }
        ifSave = true;
    }

    @Override
    public void run() {
        try {
            mReader = ImageReader.newInstance(mWidth, mHeight, 0x1, 2);
            mVirtualDisplay = mMediaProjection.createVirtualDisplay(TAG + "-dispaly", mWidth, mHeight, mDpi,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mReader.getSurface(), null, null);
            Looper.prepare();
            mReader.setOnImageAvailableListener(new OnImageAvailableListener() {

                @Override
                public void onImageAvailable(ImageReader reader) {
                    Image image = reader.acquireNextImage() ;

                    ByteBuffer byteBuffer = image.getPlanes()[0].getBuffer();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);

                    try {
                        Bitmap temp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        Bitmap newBitmap = Bitmap.createBitmap(mWidth,mHeight,temp.getConfig());

                        Canvas canvas = new Canvas(newBitmap);
                        Paint paint = new Paint();
                        Matrix matrix = new Matrix();
                        //图片镜像并旋转90度
                        matrix.setScale(-1, 1);
                        matrix.postTranslate(temp.getWidth(), 0);
                        matrix.postRotate(90 ,temp.getWidth()/2,temp.getHeight()/2);
                        matrix.postTranslate(0,(temp.getWidth()-temp.getHeight())/2);

                        canvas.drawBitmap(temp, matrix , paint );

                        File newFile = new File(Constants.CHACHE_IMG+System.currentTimeMillis()+".jpg");
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));
                        newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                        bos.flush();
                        bos.close();
                        temp.recycle();
                        newBitmap.recycle();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        image.close();
                        byteBuffer.clear();
                    }

                    image.close();


                }
            }, null);
            Looper.loop();
        } finally {
            release();
        }
    }


    /**
     * 释放资源
     */
    public void release() {
        if (mVirtualDisplay != null) {
            mVirtualDisplay.release();
            mVirtualDisplay = null;
        }
        if (mMediaProjection != null) {
            mMediaProjection.stop();
        }
    }

}
