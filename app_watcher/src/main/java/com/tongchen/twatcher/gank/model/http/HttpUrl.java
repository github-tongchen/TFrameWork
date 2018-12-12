package com.tongchen.twatcher.gank.model.http;

/**
 * Created by TongChen at 17:59 on 2018/7/8.
 * <p>
 * Description:该文件实现的功能
 */
public class HttpUrl {

    public static final String GANK_BASE = "https://gank.io/api/";

    /**
     * 加载图片时添加此后缀，并在后面添加宽度来选择图片的大小
     */
    public static final String IMAGE_WIDTH_SUFFIX = "?imageView2/0/w/";

    /**
     * 加载图片时添加此后缀，并在后面添加高度来选择图片的大小
     */
    public static final String IMAGE_HEIGHT_SUFFIX = "?imageView2/0/h/";
}
