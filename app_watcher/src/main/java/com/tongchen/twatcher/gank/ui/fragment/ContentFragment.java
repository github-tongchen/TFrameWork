package com.tongchen.twatcher.gank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tongchen.twatcher.R;
import com.tongchen.twatcher.base.ui.fragment.MVPFragment;
import com.tongchen.twatcher.gank.model.entity.Android;
import com.tongchen.twatcher.gank.presenter.IContentPresenter;
import com.tongchen.twatcher.gank.ui.adapter.ContentAdapter;
import com.tongchen.twatcher.gank.view.IContentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ContentFragment extends MVPFragment<List<Android>, IContentView, IContentPresenter> implements IContentView {

    private static final String ARG_TITLE = "title";

    @BindView(R.id.recyclerlv_content)
    RecyclerView mContentRecyclerLv;

    private ContentAdapter mContentAdapter;
    private List<Android> mData = new ArrayList<>();

    //  当前所处Tab的分类
    private String mTitle;


    public ContentFragment() {

    }

    public static ContentFragment newInstance(String title) {
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    protected void injectFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mContentAdapter = new ContentAdapter(R.layout.gank_recycle_item_content, mData);
        mContentRecyclerLv.setLayoutManager(linearLayoutManager);
        mContentRecyclerLv.setAdapter(mContentAdapter);

        mContentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    //  返回到顶部
    public void back2Top() {
        mContentRecyclerLv.smoothScrollToPosition(0);
    }

    @Override
    public int bindLayout() {
        return R.layout.gank_fragment_content;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void requestSucceed(List<Android> androidList) {
        mData.clear();
        mData.addAll(androidList);
        mContentRecyclerLv.notify();
    }

    @Override
    public void requestFailed(String errorMsg) {

    }
}
