package com.tongchen.twatcher.base.view

/**
 * Created by TongChen at 22:51 on 2018/6/21.
 *
 *
 * Description:该文件实现的功能
 *
 *
 * E: Data Model 数据类型
 */
interface IMVPView<E> {

    fun showLoading()

    fun hideLoading()

    fun refreshSucceed(result: E?)

    fun refreshFailed(errorMsg: String)

    fun loadMoreSucceed(result: E?)

    fun loadMoreFailed(errorMsg: String)
}
