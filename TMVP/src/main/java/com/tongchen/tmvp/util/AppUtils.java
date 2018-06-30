package com.tongchen.tmvp.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;
import java.util.List;

/**
 * Created by TongChen at 17:14 on 2018/6/30.
 * <p>
 * Description: 获取App常用信息的工具类
 */
public class AppUtils {

    private static final String TAG = "AppUtils";
    //  上次点击的时间
    private static long mLastClickTime;
    //  两次点击的最大时间间隔
    private static final long INTERVAL_TIME = 800;


    public AppUtils() {
        throw new IllegalStateException("AppUtils doesn't need to be initialized!");
    }

    /**
     * 通过控制2次点击的时间间隔防止连续点击
     *
     * @return 是否在短时间内连续点击2次
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - mLastClickTime;
        if (0 < timeD && timeD < INTERVAL_TIME) {
            return true;
        }
        mLastClickTime = time;
        return false;
    }

    /**
     * 获取指定包名对应的PackageInfo
     *
     * @param context     上下文
     * @param packageName 制定的包名
     * @return 指定包名对应的PackageInfo
     */
    public static PackageInfo getPackageInfo(Context context, String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取App版本号
     *
     * @param context 上下文
     * @return App 版本号 versionCode
     */
    public static int getVersionCode(Context context) {
        return getVersionCode(context, context.getPackageName());
    }

    /**
     * 获取指定包名对应的APK的版本号
     *
     * @param context     上下文
     * @param packageName 要获取versionCode的APK包名
     * @return 指定包名对应的App的版本号 versionCode
     */
    public static int getVersionCode(Context context, String packageName) {
        PackageInfo pkgInfo = getPackageInfo(context, packageName);
        if (pkgInfo != null) {
            return pkgInfo.versionCode;
        } else {
            return 0;
        }
    }

    /**
     * 获取App版本名称
     *
     * @param context 上下文
     * @return App版本名称 versionName
     */
    public static String getVersionName(Context context) {
        return getVersionName(context, context.getPackageName());
    }

    /**
     * 获取指定包名对应的App的版本名称
     *
     * @param context     上下文
     * @param packageName 要获取versionName的APK包名
     * @return 指定包名对应的App的版本名称 versionName
     */
    public static String getVersionName(Context context, String packageName) {
        PackageInfo pkgInfo = getPackageInfo(context, packageName);
        if (pkgInfo != null) {
            return pkgInfo.versionName;
        } else {
            return "";
        }
    }

    /**
     * 安装App
     *
     * @param context 上下文
     * @param file    要安装的Apk文件
     */
    public static void installAPK(Context context, File file) {
        if (file == null || !file.exists()) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LogUtils.w(TAG, "系统版本 >= N ，使用 fileProvider 获取Uri进行安装");

            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            LogUtils.w(TAG, "版本 < N ，从文件生成Uri进行安装");

            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /**
     * 卸载App
     *
     * @param context     上下文
     * @param packageName 要卸载应用的包名
     */
    public static void uninstallApk(Context context, String packageName) {
        if (isPackageExist(context, packageName)) {
            Intent intent = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * 指定包名的PackageInfo是否存在(即对应的App是否存在)
     *
     * @param context     上下文
     * @param packageName 指定的包名
     * @return 指定包名的App是否存在
     */
    public static boolean isPackageExist(Context context, String packageName) {
        return getPackageInfo(context, packageName) != null;
    }

    /**
     * 启动指定的App
     *
     * @param context     上下文
     * @param packageName 要启动的App包名
     */
    public static void openApp(Context context, String packageName) {
        if (isPackageExist(context, packageName)) {
            Intent mainIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            context.startActivity(mainIntent);
        }
    }

    /**
     * 启动指定App的指定Activity(需要在Manifest中该Activity标签中设置 android:exported="true")
     * 此外还可以在Intent中设置Action、Category等标签进行过滤
     *
     * @param context      上下文
     * @param packageName  要启动的App包名
     * @param activityName 要启动的的Activity名
     */
    public static void openAppActivity(Context context, String packageName, String activityName) {
        //  这里后面验证下resolveActivity和queryIntentActivities能不能判断出当前activityName对应的Activity是否存在
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(packageName, activityName);
        intent.setComponent(componentName);
        context.startActivity(intent);
    }

    /**
     * 调用系统原生分享
     *
     * @param context 上下文
     * @param title   分享内容的Title
     * @param url     分享的内容，通常是一个http开头的链接
     */
    public static void showSystemShareOption(Context context, String title, String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享：" + title);
        intent.putExtra(Intent.EXTRA_TEXT, title + " " + url);
        context.startActivity(Intent.createChooser(intent, "选择分享"));
    }

    /**
     * 打开应用市场
     *
     * @param context     上下文
     * @param packageName 需要查看的应用的包名
     */
    public static void openMarket(Context context, String packageName) {
        if (!isHaveMarket(context)) {
            Toast.makeText(context, "您手机中没有安装应用市场！", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + packageName));
        //  判断是否有Activity能够匹配我们的隐式Intent，如果找不到匹配的Activity就会返回null
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * 检查设备是否安装了应用市场
     *
     * @param context 上下文
     * @return 是否安装了应用市场
     */
    public static boolean isHaveMarket(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        PackageManager pm = context.getPackageManager();
        //  返回所有成功匹配的Activity
        List<ResolveInfo> infoList = pm.queryIntentActivities(intent, 0);
        return infoList.size() > 0;
    }

    /**
     * 根据资源的名字从资源文件中获取对应的Id
     * 如：使用 R.drawable.sample 的 resType——"drawable"和 resId——"sample" 可以获取到R.drawable.sample
     * 在 R 文件中的id
     *
     * @param context 上下文
     * @param resType 上面的xxx的值，如“raw”、“drawable”、“color”等
     * @param resName 上面的String类型的name
     */
    public static int getResourceId(Context context, String resType, String resName) {
        Resources res = context.getResources();
        return res.getIdentifier(resName, resType, context.getPackageName());
    }
}
