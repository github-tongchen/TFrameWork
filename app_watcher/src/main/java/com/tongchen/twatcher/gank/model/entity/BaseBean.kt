package com.tongchen.twatcher.gank.model.entity

import android.support.annotation.NonNull
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle

/**
 * @author TongChen
 * @date 2019/11/21  18:28
 * <p>
 * Desc: 重写toString()方法便于输出日志查看
 */
open class BaseBean {

    @NonNull
    @Override
    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
    }
}