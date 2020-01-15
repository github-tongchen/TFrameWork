package com.tongchen.twatcher.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import java.lang.reflect.Field;

/**
 * Created by TongChen at 11:25 on 2019/07/09.
 * <p>
 * Description: 资源工具类
 * <p>
 * 自定义View styleable 样式举例：
 * <p>
 * <declare-styleable name="CircleImageView">
 * ****<attr name="border_width" format="integer|reference" />
 * ****<attr name="border_color" format="color|reference" />
 * ****<attr name="circle_background_color" format="color|reference" />
 * </declare-styleable>
 */
public class ResourceUtils {

    private ResourceUtils() {
        throw new UnsupportedOperationException("ResourceUtils can't be initialized!");
    }

    public static int getLayoutId(Context context, String resName) {
        return findIdByResources(context, resName, "layout");
    }

    public static int getLayoutId(String packageName, String resName) {
        return findIdByReflection(packageName, resName, "layout");
    }

    public static int getId(Context context, String resName) {
        return findIdByResources(context, resName, "id");
    }

    public static int getId(String packageName, String resName) {
        return findIdByReflection(packageName, resName, "id");
    }

    public static int getStringId(Context context, String resName) {
        return findIdByResources(context, resName, "string");
    }

    public static int getStringId(String packageName, String resName) {
        return findIdByReflection(packageName, resName, "string");
    }

    public static int getDrawableId(Context context, String resName) {
        return findIdByResources(context, resName, "drawable");
    }

    public static int getDrawableId(String packageName, String resName) {
        return findIdByReflection(packageName, resName, "drawable");
    }

    public static int getStyleId(Context context, String resName) {
        return findIdByResources(context, resName, "style");
    }

    public static int getStyleId(String packageName, String resName) {
        return findIdByReflection(packageName, resName, "style");
    }

    public static int getColorId(Context context, String resName) {
        return findIdByResources(context, resName, "color");
    }

    public static int getColorId(String packageName, String resName) {
        return findIdByReflection(packageName, resName, "color");
    }

    public static int getRawId(Context context, String resName) {
        return findIdByResources(context, resName, "raw");
    }

    public static int getRawId(String packageName, String resName) {
        return findIdByReflection(packageName, resName, "raw");
    }

    public static int getAnimId(Context context, String resName) {
        return findIdByResources(context, resName, "anim");
    }

    public static int getAnimId(String packageName, String resName) {
        return findIdByReflection(packageName, resName, "anim");
    }


    /**
     * 以自定义属性 "CircleImageView" 为例
     *
     * @param context
     * @param resName
     * @return 返回从自定义属性获取到的 TypedArray
     * <p>
     * 使用方式举例：TypedArray typedArray = ResourceUtils.getTypedArray(context,"CircleImageView",attrs);
     */
    public static TypedArray getTypedArray(Context context, String resName, AttributeSet attrs) {
        return context.obtainStyledAttributes(attrs, getAttrArray(context, resName));
    }

    /**
     * 以自定义属性 "CircleImageView" 为例
     * <p>
     * 通过 Resources.getIdentifier() 的方式获取 R.styleable.CircleImageView 对应的数组
     *
     * @param context
     * @param resName
     * @return 返回 R.styleable.CircleImageView 对应的数组
     * <p>
     * 使用方式举例：TypedArray typedArray = context.obtainStyledAttributes(attrs, ResourceUtils.getAttrArray(context,"CircleImageView"));
     */
    public static int[] getAttrArray(Context context, String resName) {
        //  注意，这里的类型用的是 attr, 而不是 styleable
        return new int[]{findIdByResources(context, resName, "attr")};
    }

    /**
     * 以自定义属性 "CircleImageView" 为例
     * <p>
     * 通过反射的方式获取 R.styleable.CircleImageView 对应的数组
     *
     * @param packageName
     * @param resName
     * @return 返回 R.styleable.CircleImageView 对应的数组
     * <p>
     * 使用方式举例：TypedArray typedArray = context.obtainStyledAttributes(attrs, ResourceUtils.getAttrArray(packageName,"CircleImageView"));
     */
    public static int[] getAttrArray(String packageName, String resName) {
        try {
            Field field = Class.forName(packageName + ".R$styleable").getDeclaredField(resName);
            field.setAccessible(true);
            return (int[]) field.get(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以自定义属性 "CircleImageView" 为例
     * <p>
     * 通过 Resources.getIdentifier() 的方式获取 R.styleable.CircleImageView_border_color 对应的id
     *
     * @param context
     * @param resName
     * @return 返回 R.styleable.CircleImageView_border_color 对应的id，传入 typedArray.getXXX() 中即可获取到对应属性的值
     * <p>
     * 使用方式举例：mBorderColor = typedArray.getColor(getStyleableId(context,"CircleImageView_border_color"), Color.WHITE);
     */
    public static int getStyleableId(Context context, String resName) {
        return findIdByResources(context, resName, "styleable");
    }


    /**
     * 以自定义属性 "CircleImageView" 为例
     * <p>
     * 通过反射的方式获取 R.styleable.CircleImageView_border_color 对应的id
     *
     * @param packageName
     * @param resName
     * @return 返回 R.styleable.CircleImageView_border_color 对应的id，传入 typedArray.getXXX() 中即可获取到对应属性的值
     * <p>
     * 使用方式举例：mBorderColor = typedArray.getColor(getStyleableId(packageName,"CircleImageView_border_color"), Color.WHITE);
     */
    public static int getStyleableId(String packageName, String resName) {
        try {
            Field field = Class.forName(packageName + ".R$styleable").getDeclaredField(resName);
            field.setAccessible(true);
            return (int) (Integer) field.get(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 通过 Resources.getIdentifier() 的方式
     * 根据资源的名字从资源文件中获取对应的Id
     * 如：使用 R.drawable.sample 的 resType——"drawable"和 resId——"sample" 可以获取到R.drawable.sample 的id
     *
     * @param context 上下文
     * @param resType 上面的xxx的值，如“raw”、“drawable”、“color”等
     * @param resName 上面的String类型的name
     */
    public static int findIdByResources(Context context, String resName, String resType) {
        return context.getResources().getIdentifier(resName, resType, context.getPackageName());
    }

    /**
     * 通过 反射 的方式
     * 根据资源的名字从资源文件中获取对应的Id
     * 如：使用 R.drawable.sample 的 resType——"drawable"和 resId——"sample" 可以获取到R.drawable.sample 的id
     *
     * @param packageName 包名
     * @param resType     上面的xxx的值，如“raw”、“drawable”、“color”等
     * @param resName     上面的String类型的name
     */
    public static int findIdByReflection(String packageName, String resName, String resType) {
        try {
            Class clazz = Class.forName(packageName + ".R$" + resType);
            return clazz.getField(resName).getInt(clazz);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
