package com.tongchen.twatcher.gank.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tongchen.twatcher.R;
import com.tongchen.twatcher.TApp;
import com.tongchen.twatcher.base.ui.fragment.MVPFragment;
import com.tongchen.twatcher.di.component.DaggerFragmentComponent;
import com.tongchen.twatcher.di.module.FragmentModule;
import com.tongchen.twatcher.gank.model.entity.Category;
import com.tongchen.twatcher.gank.model.entity.GankResult;
import com.tongchen.twatcher.gank.presenter.CategoryPresenter;
import com.tongchen.twatcher.gank.presenter.ICategoryPresenter;
import com.tongchen.twatcher.gank.ui.adapter.CategoryAdapter;
import com.tongchen.twatcher.gank.view.ICategoryView;
import com.tongchen.twatcher.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class CategoryFragment extends MVPFragment<List<GankResult>, ICategoryView, ICategoryPresenter> implements ICategoryView,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String ARG_CATEGORY = "category";

    @Inject
    Context mContext;

    @BindView(R.id.recyclerlv_content)
    RecyclerView mContentRecyclerLv;

    private CategoryAdapter mContentAdapter;
    private List<GankResult> mData = new ArrayList<>();

    //  当前所处Tab的分类
    private Category mCategory;
    private String mRequestName;
    private int mPage = 1;


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
        return R.layout.gank_fragment_content;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mContentAdapter = new CategoryAdapter(R.layout.gank_recycle_item_content, mData);
        mContentRecyclerLv.setLayoutManager(linearLayoutManager);
        mContentRecyclerLv.setAdapter(mContentAdapter);
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

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void refreshSucceed(List<GankResult> result) {
        LogUtils.d("CategoryFragment", "refreshSucceed---" + result.size());
        mData.clear();
        mData.addAll(result);
        mContentAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshFailed(String errorMsg) {
        LogUtils.d("CategoryFragment", "refreshFailed---" + errorMsg);

    }

    @Override
    public void loadMoreSucceed(List<GankResult> result) {
        LogUtils.d("CategoryFragment", "loadMoreSucceed---" + result.size());
        mData.addAll(result);
        mContentAdapter.notifyDataSetChanged();
        mContentAdapter.loadMoreComplete();
    }

    @Override
    public void loadMoreFailed(String errorMsg) {
        LogUtils.d("CategoryFragment", "loadMoreFailed---" + errorMsg);
        mContentAdapter.loadMoreFail();
    }


    //  返回到顶部
    public void back2Top() {
        mContentRecyclerLv.smoothScrollToPosition(0);
    }
}
