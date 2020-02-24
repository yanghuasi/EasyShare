package com.ggh.easy.utils;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * 类名: NetUtil</br> 
 * 包名：com.cndatacom.imusicsellhelper.util </br> 
 * 描述: 网络相关的工具类</br>
 * 发布版本号：</br>
 * 开发人员： luohf</br>
 * 创建时间： 2013年11月13日
 */
public class NetUtil {
	public static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
	public static Uri APN_TABLE_URI = Uri.parse("content://telephony/carriers");
	/**
	 * 方法名: </br>
	 * 详述: 判断是否有网络连接</br>
	 * 开发人员：luohf</br>
	 * 创建时间：2013年7月17日</br>
	 * @param context
	 * @return true 可用		false 不可用
	 */
	public static boolean isNetworkAvailable(Context context) {
		if (context != null) {
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
	                .getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
	        if (mNetworkInfo != null) {  
	            return mNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false; 
	}
	/**
	 * 方法名: </br>
	 * 详述: 判断手机类型是否是电信手机</br>
	 * 开发人员：luohf</br>
	 * 创建时间：2013年11月13日</br>
	 * @param context
	 * @return
	 */
	  public static boolean isChinaNet(Context context){
		  TelephonyManager mPhone = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	      int type = mPhone.getPhoneType();
	      if(2 == type) {
	    	  return true;
	      }
		  return false;

	  }
	/**
	 * 
	 * 方法名: </br>
	 * 详述: 检测网络类型</br>
	 * 开发人员：luohf</br>
	 * 创建时间：2013年11月13日</br>
	 * @param context
	 * @return -1:无网络， 0:移动网络，1:wifi网络
	 */
	public static int checkNetWork(Context context){
	    try {
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkinfo = manager.getActiveNetworkInfo();
			if (networkinfo == null || !networkinfo.isAvailable()) {    
			   return -1;
			}
			
			if(networkinfo.getType() == ConnectivityManager.TYPE_MOBILE){
				return 0;
			}
			
			if(networkinfo.getType() == ConnectivityManager.TYPE_WIFI){
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//	    State mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();   
//	    if(mobile == State.CONNECTED){
//	       return 0;
//	    }
//	    State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();      
//	    if(wifi == State.CONNECTED){
//	       return 1;
//	    }
	    return -1;
	}
	/**
	 * 检测网络类型
	 * *@param context
	 * @return -1:无网络， 0:移动网络，1:wifi网络
	 * NETWORK_TYPE_CDMA 网络类型为CDMA
         * NETWORK_TYPE_EDGE 网络类型为EDGE
         * NETWORK_TYPE_EVDO_0 网络类型为EVDO0
         * NETWORK_TYPE_EVDO_A 网络类型为EVDOA
         * NETWORK_TYPE_GPRS 网络类型为GPRS
         * NETWORK_TYPE_HSDPA 网络类型为HSDPA
         * NETWORK_TYPE_HSPA 网络类型为HSPA
         * NETWORK_TYPE_HSUPA 网络类型为HSUPA
         * NETWORK_TYPE_UMTS 网络类型为UMTS
 			联通的3G为UMTS或HSDPA，
 			移动和联通的2G为GPRS或EDGE，
 			电信的2G为CDMA，电信的3G为EVDO
	 */
	public static String checkNetWorkType(Context context){
		try {
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkinfo = manager.getActiveNetworkInfo();
			if (networkinfo == null || !networkinfo.isAvailable()) {    
			   return "无网络连接";
			}
			
			if(networkinfo.getType() == ConnectivityManager.TYPE_MOBILE){
				String networkType = "未知";
				switch (networkinfo.getSubtype()) {
					case TelephonyManager.NETWORK_TYPE_EDGE:
						networkType = "移动2G";
						break;
					case TelephonyManager.NETWORK_TYPE_GPRS:
						networkType = "联通2G";
						break;
					case TelephonyManager.NETWORK_TYPE_UMTS :
					case TelephonyManager.NETWORK_TYPE_HSDPA :
						networkType = "联通3G";
						break;
						
					case TelephonyManager.NETWORK_TYPE_CDMA :
						networkType = "电信2G";
						break;
						
					case TelephonyManager.NETWORK_TYPE_EVDO_0:
					case TelephonyManager.NETWORK_TYPE_EVDO_A:
					case TelephonyManager.NETWORK_TYPE_EVDO_B:
						networkType = "电信3G";
						break;
					default :
						break;
				}
				return networkType;
			}
			
			if(networkinfo.getType() == ConnectivityManager.TYPE_WIFI){
				return "wifi网络";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "未知";
	}
	
	/**
	 * 方法名: </br>
	 * 详述: 关闭wifi</br>
	 * 开发人员：luohf</br>
	 * 创建时间：2013年11月13日</br>
	 * @param context
	 * @return 成功返回true
	 */
	public static boolean closeWiFi(Context context){
	    try {
	      WifiManager mWifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
	      if (mWifiManager != null) {
	        mWifiManager.setWifiEnabled(false);
	        return true;
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	      return false;
	    }
	    return false;
   }
	/**
	 * 方法名: </br>
	 * 详述: 打开wifi</br>
	 * 开发人员：luohf</br>
	 * 创建时间：2013年11月13日</br>
	 * @param context
	 * @return 成功返回true
	 */
	public static boolean openWiFi(Context context){
		try {
			WifiManager mWifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
			if (mWifiManager != null) {
				mWifiManager.setWifiEnabled(true);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	/** 
	 * make true current connect service is wifi 
	 * @param mContext 
	 * @return 
	 */  
	public static boolean isWifi(Context mContext) {
	    return checkNetWork(mContext) == 1;  
	} 
	/**
	 * 方法名: </br>
	 * 详述: 获取当前wifi信息</br>
	 * 开发人员：luohf</br>
	 * 创建时间：2014年10月22日</br>
	 * @param context
	 * @return
	 */
	public static WifiInfo getWifiInfo(Context context) {
		return ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
	}
	/**
	 * 方法名: </br>
	 * 详述: 获取当前wifi信号强度</br>
	 * 开发人员：luohf</br>
	 * 创建时间：2014年10月22日</br>
	 * @param context
	 * @param numLevels
	 * @return
	 */
	public static int getWifiSignStrength(Context context, int numLevels) {
		WifiInfo wifiInfo = getWifiInfo(context);
		return calculateSignalLevel(wifiInfo.getRssi(), numLevels);
	}
	
	private static int MIN_RSSI = -120;
	private static int MAX_RSSI = -55;
	private static int calculateSignalLevel(int rssi, int numLevels) {
	    if(rssi <= MIN_RSSI) {
	        return 0;
	    } else if(rssi >= MAX_RSSI) {
	        return numLevels - 1;
	    } else {
	        float inputRange = (MAX_RSSI - MIN_RSSI);
	        float outputRange = (numLevels - 1);
	        if(inputRange != 0)
	            return (int) ((rssi - MIN_RSSI) * outputRange / inputRange);
	    }
	    return 0;
	}
	
	
//	//设置ctwap网络
//	/**
//	 * 方法名: </br>
//	 * 详述: 设置为ctwap网络</br>
//	 * 开发人员：luohf</br>
//	 * 创建时间：2013年11月13日</br>
//	 * @param context
//	 * @return 成功返回true
//	 */
//    public static boolean setCtwap(Context context){
//        ContentResolver cr = context.getContentResolver();
//        ContentValues cv = new ContentValues();
//        cv.put("name", "ctwap");
//        cv.put("apn", "ctwap");
//        cv.put("proxy", "10.0.0.200");
//        cv.put("port", "80");
//        cv.put("current", 1);
//
//        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
//        String imsi =tm.getSubscriberId();
//        if(imsi != null){
//            if(imsi.startsWith("46000")){
//                cv.put("numeric", "46000");
//                cv.put("mcc", "460");
//                cv.put("mnc", "00");
//            }
//            else if(imsi.startsWith("46003")){
//                cv.put("numeric", "46003");
//                cv.put("mcc", "460");
//                cv.put("mnc", "03");
//            }
//        }
//        
//        Cursor c = null;
//        try{
//            Uri newRow = cr.insert(APN_TABLE_URI, cv);
//            if(newRow != null){
//                c = cr.query(newRow, null, null, null, null);
//                c.moveToFirst();
//                String id = c.getString(c.getColumnIndex("_id"));
//                int apnId=Integer.parseInt(id);
//                return setAPN(context, apnId);
////    	        return res;
//            }
//            
//        }catch(Exception e){
//        	e.printStackTrace();
//        }
//        finally{
//            if(c != null){
//                c.close();
//            }
//        }   
//        return false;      
//    }
//    
//  //设置为ctwap网络
//    public static boolean setAPN(Context context, int id){
//        boolean res = false;
//        ContentResolver resolver = context.getContentResolver();
//        ContentValues values = new ContentValues();
//        values.put("apn_id", id);
//        Cursor c = null;
//        try{
//            resolver.update(PREFERRED_APN_URI, values, null, null);
//            c = resolver.query(PREFERRED_APN_URI, new String[]{"name", "apn"}, "_id=" + id, null, null);
//            if(c != null && c.moveToFirst()){
//                res = true;
//            }
//        }catch(Exception e){
//        } finally {
//        	if(c != null) {
//        		c.close();
//        	}
//        }
//        return res;
//    }
	
}
