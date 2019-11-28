package com.tongchen.twatcher.mzitu.model.entity

import android.support.annotation.IntDef

/**
 * @author TongChen
 * @date 2019/11/28  15:43
 * <p>
 * Desc: 全设置默认值才会有无参构造函数
 */
data class BaseResult<T>(

        @ResultCode
        var code: Int = 0,

        var message: String? = "",

        var data: T? = null,

        var totalPage: Int = 1) {

    companion object {
        const val SUCCESS_CODE = 1
        const val ERROR_CODE = 2
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(SUCCESS_CODE, ERROR_CODE)
    annotation class ResultCode
}