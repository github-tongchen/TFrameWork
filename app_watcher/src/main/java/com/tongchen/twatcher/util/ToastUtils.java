package com.tongchen.twatcher.util;

import android.widget.Toast;

import com.tongchen.twatcher.TApp;

/**
 * Created by TongChen at 16:13 on 2018/12/26.
 * <p>
 * Description: Toast工具类
 */
public class ToastUtils {

    private ToastUtils() {
        throw new UnsupportedOperationException("ToastUtils doesn't need to be initialized!");
    }


    public static void showShort(CharSequence message) {
        Toast.makeText(TApp.mInstance, message, Toast.LENGTH_SHORT).show();
    }


    public static void showShort(int message) {
        Toast.makeText(TApp.mInstance, message, Toast.LENGTH_SHORT).show();
    }


    public static void showLong(CharSequence message) {
        Toast.makeText(TApp.mInstance, message, Toast.LENGTH_LONG).show();
    }


    public static void showLong(int message) {
        Toast.makeText(TApp.mInstance, message, Toast.LENGTH_LONG).show();
    }


    public static void show(CharSequence message, int duration) {
        Toast.makeText(TApp.mInstance, message, duration).show();
    }


    public static void show(int message, int duration) {
        Toast.makeText(TApp.mInstance, message, duration).show();
    }
}
