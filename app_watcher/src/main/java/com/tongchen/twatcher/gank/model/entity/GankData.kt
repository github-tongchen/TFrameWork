package com.tongchen.twatcher.gank.model.entity

import com.google.gson.annotations.SerializedName

/**
 * @author TongChen
 * @date 2019/11/21  18:21
 * <p>
 * Desc: GSON使用方式参考：http://www.jianshu.com/p/e740196225a4
 */
data class GankData<T> constructor(

        @SerializedName("error")
        var mError: Boolean = false,

        @SerializedName("results")
        var mResult: T? = null
)