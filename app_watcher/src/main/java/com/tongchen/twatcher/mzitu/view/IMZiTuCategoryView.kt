package com.tongchen.twatcher.mzitu.view

import com.tongchen.twatcher.base.view.IMVPView
import com.tongchen.twatcher.mzitu.model.entity.MZiTu

/**
 * @author TongChen
 * @date 2019/11/28  15:38
 * <p>
 * Desc: 妹子图按分类获取封面列表
 */
interface IMZiTuCategoryView : IMVPView<MutableList<MZiTu>> {

    fun noMoreData()
}