package com.ggh.easy.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.apkfuns.logutils.LogUtils;
import com.ggh.easy.constant.Constants;
import com.ggh.easy.net.ReceiverCallback;
import com.ggh.easy.net.udp.UDPReceiver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UdpReceiverService extends Service {
    private UDPReceiver receiver;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new UDPReceiver();
        receiver.setCallback(new ReceiverCallback() {
            @Override
            public void callback(byte[] data) {
                LogUtils.d("接受数据", "data");
//                bytesToImageFile(data);

            }
        });
        receiver.startRecivice();
    }

    private void bytesToImageFile(byte[] bytes) {
        try {
            File file = new File(Constants.CHACHE_IMG + System.currentTimeMillis() + ".jpg");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes, 0, bytes.length);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
