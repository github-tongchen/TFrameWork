package com.tongchen.twatcher.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by TongChen at 12:41 on 2018/6/30.
 * <p>
 * Description: 网络工具类
 */
public class NetUtils {

    private static final String TAG = "NetUtils";

    // 手机网络类型
    public static final int NET_TYPE_WIFI = 0x01;
    public static final int NET_TYPE_CMWAP = 0x02;
    public static final int NET_TYPE_CMNET = 0x03;

    public NetUtils() {
        throw new IllegalStateException("NetUtils doesn't need to be initialized!");
    }

    /**
     * 检查WiFi是否开启
     *
     * @param context 上下文
     * @return WiFi是否开启
     */
    public static boolean isWifiOpen(Context context) {
        boolean isWifiConnect = false;
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectMgr == null) {
            return false;
        }
        NetworkInfo[] networkInfos = connectMgr.getAllNetworkInfo();
        for (NetworkInfo networkInfo : networkInfos) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    isWifiConnect = false;
                }
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    isWifiConnect = true;
                }
            }
        }
        return isWifiConnect;
    }

    /**
     * 检查当前网络是否连接
     *
     * @param context 上下文
     * @return 当前网络是否连接
     */
    public static boolean netIsConnected(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        //手机网络连接状态
        NetworkInfo mobNetInfo;
        //WIFI连接状态
        NetworkInfo wifiNetInfo;
        if (connectMgr != null) {
            mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return mobNetInfo.isConnected() || wifiNetInfo.isConnected();
        }
        return true;
    }

    /**
     * 获取当前网络类型
     *
     * @param context 上下文
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectMgr != null) {
            networkInfo = connectMgr.getActiveNetworkInfo();
        }
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (extraInfo != null && !extraInfo.isEmpty()) {
                if (extraInfo.equalsIgnoreCase("cmnet")) {
                    netType = NET_TYPE_CMNET;
                } else {
                    netType = NET_TYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NET_TYPE_WIFI;
        }
        return netType;
    }

    /**
     * 检查当前是否有网
     *
     * @param context 上下文
     * @return 当前是否有网
     */
    public static boolean hasInternet(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectMgr == null) {
            return false;
        }
        NetworkInfo networkinfo = connectMgr.getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isAvailable();

    }
}
