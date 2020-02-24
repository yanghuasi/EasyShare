package com.ggh.easy.constant;

import android.os.Environment;

/**
 * 常量
 */
public class Constants {

    public static String BASE_URL = "https://www.baidu.com";
    //是否是IP地址
    public static String CHECK_IP = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

    public static String CHACHE_BASE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "AScreenRecord" + "/";

    public static String CHACHE_VIDEO = CHACHE_BASE + "video/";

    public static String CHACHE_IMG = CHACHE_BASE+"img/";


}
