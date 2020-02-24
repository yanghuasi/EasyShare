package com.ggh.easy.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

/**
 * Activity 跳转工具类
 */
public class ActivityLaunchUtils {

    public static boolean startActivity(Context context, Class<?> clz) {
        return startActivity(context, clz, null);
    }

    public static boolean startActivity(Context context, Class<?> clz, Bundle bundle) {
        if (context == null || clz == null)
            return false;

        Intent intent = new Intent(context, clz);
        if (bundle != null)
            intent.putExtras(bundle);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        try {
//            context.startActivity(intent);
//        } catch (Exception globalException) {
//            // catch all exception here
//            globalException.printStackTrace();
//            return false;
//        }
        return startActivity(context, intent);
    }

    public static boolean startActivity(Context context, Intent intent) {
        if (context == null || intent == null) {
            return false;
        }

        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        try {
            context.startActivity(intent);
        } catch (Exception globalException) {
            // catch all exception here
            globalException.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean startActivityForResult(Activity activity,Intent intent, int requestCode) {
        if (activity == null || intent == null) {
            return false;
        }

        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception globalException) {
            globalException.printStackTrace();
            return false;
        }
        return true;
    }

//    public static void startFragment(Class<?> clz) {
//        Intent intent = new Intent(ImageLoaderApplication.getAppContext(), SingleFragmentActivity.class);
//        intent.putExtra(SingleFragmentActivity.FRAGMENT_PARAM, clz);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        ImageLoaderApplication.getAppContext().startActivity(intent);
//    }

//    public static void startFragment(Class<?> clz, Bundle bundle) {
//        Intent intent = new Intent(ImageLoaderApplication.getAppContext(), SingleFragmentActivity.class);
//        bundle.putSerializable(SingleFragmentActivity.FRAGMENT_PARAM, clz);
//        intent.putExtras(bundle);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        ImageLoaderApplication.getAppContext().startActivity(intent);
//    }

    public static void launch(Activity activity, Intent intent, View transitionView) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, "IMAGE");
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

}
