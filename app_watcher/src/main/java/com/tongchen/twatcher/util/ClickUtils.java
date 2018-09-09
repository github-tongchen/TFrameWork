package com.tongchen.twatcher.util;

/**
 * Created by TongChen at 17:33 on 2018/7/1.
 * <p>
 * Description:该文件实现的功能
 */
public class ClickUtils {

    private static String TAG = "ClickUtils";

    public ClickUtils() {
        throw new IllegalStateException("ClickUtils doesn't need to be initialized!");
    }

    //  上次点击的时间
    private static long mLastClickTime;
    //  两次点击的最大时间间隔
    private static final long INTERVAL_TIME = 800;

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

}
