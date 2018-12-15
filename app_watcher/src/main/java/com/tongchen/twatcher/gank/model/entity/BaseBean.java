package com.tongchen.twatcher.gank.model.entity;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by TongChen at 11:50 on 2018/12/15.
 * <p>
 * Description: 重写toString()方法便于输出日志查看
 */
public class BaseBean {

    @NonNull
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
