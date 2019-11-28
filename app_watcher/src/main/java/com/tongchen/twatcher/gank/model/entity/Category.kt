package com.tongchen.twatcher.gank.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author TongChen
 * @date 2019/11/21  18:31
 * <p>
 * Desc:
 */
@Parcelize
data class Category(

        //  显示的名称
        var mCategoryName: String = "",
        //  请求用的参数
        var mRequestName: String? = null,
        //  当前分类在分类List中的下标
        var mIndex: Int = 0,
        //  分类List的大小
        var mCount: Int = 0

) : BaseBean(), Parcelable {

    constructor(builder: Builder) : this() {
        mCategoryName = builder.categoryName
        mRequestName = builder.requestName
        mIndex = builder.index
        mCount = builder.count
    }

    class Builder {
        var categoryName: String = ""
        var requestName: String = ""
        var index: Int = 0
        var count = 9

        fun categoryName(categoryName: String): Builder {
            this.categoryName = categoryName
            return this
        }

        fun requestName(requestName: String): Builder {
            this.requestName = requestName
            return this
        }

        fun index(index: Int): Builder {
            this.index = index
            return this
        }

        fun count(count: Int): Builder {
            this.count = count
            return this
        }

        fun build(): Category {
            return Category(this)
        }
    }

}


