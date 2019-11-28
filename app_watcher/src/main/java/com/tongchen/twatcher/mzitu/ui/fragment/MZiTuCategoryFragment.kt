package com.tongchen.twatcher.mzitu.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.tongchen.twatcher.R
import com.tongchen.twatcher.base.ui.BaseRecyclerAdapter
import com.tongchen.twatcher.base.ui.fragment.MVPFragment
import com.tongchen.twatcher.mzitu.model.entity.MZiTu
import com.tongchen.twatcher.mzitu.model.entity.MZiTuCategory
import com.tongchen.twatcher.mzitu.presenter.IMZiTuCategoryPresenter
import com.tongchen.twatcher.mzitu.ui.adapter.MZiTuCategoryAdapter
import com.tongchen.twatcher.mzitu.view.IMZiTuCategoryView
import com.tongchen.twatcher.util.LogUtils
import com.tongchen.twatcher.widget.SimpleItemDecoration
import kotlinx.android.synthetic.main.gank_fragment_category.*
import javax.inject.Inject

/**
 * @author TongChen
 * @date 2019/11/28  12:04
 * <p>
 * Desc:
 */
class MZiTuCategoryFragment : MVPFragment<MutableList<MZiTu>, IMZiTuCategoryView, IMZiTuCategoryPresenter>(), IMZiTuCategoryView {

    private val TAG = "MZiTuCategoryFragment"

    private var mCategory: MZiTuCategory? = null
    private var mRequestName: String? = null
    private val mSpanCount = 2

    private lateinit var mContentAdapter: MZiTuCategoryAdapter
    private val mData = mutableListOf<MZiTu>()

    @Inject
    lateinit var mContext: Context

    companion object {
        const val ARG_CATEGORY = "category"

        @JvmStatic
        fun newInstance(category: MZiTuCategory): MZiTuCategoryFragment {
            val fragment = MZiTuCategoryFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_CATEGORY, category)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mCategory = arguments!!.getParcelable(ARG_CATEGORY)
            mRequestName = mCategory?.requestName
        }
    }

    override fun injectFragment() {
        fragmentComponent.inject2Fragment(this)
    }

    override fun bindLayout() = R.layout.mzitu_fragment_category

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mContentAdapter = MZiTuCategoryAdapter(mContext, mData)
        mContentAdapter.setOnItemClickListener(object : BaseRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(itemView: View, position: Int) {

            }
        })
        recyclerlv_content.adapter = mContentAdapter

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(mSpanCount, StaggeredGridLayoutManager.VERTICAL)
        recyclerlv_content.layoutManager = staggeredGridLayoutManager
        recyclerlv_content.addItemDecoration(SimpleItemDecoration(mContext))
        recyclerlv_content.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        //  滑动停止后再加载图片
                        Glide.with(mContext).resumeRequests()
                    }

                    RecyclerView.SCROLL_STATE_DRAGGING,
                    RecyclerView.SCROLL_STATE_SETTLING -> {
                        //  滑动时暂停加载图片
                        Glide.with(mContext).pauseRequests()
                    }
                }
            }
        })

        smartRefreshLyt.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPresenter.getMZiTuCoverListByCategory(mRequestName, false)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPresenter.getMZiTuCoverListByCategory(mRequestName, true)
            }
        })

        mPresenter.getMZiTuCoverListByCategory(mRequestName, true)
    }

    override fun noMoreData() {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun refreshSucceed(result: MutableList<MZiTu>?) {
        smartRefreshLyt.finishRefresh(true)

        mData.clear()
        if (result != null) {
            mData.addAll(result)
        }
        mContentAdapter.notifyDataSetChanged()

        LogUtils.d(TAG, "refreshSucceed:${result?.get(0)?.thumbUrl}")
    }

    override fun refreshFailed(errorMsg: String?) {
        smartRefreshLyt.finishRefresh(false)

        LogUtils.d(TAG, "refreshFailed---$errorMsg")
    }

    override fun loadMoreSucceed(result: MutableList<MZiTu>?) {
        smartRefreshLyt.finishLoadMore(true)

        if (result != null) {
            mData.addAll(result)
            mContentAdapter.notifyDataSetChanged()
        }

        LogUtils.d(TAG, "loadMoreSucceed:${result?.get(0)?.thumbUrl}")
    }

    override fun loadMoreFailed(errorMsg: String?) {
        smartRefreshLyt.finishLoadMore(false)

        LogUtils.d(TAG, "loadMoreFailed---$errorMsg")
    }
}