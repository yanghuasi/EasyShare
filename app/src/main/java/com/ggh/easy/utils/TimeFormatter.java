package com.ggh.easy.utils;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;

/**
 * 转换时间显示样式
 *
 * @author yuansui
 */
public class TimeFormatter {

    /**
     * 时间格式定义, 规则:
     * from: 起始单位
     * to: 结束单位, 如没有定义, 则默认为到'秒'
     */
    @StringDef({
            TimeFormat.simple_ymd,
            TimeFormat.from_y_24,
            TimeFormat.from_y_to_m_24,
            TimeFormat.from_y_to_h_12,
            TimeFormat.from_y_to_h_24,
            TimeFormat.from_y_12,
            TimeFormat.from_h_12,
            TimeFormat.from_h_to_m_12,
            TimeFormat.from_h_to_m_24,
            TimeFormat.from_m,
            TimeFormat.form_MM_dd_24,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface TimeFormat {
        String simple_ymd = "yyyy-MM-dd"; // 2016-08-21
        String from_y_24 = "yyyy-MM-dd HH:mm:ss"; // 24h 2016-08-21 18:22:22
        String from_y_to_m_24 = "yyyy-MM-dd HH:mm"; // 24h
        String from_y_to_h_12 = "yyyy-MM-dd hh"; // 12h
        String from_y_to_h_24 = "yyyy-MM-dd HH"; // 24h
        String from_y_12 = "yyyy-MM-dd hh:mm:ss"; // 12h
        String from_h_12 = "hh:mm:ss";
        String from_h_24 = "HH:mm:ss";
        String from_h_to_m_12 = "hh:mm";
        String from_h_to_m_24 = "HH:mm";
        String from_m = "mm:ss";
        String only_MM = "MM";
        String only_dd = "dd";
        String only_ss = "ss";
        String form_MM_dd_24 = "MM月dd日 HH:mm"; // 06月12日 15:00
    }

    /**
     * 转换毫秒
     *
     * @param milli
     * @param format
     * @return
     */
    public static String milli(long milli, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(milli);
    }

    public static String milli(String milli, String format) {
        return milli(Long.parseLong(milli), format);
    }

    /**
     * 转换秒
     *
     * @param second
     * @param format
     * @return
     */
    public static String second(long second, String format) {
        return milli(second * 1000, format);
    }

    public static String second(String second, String format) {
        return second(Long.parseLong(second), format);
    }
}
