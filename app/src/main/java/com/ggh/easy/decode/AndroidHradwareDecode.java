package com.ggh.easy.decode;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.util.Log;
import android.view.SurfaceHolder;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by ZQZN on 2017/9/14.
 */

public class AndroidHradwareDecode {
    private long mPresentTimeUs;
    private MediaCodec vDeCodec = null;
    MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();

    private boolean isStartDecode = false;

    public AndroidHradwareDecode(SurfaceHolder holder,int width , int heigth) {
        initVideoEncode(holder,width, heigth);
    }

    public boolean initVideoEncode(SurfaceHolder holder,int width , int heigth) {
        MediaFormat format = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, width, heigth);
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT,
                MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        format.setInteger(MediaFormat.KEY_FRAME_RATE, 30);
        //IFRAME_INTERVAL是指的帧间隔，这是个很有意思的值，它指的是，关键帧的间隔时间。通常情况下，你设置成多少问题都不大。
        //比如你设置成10，那就是10秒一个关键帧。但是，如果你有需求要做视频的预览，那你最好设置成1
        //因为如果你设置成10，那你会发现，10秒内的预览都是一个截图
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1);
        try {
            // Get an instance of MediaCodec and give it its Mime type
            vDeCodec = MediaCodec.createDecoderByType(MediaFormat.MIMETYPE_VIDEO_AVC);
            // Configure the codec
            vDeCodec.configure(format, holder.getSurface(), null, 0);
            // Start the codec
            vDeCodec.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void decode(byte[] buf) {
        ByteBuffer[] inputBuffers = vDeCodec.getInputBuffers();
        ByteBuffer[] outputBuffers = vDeCodec.getOutputBuffers();
        int inputBufferIndex = vDeCodec.dequeueInputBuffer(-1);
        if (inputBufferIndex >= 0) {
            ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
            inputBuffer.clear();
            inputBuffer.put(buf, 0, buf.length);
            vDeCodec.queueInputBuffer(inputBufferIndex, 0, buf.length, 0, 0);
        }

        // TO DO
    }

    public void onDecodeData(byte[] codeData) {
        Log.e("ggh1", "解码前");
        ByteBuffer[] inputBuffer = vDeCodec.getInputBuffers();
        int inputIndex = vDeCodec.dequeueInputBuffer(0);

        if (inputIndex >= 0) {
            ByteBuffer buffer = inputBuffer[inputIndex];

            try {
                buffer.put(codeData);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            vDeCodec.queueInputBuffer(inputIndex, 0, codeData.length, 0, 0);
        }

        int outputIndex = vDeCodec.dequeueOutputBuffer(info, 0);
        if (outputIndex >= 0) {
            vDeCodec.releaseOutputBuffer(outputIndex, true);
            Log.e("ggh1", "解码后");
        }

    }



    public Long getKey(Map<Long, byte[]> data) {
        Set<Long> setKey = data.keySet();
        Iterator<Long> iterator = setKey.iterator();
        // 从while循环中读取key
        while (iterator.hasNext()) {
            long key = iterator.next();
            // 此时的String类型的key就是我们需要的获取的值
            return key;
        }
        return 0L;

    }


}
