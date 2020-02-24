package com.ggh.easy.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by ZQZN on 2017/12/12.
 */

public class NetConfig {

    public static final String REMOTEIP = "10.0.0.22";
    public static final int REMOTE_VIDEO_PORT = 19999;
    public static final int REMOTE_AUDIO_PORT = 19998;
    public static final int packSize = 1400;

    /**
     * 获取ip地址
     * @return
     * @throws UnknownHostException
     */
    public static InetAddress getIpAddress() throws UnknownHostException {
        return InetAddress.getByName(REMOTEIP);
    }
}
