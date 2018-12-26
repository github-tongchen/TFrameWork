package com.tongchen.twatcher.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by TongChen at 18:11 on 2018/6/30.
 * <p>
 * Description: 输入工具类
 */
public class InputUtils {

    private static String TAG = "InputUtils";

    private static InputMethodManager mInputMethodManager = null;

    private InputUtils() {
        throw new IllegalStateException("InputUtils doesn't need to be initialized!");
    }

    /**
     * 弹出软键盘
     *
     * @param context 上下文
     * @param view    文本输入框
     */
    public static void showSoftKeyBoard(Context context, View view) {
        if (view == null) {
            LogUtils.w(TAG, "view为空，弹出键盘失败");
            return;
        }
        if (mInputMethodManager == null) {
            mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        view.requestFocus();
        mInputMethodManager.showSoftInputFromInputMethod(view.getWindowToken(), 0);
    }

    /**
     * 隐藏软键盘
     *
     * @param context 上下文
     * @param view    存在于当前布局 ViewTree 中，随意一个 View
     */
    public static void hideSoftKeyboard(Context context, View view) {
        if (view == null) {
            LogUtils.w(TAG, "view为空，隐藏键盘失败");
            return;
        }
        if (mInputMethodManager == null) {
            mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 弹出/隐藏软键盘
     *
     * @param context 上下文
     * @param view    存在于当前布局 ViewTree 中，随意一个 View
     */
    public static void toggleSoftKeyboard(Context context, View view) {
        if (view == null) {
            LogUtils.w(TAG, "view为空，弹出/隐藏 键盘失败");
            return;
        }
        if (mInputMethodManager == null) {
            mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        mInputMethodManager.toggleSoftInputFromWindow(view.getWindowToken(), 0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 将文本复制到剪切板
     *
     * @param context 上下文
     * @param string  被复制到剪切板的文本
     */
    public static void text2Board(Context context, String string) {
        if (TextUtils.isEmpty(string)) {
            LogUtils.w(TAG, "空文本不可以复制到剪切板");
            return;
        }
        //  获取系统剪贴板管理器
        ClipboardManager clipboardMgr = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //  创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
        ClipData clipData = ClipData.newPlainText("Type_Text", "需要复制的文本数据");
        //  把数据集设置（复制）到剪贴板
        if (clipboardMgr != null) {
            clipboardMgr.setPrimaryClip(clipData);
        }
    }

    /**
     * 从剪切板获取文本
     *
     * @param context 上下文
     * @return 从剪切板中取出的文本
     */
    public static String board2Text(Context context) {
        ClipboardManager clipboardMgr = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardMgr == null) {
            LogUtils.w(TAG, "获取ClipboardManager失败");
            return "";
        }
        //  检查剪贴板是否有内容
        if (clipboardMgr.hasPrimaryClip()) {
            //  一般取出剪贴板里的第一条内容
            return clipboardMgr.getPrimaryClip().getItemAt(0).coerceToText(context).toString();
        } else {
            LogUtils.w(TAG, "剪切板没有文本内容,clipboardMgr.getPrimaryClip() = " + clipboardMgr.getPrimaryClip());
            return "";
        }
    }

}
