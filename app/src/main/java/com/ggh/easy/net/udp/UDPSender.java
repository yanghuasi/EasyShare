package com.ggh.easy.net.udp;

import android.util.Log;


import com.ggh.easy.App;
import com.ggh.easy.net.NetConfig;
import com.ggh.easy.net.Send;
import com.ggh.easy.utils.ShareKey;
import com.ggh.easy.utils.ShareUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by ZQZN on 2017/12/12.
 */

public class UDPSender extends Send {

    private boolean isStratSendData = true;

    private DatagramChannel channel;

    public UDPSender() {

        try {
            channel = DatagramChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addData(final byte[] frame) {
        if (isStratSendData) {
            send(frame);
        }
    }

    @Override
    public void startSender() {
        isStratSendData = true;

    }

    @Override
    public void stopSender() {
        isStratSendData = false;
    }

    @Override
    public void destroy() {
        if (channel != null) {
            try {
                channel.close();
                channel = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void send(final byte[] data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    sendMessage(channel, data);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public static void sendMessage(DatagramChannel channel, byte[] mes) throws IOException {
        if (mes == null || mes.length < 0) {
            return;
        }
        ByteBuffer buffer = ByteBuffer.allocate(600000);
        buffer.clear();
        buffer.put(mes);
        buffer.flip();
        String ip = ShareUtil.getString(App.getInstance().getApplicationContext(), "10.0.0.1", ShareKey.KEY_IP);
        int port = ShareUtil.getInt(App.getInstance().getApplicationContext(), 1234, ShareKey.KEY_PORT);
        int send = channel.send(buffer, new InetSocketAddress(ip, port));
        Log.d("ggh", "发送 " + send + " 字节");
    }
}
