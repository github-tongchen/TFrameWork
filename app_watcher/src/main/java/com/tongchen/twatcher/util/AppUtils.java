package com.tongchen.twatcher.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
    private static PackageManager mPkgMgr = null;

    public AppUtils() {
        throw new IllegalStateException("AppUtils doesn't need to be initialized!");
    }


    /**
     * 获取指定包名对应的PackageInfo
     *
     * @param context     上下文
     * @param packageName 制定的包名
     * @return 指定包名对应的PackageInfo
     */
    public static PackageInfo getPackageInfo(Context context, String packageName) {
        if (mPkgMgr == null) {
            mPkgMgr = context.getPackageManager();
        }
        try {
            return mPkgMgr.getPackageInfo(packageName, 0);
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
     * 启动指定的App
     * 注：getLaunchIntentForPackage()方法只有在指定包名的App已安装且有主Activity时才返回intent,否则返回null
     *
     * @param context     上下文
     * @param packageName 要启动的App包名
     */
    public static void openApp(Context context, String packageName) {
        if (!checkPackageExist(context, packageName)) {
            LogUtils.w(TAG, "要启动的App不存在");
            return;
        }
        if (mPkgMgr == null) {
            mPkgMgr = context.getPackageManager();
        }
        Intent intent = mPkgMgr.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            LogUtils.w(TAG, "要启动的App没有主Activity");
            return;
        }
        if (checkActivityExported(context, intent)) {
            LogUtils.w(TAG, "启动指定的App");

            context.startActivity(intent);
        } else {
            LogUtils.w(TAG, "无法启动指定的App，"
                    + " checkActivityExported() = " + checkActivityExported(context, intent));
        }
    }

    /**
     * 启动指定App的指定Activity(如果不是主Activity需要在Manifest中该Activity标签中设置 android:exported="true")
     * 此外还可以在Intent中设置Action、Category等标签进行过滤
     *
     * @param context      上下文
     * @param packageName  要启动的App包名
     * @param activityName 要启动的的Activity名（包含包名）
     */
    public static void openAppActivity(Context context, String packageName, String activityName) {
        if (!checkPackageExist(context, packageName)) {
            LogUtils.w(TAG, "要启动的Activity对应的App不存在");
            return;
        }
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(packageName, activityName);
        intent.setComponent(componentName);
        if (checkActivityExistByResolve(context, intent) && checkActivityExported(context, intent)) {
            LogUtils.w(TAG, "启动指定的App的指定Activity");

            context.startActivity(intent);
        } else {
            LogUtils.w(TAG, "无法启动指定的Activity，"
                    + "checkActivityExistByResolve() = " + checkActivityExistByResolve(context, intent)
                    + " checkActivityExported() = " + checkActivityExported(context, intent));
        }
    }

    /**
     * 检查指定包名的PackageInfo是否存在(即对应的App是否存在)
     *
     * @param context     上下文
     * @param packageName 指定的包名
     * @return 指定包名的App是否存在
     */
    public static boolean checkPackageExist(Context context, String packageName) {
        return getPackageInfo(context, packageName) != null;
    }

    /**
     * 使用PackageManager的queryIntentActivities()方法检查要启动的Activity是否存在
     *
     * @param context 上下文
     * @param intent  启动Activity的Intent
     * @return 要启动的Activity是否存在
     */
    public static boolean checkActivityExistByQuery(Context context, Intent intent) {
        if (mPkgMgr == null) {
            mPkgMgr = context.getPackageManager();
        }
        return mPkgMgr.queryIntentActivities(intent, 0).size() > 0;
    }

    /**
     * 使用PackageManager的resolveActivity()方法检查要启动的Activity是否存在
     * <p>
     * 不使用 Intent.resolveActivity() 方法的原因如下：
     * 查看Intent.resolveActivity()方法源码个人理解，手动构造Intent时会产生Component,
     * 使得Intent.resolveActivity()的返回值永远不为null，无法判断Activity是否存在
     *
     * @param context 上下文
     * @param intent  启动Activity的Intent
     * @return 要启动的Activity是否存在
     */
    public static boolean checkActivityExistByResolve(Context context, Intent intent) {
        if (mPkgMgr == null) {
            mPkgMgr = context.getPackageManager();
        }
        return mPkgMgr.resolveActivity(intent, 0) != null;
    }

    /**
     * 获取Manifest文件中指定Activity标签下的android:exported属性值(不设置该属性默认为false)
     *
     * @param context 上下文
     * @param intent  启动指定Activity的Intent
     * @return Manifest文件中指定Activity标签下的android:exported属性的值
     */
    public static boolean checkActivityExported(Context context, Intent intent) {
        if (intent.getComponent() == null) {
            LogUtils.w(TAG, "intent.getComponent()为空");
            return false;
        }
        if (mPkgMgr == null) {
            mPkgMgr = context.getPackageManager();
        }
        try {
            ActivityInfo activityInfo = mPkgMgr.getActivityInfo(intent.getComponent(), 0);
            return activityInfo.exported;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 安装App
     *
     * @param context 上下文
     * @param file    要安装的Apk文件
     */
    public static void installAPK(Context context, File file) {
        if (file == null || !file.exists()) {
            LogUtils.w(TAG, "App无法安装，Apk文件不存在");
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
        if (!checkPackageExist(context, packageName)) {
            LogUtils.w(TAG, "找不到要卸载的App,请检查包名是否正确，packageName = " + packageName);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
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
        if (mPkgMgr == null) {
            mPkgMgr = context.getPackageManager();
        }
        //  判断是否有Activity能够匹配我们的隐式Intent，如果找不到匹配的Activity就会返回null
        if (intent.resolveActivity(mPkgMgr) != null) {
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
        if (mPkgMgr == null) {
            mPkgMgr = context.getPackageManager();
        }
        //  返回所有成功匹配的Activity
        List<ResolveInfo> infoList = mPkgMgr.queryIntentActivities(intent, 0);
        return infoList.size() > 0;
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
     * 根据资源的名字从资源文件中获取对应的Id
     * 如：使用 R.drawable.sample 的 resType——"drawable"和 resId——"sample" 可以获取到R.drawable.sample
     * 在 R 文件中的id
     *
     * @param context 上下文
     * @param resType 上面的xxx的值，如“raw”、“drawable”、“color”等
     * @param resName 上面的String类型的name
     */
    public static int getResourceId(Context context, String resType, String resName) {
        return context.getResources().getIdentifier(resName, resType, context.getPackageName());
    }

}
