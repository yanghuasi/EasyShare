package com.ggh.easy.net.udp;

import android.util.Log;


import com.ggh.easy.App;
import com.ggh.easy.net.NetConfig;
import com.ggh.easy.net.Receiver;
import com.ggh.easy.net.ReceiverCallback;
import com.ggh.easy.utils.ShareKey;
import com.ggh.easy.utils.ShareUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by ZQZN on 2017/12/12.
 */

public class UDPReceiver extends Receiver {
    private boolean isReceiver = false;

    private ReceiverCallback callback;

    public void setCallback(ReceiverCallback callback) {
        this.callback = callback;
    }


    public UDPReceiver() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                receiver();
            }
        }).start();
    }

    public void receiver(){
        DatagramChannel datagramChannel = null;
        try {
            datagramChannel = DatagramChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            int port = ShareUtil.getInt(App.getInstance().getApplicationContext(),1234, ShareKey.KEY_PORT);
            datagramChannel.socket().bind(new InetSocketAddress(port));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteBuffer buffer = ByteBuffer.allocate(60000);
        byte b[];
        while(isReceiver) {
            buffer.clear();
            SocketAddress socketAddress = null;
            try {
                socketAddress = datagramChannel.receive(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (socketAddress != null) {
                int position = buffer.position();
                b = new byte[position];
                buffer.flip();
                for(int i=0; i<position; ++i) {
                    b[i] = buffer.get();
                }
                Log.d("ggh","接收到  "+b.length);
                if (callback!=null){
                    callback.callback(b);

                }
            }
        }
    }



    @Override
    public void startRecivice() {
        isReceiver = true;
    }

    @Override
    public void stopRecivice() {
        isReceiver = false;
    }
}
