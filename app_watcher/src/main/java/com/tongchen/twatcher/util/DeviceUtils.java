/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tongchen.twatcher.util;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

import java.util.List;

/**
 * Created by TongChen at 12:39 on 2018/6/30.
 * <p>
 * Description: 获取设备常用信息和处理设备常用操作的工具类
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class DeviceUtils {

    private static final String TAG = "DeviceUtils";

    private static PackageManager mPkgMgr = null;
    private static Boolean mHasCamera = null;

    private DeviceUtils() {
        throw new IllegalStateException("DeviceUtils doesn't need to be initialized!");
    }

    /**
     * 判断设备是否有相机
     *
     * @param context 上下文
     * @return 设备是否有相机
     */
    public static boolean hasCamera(Context context) {
        if (mHasCamera == null) {
            boolean hasFrontCamera = hasFrontCamera(context);
            boolean hasBackCamera = hasBackCamera(context);
            mHasCamera = hasFrontCamera || hasBackCamera;
        }
        return mHasCamera;
    }

    /**
     * 判断设备是否有前置相机
     *
     * @param context 上下文
     * @return 设备是否有前置相机
     */
    public static boolean hasFrontCamera(Context context) {
        if (mPkgMgr == null) {
            mPkgMgr = context.getPackageManager();
        }
        return mPkgMgr.hasSystemFeature("android.hardware.camera.front");
    }

    /**
     * 判断设备是否有后置相机
     *
     * @param context 上下文
     * @return 设备是否有后置相机
     */
    public static boolean hasBackCamera(Context context) {
        if (mPkgMgr == null) {
            mPkgMgr = context.getPackageManager();
        }
        return mPkgMgr.hasSystemFeature("android.hardware.camera");
    }

    /**
     * 设备是否有实体菜单键（SDK >= 14）
     *
     * @param context 上下文
     * @return 是否有实体菜单键
     */
    public static boolean hasHardwareMenuKey(Context context) {
        return ViewConfiguration.get(context).hasPermanentMenuKey();
    }


    /**
     * 通过判断设备是否有菜单键、HOME键、返回键(实体键)来确定是否有NavigationBar(虚拟导航栏)
     *
     * @param context 上下文
     * @return 是否有虚拟导航栏
     */
    public static boolean hasNavigationBar(Context context) {
        boolean hasMenuKey = hasHardwareMenuKey(context);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        return !hasMenuKey && !hasBackKey && !hasHomeKey;
    }

    /**
     * 判断当前设备是否锁屏
     *
     * @param context 上下文
     * @return 当前设备是否锁屏, true 解锁，false 锁屏
     */
    public static boolean isScreenOn(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (pm != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                return pm.isInteractive();
            } else {
                return pm.isScreenOn();
            }
        }
        return false;
    }


    /**
     * 调用拨号界面
     *
     * @param context  上下文
     * @param phoneNum 电话号码
     */
    public static void openDail(Context context, String phoneNum) {
        Uri uri = Uri.parse("tel:" + phoneNum);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 直接拨打电话
     *
     * @param context  上下文
     * @param phoneNum 电话号码
     */
    public static void dialPhone(Context context, String phoneNum) {
        Uri uri = Uri.parse("tel:" + phoneNum);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        context.startActivity(intent);
    }

    /**
     * 调用系统发短信界面
     *
     * @param phoneNum 电话号码
     * @param smsBody  短信内容
     */
    public static void openSMS(Context context, String phoneNum, String smsBody) {
        Uri uri = Uri.parse("smsto:" + phoneNum);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", smsBody);
        context.startActivity(it);
    }

}


