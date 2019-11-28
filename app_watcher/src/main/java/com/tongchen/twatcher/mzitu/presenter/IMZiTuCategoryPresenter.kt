package com.tongchen.twatcher.mzitu.presenter

import com.tongchen.twatcher.base.presenter.IMVPPresenter
import com.tongchen.twatcher.mzitu.view.IMZiTuCategoryView

/**
 * @author TongChen
 * @date 2019/11/28  14:49
 * <p>
 * Desc: 妹子图按分类获取封面列表
 */
interface IMZiTuCategoryPresenter : IMVPPresenter<IMZiTuCategoryView> {

    fun getMZiTuCoverListByCategory(category: String?, pullToRefresh: Boolean)
}