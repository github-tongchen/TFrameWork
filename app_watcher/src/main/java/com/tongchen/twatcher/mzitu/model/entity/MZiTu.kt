package com.tongchen.twatcher.mzitu.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author TongChen
 * @date 2019/11/28  16:23
 * <p>
 * Desc: 妹子图解析实体类
 */
@Parcelize
data class MZiTu(
        var id: Int = 0,
        var name: String = "",
        var date: String = "",
        var viewCount: String = "",
        var thumbUrl: String = "",
        var height: Int = 0,
        var width: Int = 0) : Parcelable {
}