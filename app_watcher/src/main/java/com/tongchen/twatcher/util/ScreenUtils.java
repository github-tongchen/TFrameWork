package com.tongchen.twatcher.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

/**
 * Created by TongChen at 12:21 on 2018/6/30.
 * <p>
 * Description: 屏幕尺寸工具类
 */
public class ScreenUtils {

    private static final String TAG = "ScreenUtils";
    //  当前设备是否为平板
    private static Boolean mIsTablet = null;

    public ScreenUtils() {
        throw new IllegalStateException("ScreenUtils doesn't need to be initialized!");
    }

    /**
     * dp转px
     *
     * @param context  上下文
     * @param dipValue 需要转换的dip值
     * @return dp对应的px值
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param context 上下文
     * @param pxValue 需要转换的px值
     * @return px对应的dp值
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param context 上下文
     * @param spValue 需要转换的sp值
     * @return sp对应的px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px值转sp
     *
     * @param context 上下文
     * @param pxValue 需要转换的px值
     * @return px对应的sp值
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽高
     *
     * @param context 上下文
     * @return 屏幕宽高的数组
     */
    public static int[] getScreenSize(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return new int[]{width, height};
    }

    /**
     * 获取屏幕宽度
     *
     * @param context 上下文
     * @return 屏幕宽度
     */
    public static float getScreenWidth(Context context) {
        return getScreenSize(context)[0];
    }

    /**
     * 获取屏幕高度
     *
     * @param context 上下文
     * @return 屏幕高度
     */
    public static float getScreenHeight(Context context) {
        return getScreenSize(context)[1];
    }

    /**
     * 通过反射获取状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    @SuppressLint("PrivateApi")
    public static int getStatusBarHeightByReflection(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int height = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.w(TAG, "get StatusBar height by Reflection：" + statusBarHeight);

        return statusBarHeight;
    }

    /**
     * 通过系统资源文件获取状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    private int getStatusBarHeightByResource(Context context) {
        int statusBarHeight = 0;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        LogUtils.w(TAG, "get StatusBar height by Resource：" + statusBarHeight);

        return statusBarHeight;
    }

    /**
     * 获取底部虚拟导航栏高度
     *
     * @param context 上下文
     * @return 底部虚拟导航栏高度
     */
    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        LogUtils.w(TAG, "get NavigationBar height by Resource：" + navigationBarHeight);

        return navigationBarHeight;
    }

    /**
     * 判断当前是否是横屏
     *
     * @param context 上下文
     * @return 当前是否是横屏
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断当前是否是竖屏
     *
     * @param context 上下文
     * @return 当前是否是竖屏
     */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 判断当前设备是否为平板
     *
     * @param context 上下文
     * @return 当前设备是否为平板
     */
    public static boolean isTablet(Context context) {
        if (mIsTablet == null) {
            mIsTablet = (Configuration.SCREENLAYOUT_SIZE_MASK & context.getResources().getConfiguration().screenLayout) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        }
        return mIsTablet;
    }

}
