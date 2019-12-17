package com.tongchen.twatcher.base.http


import com.tongchen.twatcher.gank.model.entity.GankData
import com.tongchen.twatcher.gank.model.entity.GankResult
import com.tongchen.twatcher.mzitu.model.entity.BaseResult
import com.tongchen.twatcher.mzitu.model.entity.MZiTu

import io.reactivex.Observable

/**
 * Created by TongChen at 17:44 on 2018/7/8.
 *
 *
 * Description: 全App的 API 帮助类
 */
interface IAppApiHelper {

    /**
     * @param category
     * @param size
     * @param page
     * @return
     */
    fun getGankDataByPage(category: String?, size: Int, page: Int): Observable<GankData<MutableList<GankResult>>>

    /**
     * @param tag
     * @param page
     * @param pullToRefresh
     * @return
     */
    fun listMZiTu(tag: String?, page: Int, pullToRefresh: Boolean): Observable<BaseResult<MutableList<MZiTu>>>
}
