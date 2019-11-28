package com.tongchen.twatcher.mzitu.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author TongChen
 * @date 2019/11/28  16:11
 * <p>
 * Desc: 妹子图分类
 */
@Parcelize
data class MZiTuCategory(
        //  显示的名称
        val categoryName: String? = "",
        //  请求用的参数
        val requestName: String? = "") : Parcelable {

    constructor(builder: Builder) : this(builder.categoryName, builder.requestName) {

    }

    class Builder {
        var categoryName: String? = ""
        var requestName: String? = ""

        fun categoryName(categoryName: String?): Builder {
            this.categoryName = categoryName
            return this
        }

        fun requestName(requestName: String?): Builder {
            this.requestName = requestName
            return this
        }

        fun build(): MZiTuCategory {
            return MZiTuCategory(this)
        }
    }
}