package com.tongchen.twatcher.mzitu.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tongchen.twatcher.R;
import com.tongchen.twatcher.base.ui.fragment.MVPFragment;
import com.tongchen.twatcher.gank.model.entity.GankResult;
import com.tongchen.twatcher.gank.model.entity.MultipleItem;
import com.tongchen.twatcher.mzitu.model.entity.MZiTu;
import com.tongchen.twatcher.mzitu.model.entity.MZiTuCategory;
import com.tongchen.twatcher.mzitu.presenter.IMZiTuCategoryPresenter;
import com.tongchen.twatcher.mzitu.ui.adapter.MZiTuCategoryAdapter;
import com.tongchen.twatcher.mzitu.view.IMZiTuCategoryView;
import com.tongchen.twatcher.util.LogUtils;
import com.tongchen.twatcher.widget.SimpleItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MZiTuCategoryFragment extends MVPFragment<List<MZiTu>, IMZiTuCategoryView, IMZiTuCategoryPresenter> implements IMZiTuCategoryView,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = "MZiTuCategoryFragment";

    private static final String ARG_CATEGORY = "category";

    @Inject
    Context mContext;

    @BindView(R.id.smartRefreshLyt)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerlv_content)
    RecyclerView mContentRecyclerLv;

    private MZiTuCategory mCategory;
    private String mRequestName;

    private int mSpanCount = 2;

    private MZiTuCategoryAdapter mContentAdapter;
    private List<MZiTu> mData = new ArrayList<>();


    public static MZiTuCategoryFragment newInstance(MZiTuCategory category) {
        MZiTuCategoryFragment fragment = new MZiTuCategoryFragment();
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
            mRequestName = mCategory != null ? mCategory.getRequestName() : null;
        }
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject2Fragment(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.mzitu_fragment_category;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LogUtils.d(TAG, "-------run");

        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(mSpanCount, LinearLayoutManager.VERTICAL);
        mContentRecyclerLv.setLayoutManager(linearLayoutManager);
        mContentAdapter = new MZiTuCategoryAdapter(mData);
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

        mPresenter.getMZiTuCoverListByCategory(mRequestName, true);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getMZiTuCoverListByCategory(mRequestName, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LogUtils.d(TAG, "-------onViewCreated");

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void refreshSucceed(List<MZiTu> result) {
        LogUtils.d(TAG, result.get(0).getThumbUrl());
        mData.clear();
        mData.addAll(result);
        mContentAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshFailed(String errorMsg) {

    }

    @Override
    public void loadMoreSucceed(List<MZiTu> result) {
        LogUtils.d("CategoryFragment", "loadMoreSucceed---" + result.toString());
//        mData.clear();
        mData.addAll(result);
        mContentAdapter.notifyDataSetChanged();
        mContentAdapter.loadMoreComplete();
    }

    @Override
    public void loadMoreFailed(String errorMsg) {

    }

    @Override
    public void noMoreData() {

    }


}
