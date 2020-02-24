package com.ggh.easy.utils;
import android.util.Log;

/**
 * log工具
 * app启动时初始化，设置是否debug模式
 * 如果是 则打印
 */
public class L {
	private static String TAG = "text";
	private static boolean debug = true;
	public static void i(String log){
		if(debug){
			Log.i(TAG, log);
//			Logger.i(log);
		}
	}
	public static void d(String log){
		if(debug){
			Log.d(TAG, log);
//			Logger.d(log);
		}
	}
	public static void w(String log){
		if(debug){
			Log.w(TAG, log);
//			Logger.w(log);
		}
	}
	/**
	 * 是否为debug模式 
	 *  如果不是则 不打印日志
	 */
	public static void debug(boolean yes){
		debug = yes;
	}
	
	/**
	 * 是否为debug模式 
	 *  如果不是则 不打印日志
	 */
	public static void debug(boolean yes, String tag){
		debug = yes;
		TAG = tag;
	}
	
	public static void e(String log){
		if(debug){
			Log.e(TAG, log);
		}
	}

	/**
	 * @param log
	 * log json字符样式
	 */
	public static void json(String log){
		if(debug){
			Log.e(TAG, log);
//			Logger.json(log);
		}
	}
}
