package com.ggh.easy.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.Locale;

/**
 * @author YanLu
 * @since 17/5/12
 */

public class LanguageUtils {

    // 简体中文
    public static final String SIMPLIFIED_CHINESE = "zh";
    // 英文
    public static final String ENGLISH = "en";
    // 繁体中文
    public static final String TRADITIONAL_CHINESE = "zh-hant";
    // 法语
    public static final String FRANCE = "fr";
    // 德语
    public static final String GERMAN = "de";
    // 印地语
    public static final String HINDI = "hi";
    // 意大利语
    public static final String ITALIAN = "it";

    private static HashMap<String, Locale> mAllLanguages = new HashMap<String, Locale>(7) {{
        put(ENGLISH, Locale.ENGLISH);
        put(SIMPLIFIED_CHINESE, Locale.SIMPLIFIED_CHINESE);
        put(TRADITIONAL_CHINESE, Locale.TRADITIONAL_CHINESE);
//        put(FRANCE, Locale.FRANCE);
//        put(GERMAN, Locale.GERMANY);
//        put(HINDI, new Locale(ConstantLanguages.HINDI, "IN"));
//        put(ITALIAN, Locale.ITALY);
    }};
    private static boolean mIsChange = false;

    public static boolean setChangeAppLanguage(boolean isChange) {
        mIsChange = isChange;
        return mIsChange;
    }


    public static void changeAppLanguage(Context context, String newLanguage) {
        if (!mIsChange) return;
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();

        // app locale
        Locale locale = getLocaleByLanguage(newLanguage);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            Locale.setDefault(locale);
            configuration.locale = locale;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            context.createConfigurationContext(configuration);
        } else {
            // updateConfiguration
            DisplayMetrics dm = resources.getDisplayMetrics();
            resources.updateConfiguration(configuration, dm);
        }

        //ToastUtil.makeToast("changeAppLanguage 默认语言 = " + newLanguage);
        setAppLanguage(context, newLanguage);
    }


    private static boolean isSupportLanguage(String language) {
        return mAllLanguages.containsKey(language);
    }

    public static String getSupportLanguage(String language) {
        if (isSupportLanguage(language)) {
            return language;
        }

        return SIMPLIFIED_CHINESE;
    }

    /**
     * 获取指定语言的locale信息，如果指定语言不存在{@link #mAllLanguages}，返回本机语言，如果本机语言不是语言集合中的一种{@link #mAllLanguages}，返回英语
     *
     * @param language language
     * @return
     */
    public static Locale getLocaleByLanguage(String language) {
        if (isSupportLanguage(language)) {
            return mAllLanguages.get(language);
        } else {
            Locale locale = Locale.getDefault();
            for (String key : mAllLanguages.keySet()) {
                if (TextUtils.equals(mAllLanguages.get(key).getLanguage(), locale.getLanguage())) {
                    return locale;
                }
            }
        }
        return Locale.SIMPLIFIED_CHINESE;
    }


    public static Context attachBaseContext(Context context, String language) {
        if (!mIsChange) return context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        } else {
            return context;
        }
    }


    @TargetApi(Build.VERSION_CODES.N)
   	private static Context updateResources(Context context, String language) {
//        ToastUtil.makeToast("updateResources 默认语言 = " + language);
        Resources resources = context.getResources();
        Locale locale = getLocaleByLanguage(language);
        Locale.setDefault(locale);
//        ToastUtil.makeToast("updateResources 语言 = " + locale.getDisplayLanguage());
   		Configuration configuration = resources.getConfiguration();
   		configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
//        configuration.setLocales(new LocaleList(locale));
   		return context.createConfigurationContext(configuration);
   	}

    public static String getAppLanguage(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString("app_language_pref_key" , SIMPLIFIED_CHINESE);
    }

    public static void setAppLanguage(Context context, String language) {
        SharedPreferences.Editor mEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        mEditor.putString("app_language_pref_key"/*context.getString(R.string.app_language_pref_key)*/, language).apply();
    }

    public static void onLanguageChange(Context context) {
        if (!mIsChange) return;
        LanguageUtils.changeAppLanguage(context, LanguageUtils.getSupportLanguage(getAppLanguage(context)));
    }
}
