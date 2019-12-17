package com.tongchen.twatcher.base.http

import com.tongchen.twatcher.gank.model.entity.GankData
import com.tongchen.twatcher.gank.model.entity.GankResult
import com.tongchen.twatcher.gank.model.http.GankServiceApi
import com.tongchen.twatcher.mzitu.model.http.MZiTuServiceApi
import com.tongchen.twatcher.mzitu.model.entity.BaseResult
import com.tongchen.twatcher.mzitu.model.entity.MZiTu
import com.tongchen.twatcher.mzitu.model.parse.MZiTuParser

import javax.inject.Inject

import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * Created by TongChen at 14:56 on 2018/12/28.
 *
 *
 * Description: 全App的 API 帮助实现类
 */
class AppApiHelper @Inject
constructor(private val mGankServiceApi: GankServiceApi, private val mMZiTuServiceApi: MZiTuServiceApi) : IAppApiHelper {

    override fun getGankDataByPage(category: String?, size: Int, page: Int): Observable<GankData<MutableList<GankResult>>> {
        return mGankServiceApi.getGankDataByPage(category, size, page)
    }

    override fun listMZiTu(tag: String?, page: Int, pullToRefresh: Boolean): Observable<BaseResult<MutableList<MZiTu>>> {
        when (tag) {
            "index" -> return action(mMZiTuServiceApi.mZiTuIndex(page), tag, page, pullToRefresh)
            "hot" -> return action(mMZiTuServiceApi.mZiTuHot(page), tag, page, pullToRefresh)
            "best" -> return action(mMZiTuServiceApi.mZiTuBest(page), tag, page, pullToRefresh)
            "japan" -> return action(mMZiTuServiceApi.mZiTuJapan(page), tag, page, pullToRefresh)
            "taiwan" -> return action(mMZiTuServiceApi.mZiTuTaiwan(page), tag, page, pullToRefresh)
            "xinggan" -> return action(mMZiTuServiceApi.mZiTuSexy(page), tag, page, pullToRefresh)
            "mm" -> return action(mMZiTuServiceApi.mZiTuMm(page), tag, page, pullToRefresh)

            else -> return action(mMZiTuServiceApi.mZiTuIndex(page), "index", page, pullToRefresh)
        }
    }

    private fun action(stringObservable: Observable<String>, tag: String, page: Int, pullToRefresh: Boolean): Observable<BaseResult<MutableList<MZiTu>>> {

        return stringObservable.map(Function { s -> MZiTuParser.parseAlbumCoverListByCategory(s, page) })
    }
}
