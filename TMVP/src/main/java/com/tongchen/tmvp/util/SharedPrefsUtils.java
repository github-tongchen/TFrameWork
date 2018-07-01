package com.tongchen.tmvp.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by TongChen at 10:51 on 2018/6/30.
 * <p>
 * Description: SharedPreferences工具类
 */
public class SharedPrefsUtils {

    private static String TAG = "SharedPrefsUtils";

    private SharedPrefsUtils() {
        throw new IllegalStateException("SharedPrefsUtils doesn't need to be initialized!");
    }

    /**
     * SharedPreferences存储在sd卡中的文件名字
     */
    private static String getSpName(Context context) {
        return context.getPackageName() + "_preferences";
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    public static void put(Context context, String key, Object o) {
        SharedPreferences sp = context.getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (o instanceof String) {
            editor.putString(key, (String) o);

        } else if (o instanceof Integer) {
            editor.putInt(key, (Integer) o);

        } else if (o instanceof Float) {
            editor.putFloat(key, (Float) o);

        } else if (o instanceof Long) {
            editor.putLong(key, (Long) o);

        } else if (o instanceof Boolean) {
            editor.putBoolean(key, (Boolean) o);

        } else {
            editor.putString(key, o.toString());
        }
        //提交
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);

        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);

        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);

        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);

        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);

        } else {
            return null;
        }
    }

    /**
     * 移除某个key值对应的键值对
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        return sp.getAll();
    }

    //*************************************内部类*******************************************//

    /**
     * 优先使用SharedPreferences的apply方法，如果找不到则使用commit方法
     * <p>
     * 主要是为了兼容API 9 以下(apply()是在API 9以后添加的)
     */
    private static class SharedPreferencesCompat {
        //查看SharedPreferences是否有apply方法
        private static final Method sApplyMethod = findApply();

        /**
         * 反射查找apply的方法
         */
        private static Method findApply() {
            try {
                Class cls = SharedPreferences.class;
                return cls.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        private static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            editor.commit();
        }

    }


}
