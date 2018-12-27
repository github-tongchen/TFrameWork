package com.tongchen.twatcher.gank.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tongchen.twatcher.MainActivity;
import com.tongchen.twatcher.R;
import com.tongchen.twatcher.TApp;
import com.tongchen.twatcher.base.ui.fragment.BaseFragment;
import com.tongchen.twatcher.base.ui.fragment.MVPFragment;
import com.tongchen.twatcher.di.component.DaggerFragmentComponent;
import com.tongchen.twatcher.di.module.FragmentModule;
import com.tongchen.twatcher.gank.model.entity.Category;
import com.tongchen.twatcher.gank.model.entity.GankResult;
import com.tongchen.twatcher.gank.model.entity.MultipleItem;
import com.tongchen.twatcher.gank.presenter.CategoryPresenter;
import com.tongchen.twatcher.gank.presenter.ICategoryPresenter;
import com.tongchen.twatcher.gank.ui.adapter.CategoryAdapter;
import com.tongchen.twatcher.gank.view.ICategoryView;
import com.tongchen.twatcher.util.LogUtils;
import com.tongchen.twatcher.widget.SimpleItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class CategoryFragment extends MVPFragment<List<GankResult>, ICategoryView, ICategoryPresenter> implements ICategoryView,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String ARG_CATEGORY = "category";

    private static final int SINGLE_SPAN_COUNT = 1;
    private static final int MULTIPLE_SPAN_COUNT = 2;

    @Inject
    Context mContext;

    @BindView(R.id.smartRefreshLyt)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerlv_content)
    RecyclerView mContentRecyclerLv;

    private CategoryAdapter mContentAdapter;
    private List<GankResult> mData = new ArrayList<>();
    private List<MultipleItem> mMultipleItemList = new ArrayList<>();

    //  当前所处Tab的分类
    private Category mCategory;
    private String mRequestName;
    private int mPage = 1;
    //  是否是图片（即福利）分类
    private boolean mIsImgType = false;
    private int mSpanCount = 1;
    private BaseFragment mContentFragment;

    public CategoryFragment() {

    }

    public static CategoryFragment newInstance(Category category) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_CATEGORY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = getArguments().getParcelable(ARG_CATEGORY);
            mRequestName = mCategory.getRequestName();
            mIsImgType = mCategory.getIndex() == mCategory.getCount() - 1;
        }
    }

    @Override
    protected void injectFragment() {
        DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .appComponent(TApp.getAppComponent())
                .build()
                .inject2Fragment(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.gank_fragment_category;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mIsImgType) {
            mSpanCount = MULTIPLE_SPAN_COUNT;
        } else {
            mSpanCount = SINGLE_SPAN_COUNT;
        }

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 1;
                mPresenter.getGankDataByPage(mRequestName, 10, mPage, CategoryPresenter.MODE_REFRESH);
            }
        });

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), mSpanCount, LinearLayoutManager.VERTICAL, false);
        mContentRecyclerLv.setLayoutManager(linearLayoutManager);
        mContentAdapter = new CategoryAdapter(mMultipleItemList);
        mContentRecyclerLv.setAdapter(mContentAdapter);
        mContentRecyclerLv.addItemDecoration(new SimpleItemDecoration(mContext));
        mContentRecyclerLv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        //  滑动停止后再加载图片
                        Glide.with(mContext).resumeRequests();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        //  滑动时暂停加载图片
                        Glide.with(mContext).pauseRequests();
                        break;
                }
            }
        });

        mContentAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mContentAdapter.disableLoadMoreIfNotFullPage(mContentRecyclerLv);
        //  设置上拉加载更多
        mContentAdapter.setOnLoadMoreListener(this, mContentRecyclerLv);
        mContentAdapter.setOnItemClickListener(this);

        mPresenter.getGankDataByPage(mRequestName, 10, mPage, CategoryPresenter.MODE_REFRESH);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getGankDataByPage(mRequestName, 10, ++mPage, CategoryPresenter.MODE_MORE);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MultipleItem item = (MultipleItem) adapter.getData().get(position);
        if (mActivity instanceof MainActivity) {
            if (item.getData().getUrl().endsWith(".jpg") || item.getData().getUrl().endsWith(".jpeg")) {
                mContentFragment = ContentPicFragment.newInstance(item.getData());
            } else {
                mContentFragment = ContentTextFragment.newInstance(item.getData());
            }
            ((MainActivity) mActivity).startFragment(mContentFragment);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void refreshSucceed(List<GankResult> result) {
        LogUtils.d("CategoryFragment", "refreshSucceed---" + mCategory);
        mRefreshLayout.finishRefresh();

        mData.clear();
        mData.addAll(result);
        mMultipleItemList.clear();
        if (mIsImgType) {
            for (GankResult data : mData) {
                mMultipleItemList.add(new MultipleItem(MultipleItem.TYPE_IMG, data));
            }
        } else {
            for (GankResult data : mData) {
                mMultipleItemList.add(new MultipleItem(MultipleItem.TYPE_TEXT, data));
            }
        }
        mContentAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshFailed(String errorMsg) {
        mRefreshLayout.finishRefresh();

        LogUtils.d("CategoryFragment", "refreshFailed---" + errorMsg);

    }

    @Override
    public void loadMoreSucceed(List<GankResult> result) {
        LogUtils.d("CategoryFragment", "loadMoreSucceed---" + result.toString());
        removeIncorrectData(result);
        mData.addAll(result);
        mMultipleItemList.clear();
        if (mIsImgType) {
            for (GankResult data : mData) {
                mMultipleItemList.add(new MultipleItem(MultipleItem.TYPE_IMG, data));
            }
        } else {
            for (GankResult data : mData) {
                mMultipleItemList.add(new MultipleItem(MultipleItem.TYPE_TEXT, data));
            }
        }
        mContentAdapter.notifyDataSetChanged();
        mContentAdapter.loadMoreComplete();
    }

    @Override
    public void loadMoreFailed(String errorMsg) {
        LogUtils.d("CategoryFragment", "loadMoreFailed---" + errorMsg);
        mContentAdapter.loadMoreFail();
    }

    //  移除有问题的数据
    private void removeIncorrectData(List<GankResult> results) {
        List<GankResult> incorrectDatas = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            GankResult result = results.get(i);
            if (TextUtils.isEmpty(result.getUrl())) {
                incorrectDatas.add(result);
            }
        }
        results.removeAll(incorrectDatas);
    }

    //  返回到顶部
    public void back2Top() {
        mContentRecyclerLv.smoothScrollToPosition(0);
    }
}
