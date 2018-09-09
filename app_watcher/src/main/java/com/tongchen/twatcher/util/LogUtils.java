package com.tongchen.twatcher.util;

import android.util.Log;

import com.tongchen.twatcher.BuildConfig;

/**
 * Created by TongChen at 20:03 on 2018/6/29.
 * <p>
 * Description: 日志工具类
 */
public class LogUtils {

    /**
     * BuildConfig.DEBUG的值会随着编译版本变化，在Build Variants中选择"debug"时值为true,选择"release"值为false
     */
    private static final boolean isLog = BuildConfig.DEBUG;
    private static int DEBUG_LEVEL = 6;

    private static final int VERBOSE = 5;
    private static final int DEBUG = 4;
    private static final int INFO = 3;
    private static final int WARN = 2;
    private static final int ERROR = 1;

    /**
     * 日志长度，超出长度则分段打印日志
     */
    private static final int MAX_LENGTH = 3500;

    private LogUtils() {
        throw new IllegalStateException("LogUtils doesn't need to be initialized!");
    }

    public static void v(String tag, String msg) {
        if (DEBUG_LEVEL > VERBOSE && isLog) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG_LEVEL > DEBUG && isLog) {
            if (msg.length() > MAX_LENGTH) {
                debugLong(tag, msg);
            } else {
                Log.d(tag, msg);
            }
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG_LEVEL > INFO && isLog) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG_LEVEL > WARN && isLog) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG_LEVEL > ERROR && isLog) {
            Log.e(tag, msg);
        }
    }

    /**
     * 使用分节的方式来输出足够长度的message
     *
     * @param tag 日志标签
     * @param str 日志内容
     */
    private static void debugLong(String tag, String str) {
        str = str.trim();
        int index = 0;
        int maxLength = MAX_LENGTH;
        String subStr;
        int i = 0;
        while (index < str.length()) {
            // java的字符不允许指定超过总的长度end
            if (str.length() <= index + maxLength) {
                subStr = str.substring(index);
            } else {
                subStr = str.substring(index, index + maxLength);
            }
            Log.d(tag, "Log分段" + i + " " + subStr.trim());

            index += maxLength;
            i++;
        }
    }

}
