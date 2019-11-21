package com.tongchen.twatcher.gank.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.tongchen.twatcher.MainActivity
import com.tongchen.twatcher.R
import com.tongchen.twatcher.base.ui.fragment.BaseFragment
import com.tongchen.twatcher.base.ui.fragment.MVPFragment
import com.tongchen.twatcher.gank.model.entity.Category
import com.tongchen.twatcher.gank.model.entity.GankResult
import com.tongchen.twatcher.gank.presenter.CategoryPresenter
import com.tongchen.twatcher.gank.presenter.ICategoryPresenter
import com.tongchen.twatcher.base.ui.BaseRecyclerAdapter
import com.tongchen.twatcher.gank.ui.adapter.CategoryAdapter
import com.tongchen.twatcher.gank.view.ICategoryView
import com.tongchen.twatcher.util.LogUtils
import com.tongchen.twatcher.widget.SimpleItemDecoration
import kotlinx.android.synthetic.main.gank_fragment_category.*
import javax.inject.Inject

/**
 * @author TongChen
 * @date 2019/11/20  15:32
 * <p>
 * Desc:
 */
class CategoryFragment : MVPFragment<MutableList<GankResult>, ICategoryView, ICategoryPresenter>(), ICategoryView {

    private lateinit var mContentAdapter: CategoryAdapter
    private val mData = mutableListOf<GankResult>()

    //  当前所处Tab的分类
    private var mCategory: Category? = null
    private var mRequestName: String? = null
    private var mPage = 1
    private var mSpanCount = 1
    protected var mContentFragment: BaseFragment? = null

    @Inject
    lateinit var mContext: Context

    companion object {
        internal val SINGLE_SPAN_COUNT = 1
        internal val MULTIPLE_SPAN_COUNT = 2

        private val ARG_CATEGORY = "category"

        fun newInstance(category: Category): CategoryFragment {
            val fragment = CategoryFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_CATEGORY, category)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mCategory = arguments?.getParcelable(ARG_CATEGORY)
            mRequestName = mCategory?.requestName
        }
    }

    override fun injectFragment() {
        fragmentComponent.inject2Fragment(this)
    }

    override fun bindLayout() = R.layout.gank_fragment_category

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mSpanCount = if (mRequestName == "福利") {

            MULTIPLE_SPAN_COUNT
        } else {
            SINGLE_SPAN_COUNT
        }

        smartRefreshLyt.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPage = 1
                mPresenter.getGankDataByPage(mRequestName, 10, mPage, CategoryPresenter.MODE_REFRESH)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPresenter.getGankDataByPage(mRequestName, 10, ++mPage, CategoryPresenter.MODE_MORE)
            }
        })

        val layoutManager = GridLayoutManager(mContext, mSpanCount, GridLayoutManager.VERTICAL, false)
        recyclerlv_content.layoutManager = layoutManager
        mContentAdapter = CategoryAdapter(mContext, mSpanCount, mData)
        recyclerlv_content.adapter = mContentAdapter
        recyclerlv_content.addItemDecoration(SimpleItemDecoration(mContext))
        recyclerlv_content.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    //  滑动停止后再加载图片
                    RecyclerView.SCROLL_STATE_IDLE -> Glide.with(mContext).resumeRequests()
                    //  滑动时暂停加载图片
                    RecyclerView.SCROLL_STATE_DRAGGING,
                    RecyclerView.SCROLL_STATE_SETTLING -> Glide.with(mContext).pauseRequests()
                }
            }
        })

        mContentAdapter.setOnItemClickListener(object : BaseRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(itemView: View, position: Int) {
                val itemData = mData[position]
                if (mActivity is MainActivity) {
                    if (itemData.type == "福利") {
                        mContentFragment = ContentPicFragment.newInstance(itemData)

                    } else {
                        mContentFragment = ContentTextFragment.newInstance(itemData)
                    }
                    (mActivity as MainActivity).startFragment(mContentFragment)
                }
            }
        })

        mPresenter.getGankDataByPage(mRequestName, 10, mPage, CategoryPresenter.MODE_REFRESH)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun refreshSucceed(result: MutableList<GankResult>?) {
        smartRefreshLyt.finishRefresh(true)

        mData.clear()
        if (result != null) {
            mData.addAll(result)
            mContentAdapter.notifyDataSetChanged()
        }

        LogUtils.d("CategoryFragment", "refreshSucceed---" + mCategory + ", size:${result?.size}")
    }

    override fun refreshFailed(errorMsg: String?) {
        smartRefreshLyt.finishRefresh(false)

        LogUtils.d("CategoryFragment", "refreshFailed---$errorMsg")
    }

    override fun loadMoreSucceed(result: MutableList<GankResult>?) {
        smartRefreshLyt.finishLoadMore(true)

        if (result != null) {
            removeIncorrectData(result)
            mData.addAll(result)
            mContentAdapter.notifyDataSetChanged()
        }
        LogUtils.d("CategoryFragment", "loadMoreSucceed---${result.toString()}")
    }

    override fun loadMoreFailed(errorMsg: String?) {
        smartRefreshLyt.finishLoadMore(false)

        LogUtils.d("CategoryFragment", "loadMoreFailed---$errorMsg")
    }

    private fun removeIncorrectData(result: MutableList<GankResult>) {
        val incorrectData = mutableListOf<GankResult>()
        for (element in result) {
            if (element.type == "福利") {
                //  url 不以jpg、jpeg结尾或者包含 7xi8d6.com（此网址挂了）或者来自的 img.gank.io（证书无效）的移除
                if (!(element.url.endsWith(".jpg") || element.url.endsWith(".jpeg"))
                        || element.url.contains("7xi8d6.com")
                        || element.url.contains("img.gank.io")) {
                    incorrectData.add(element)
                }
            }
        }
        result.removeAll(incorrectData)
    }

    //  返回到顶部
    fun back2Top() {
        recyclerlv_content.smoothScrollToPosition(0)
    }
}