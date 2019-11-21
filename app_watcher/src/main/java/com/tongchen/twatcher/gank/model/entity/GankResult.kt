package com.tongchen.twatcher.gank.model.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @author TongChen
 * @date 2019/10/27  17:54
 * <p>
 * Desc: @Parcelize 注解目前为Kotlin extension实验性功能
 */
@Parcelize
data class GankResult constructor(

    @SerializedName("createdAt")
    var createdAt: String = "",

    @SerializedName("desc")
    var desc: String = "",

    //  Video和Welfare没有images字段，要做null处理
    @SerializedName("images")
    var images: List<String>? = null,

    @SerializedName("publishedAt")
    var publishedAt: String = "",

    @SerializedName("source")
    var source: String = "",

    @SerializedName("type")
    var type: String = "",

    @SerializedName("url")
    var url: String = "",

    @SerializedName("used")
    var isUsed: Boolean = false,

    @SerializedName("who")
    var who: String = "",

    @SerializedName("_id")
    var id: String = ""

) : Parcelable {

}
