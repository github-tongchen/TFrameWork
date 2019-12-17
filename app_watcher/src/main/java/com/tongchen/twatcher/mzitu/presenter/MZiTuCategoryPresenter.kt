package com.tongchen.twatcher.mzitu.presenter

import android.annotation.SuppressLint
import com.tongchen.twatcher.base.http.IAppApiHelper
import com.tongchen.twatcher.base.presenter.MVPPresenter
import com.tongchen.twatcher.gank.view.ICategoryView
import com.tongchen.twatcher.mzitu.model.entity.MZiTu
import com.tongchen.twatcher.mzitu.view.IMZiTuCategoryView
import com.tongchen.twatcher.util.LogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author TongChen
 * @date 2019/11/28  14:55
 * <p>
 * Desc: 妹子图按分类获取封面列表
 */
class MZiTuCategoryPresenter : MVPPresenter<IMZiTuCategoryView, MutableList<MZiTu>>, IMZiTuCategoryPresenter {

    private val TAG = "MZiTuCategoryPresenter"

    private var mIAppApiHelper: IAppApiHelper
    //  请求的方式：0 refresh;1 more
    private var mMode = -1
    val MODE_REFRESH = 0
    val MODE_MORE = 1

    private var mPage = 1
    private var mTotalPage = 1

    constructor(mIAppApiHelper: IAppApiHelper) : super() {
        this.mIAppApiHelper = mIAppApiHelper
    }

    @SuppressLint("CheckResult")
    override fun getMZiTuCoverListByCategory(category: String?, pullToRefresh: Boolean) {
        if (pullToRefresh) {
            mMode = MODE_REFRESH
            mPage = 1

        } else {
            mMode = MODE_MORE
            if (mPage < mTotalPage) {
                mPage++
            }
        }

        mIAppApiHelper.listMZiTu(category, mPage, pullToRefresh)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ baseResult ->
                    if (baseResult != null) {
                        mTotalPage = baseResult.totalPage
                        requestSucceed(baseResult.data)
                    } else {
                        requestFailed("data is wrong")
                    }
                }, { throwable -> requestFailed(throwable.toString()) })
    }

    override fun requestSucceed(result: MutableList<MZiTu>?) {
        if (view == null) {
            return
        }
        LogUtils.d(TAG, "requestSucceed:${result.toString()}")

        when (mMode) {
            MODE_REFRESH -> {
                (view as IMZiTuCategoryView).hideLoading()
                (view as IMZiTuCategoryView).refreshSucceed(result)
            }

            MODE_MORE -> {
                (view as IMZiTuCategoryView).loadMoreSucceed(result)
            }
        }

    }

    override fun requestFailed(errorMsg: String) {
        if (view == null) {
            return
        }
        LogUtils.d(TAG, "requestFailed:$errorMsg")

        when (mMode) {
            MODE_REFRESH -> {
                (view as IMZiTuCategoryView).hideLoading()
                (view as IMZiTuCategoryView).refreshFailed(errorMsg)
            }

            MODE_MORE->{
                (view as IMZiTuCategoryView).loadMoreFailed(errorMsg)
            }
        }
    }


}