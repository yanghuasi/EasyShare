package com.ggh.easy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.List;

public class ShareUtil {
    public static String Cache = "selpro_util";

    public static void setCacheSpace(String cache) {
        Cache = cache;
    }

    public static boolean getBoolean(Context context, boolean defval,
                                     String key) {
        boolean date = defval;
        SharedPreferences share = context.getSharedPreferences(Cache, Context.MODE_PRIVATE);
        date = share.getBoolean(key, defval);
        return date;
    }

    public static int getInt(Context context, int defval,
                             String key) {
        int date = defval;
        SharedPreferences share = context.getSharedPreferences(Cache, Context.MODE_PRIVATE);
        date = share.getInt(key, defval);
        return date;
    }

    public static float getFloat(Context context, float defval,
                                 String key) {
        float date = defval;
        SharedPreferences share = context.getSharedPreferences(Cache, Context.MODE_PRIVATE);
        date = share.getFloat(key, defval);
        return date;
    }

    public static void saveBoolean(Context context, boolean defval,
                                   String key) {
        SharedPreferences share = context.getSharedPreferences(Cache, Context.MODE_PRIVATE);
        Editor edit = share.edit();
        edit.putBoolean(key, defval);
        edit.commit();
    }

    public static void saveFloat(Context context, float defval,
                                 String key) {
        SharedPreferences share = context.getSharedPreferences(Cache, Context.MODE_PRIVATE);
        Editor edit = share.edit();
        edit.putFloat(key, defval);
        edit.commit();
    }

    public static void saveLong(Context context, long defval,
                                String key) {
        SharedPreferences share = context.getSharedPreferences(Cache, Context.MODE_PRIVATE);
        Editor edit = share.edit();
        edit.putLong(key, defval);
        edit.commit();
    }

    public static long getLong(Context context, long defval,
                               String key) {
        long date = defval;
        SharedPreferences share = context.getSharedPreferences(Cache, Context.MODE_PRIVATE);
        date = share.getLong(key, defval);
        return date;
    }

    public static void saveInt(Context context, int defval,
                               String key) {
        SharedPreferences share = context.getSharedPreferences(Cache, Context.MODE_PRIVATE);
        Editor edit = share.edit();
        edit.putInt(key, defval);
        edit.commit();
    }

    public static String getString(Context context, String defval,
                                   String key) {
        String date = null;
        SharedPreferences share = context.getSharedPreferences(Cache, Context.MODE_PRIVATE);
        date = share.getString(key, defval);
        return date;
    }

    public static void saveString(Context context, String defval, String key) {

        SharedPreferences share = context.getSharedPreferences(Cache, Context.MODE_PRIVATE);
        Editor edit = share.edit();
        edit.putString(key, defval);
        edit.commit();
    }

    public static void savePhoneNum(Context context, String phone, String key) {

        SharedPreferences share = context.getSharedPreferences(Cache, Context.MODE_PRIVATE);
        Editor edit = share.edit();
        edit.putString(key, phone);
        edit.commit();
    }

    public static String getPhoneNum(Context context, String phone,
                                     String key) {
        String date = null;
        SharedPreferences share = context.getSharedPreferences(Cache, Context.MODE_PRIVATE);
        date = share.getString(key, phone);
        return date;
    }

    public static void saveList(Context context, List<String> phone, String key) {
        for (int i = 0; i < phone.size(); i++) {
            saveString(context, phone.get(i), key + i);
        }
    }

    public static List<String> getList(Context context, String phone, String key) {
        List<String> list = new ArrayList<>();
        int i = 0;
        while (getString(context, phone, key + i) != null && getString(context, phone, key + i) != phone) {
            list.add(getString(context, phone, key + i));
            i++;
        }
        return list;
    }
}
