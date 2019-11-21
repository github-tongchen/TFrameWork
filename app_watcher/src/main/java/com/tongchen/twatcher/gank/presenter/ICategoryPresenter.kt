package com.tongchen.twatcher.gank.presenter

import com.tongchen.twatcher.base.presenter.IMVPPresenter
import com.tongchen.twatcher.gank.view.ICategoryView

/**
 * @author TongChen
 * @date 2019/11/21  17:47
 * <p>
 * Desc:
 */
interface ICategoryPresenter : IMVPPresenter<ICategoryView> {

    //  mode 请求的方式：0 refresh; 1 loadMore
    fun getGankDataByPage(category: String?, size: Int, page: Int, mode: Int)
}