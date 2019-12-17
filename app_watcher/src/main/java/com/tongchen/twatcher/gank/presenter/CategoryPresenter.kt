package com.tongchen.twatcher.gank.presenter

import android.annotation.SuppressLint
import com.tongchen.twatcher.base.http.IAppApiHelper
import com.tongchen.twatcher.base.presenter.MVPPresenter
import com.tongchen.twatcher.gank.model.entity.GankData
import com.tongchen.twatcher.gank.model.entity.GankResult
import com.tongchen.twatcher.gank.view.ICategoryView
import com.tongchen.twatcher.util.LogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author TongChen
 * @date 2019/11/21  17:53
 * <p>
 * Desc:
 */
class CategoryPresenter : MVPPresenter<ICategoryView, GankData<MutableList<GankResult>>>, ICategoryPresenter {

    private var mIAppApiHelper: IAppApiHelper
    //  请求的方式：0 refresh;1 more
    private var mMode = -1

    companion object {
        const val MODE_REFRESH = 0
        const val MODE_MORE = 1
    }

    constructor(iAppApiHelper: IAppApiHelper) {
        mIAppApiHelper = iAppApiHelper
    }

    @SuppressLint("CheckResult")
    override fun getGankDataByPage(category: String?, size: Int, page: Int, mode: Int) {
        mMode = mode
        mIAppApiHelper.getGankDataByPage(category, size, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ gankData ->
                    if (gankData.mError) {
                        requestFailed("error = ${gankData.mError}")
                    } else {
                        requestSucceed(gankData)
                    }
                }, { throwable -> requestFailed(throwable.toString()) })
    }

    override fun requestSucceed(result: GankData<MutableList<GankResult>>?) {
        if (view == null) {
            return
        }

        LogUtils.d("CategoryPresenter", "requestSucceed:${result.toString()}")

        when (mMode) {
            MODE_REFRESH -> {
                (view as ICategoryView).hideLoading()
                (view as ICategoryView).refreshSucceed(result?.mResult)
            }

            MODE_MORE -> {
                (view as ICategoryView).loadMoreSucceed(result?.mResult)
            }
        }
    }

    override fun requestFailed(errorMsg: String) {
        if (view == null) {
            return
        }

        LogUtils.d("CategoryPresenter", "requestFailed:$errorMsg")

        when (mMode) {
            MODE_REFRESH -> {
                (view as ICategoryView).hideLoading()
                (view as ICategoryView).refreshFailed(errorMsg)
            }

            MODE_MORE -> {
                (view as ICategoryView).loadMoreFailed(errorMsg)
            }
        }
    }

}