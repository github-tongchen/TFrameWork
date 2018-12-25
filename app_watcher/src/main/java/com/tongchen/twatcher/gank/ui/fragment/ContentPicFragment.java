package com.tongchen.twatcher.gank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.tongchen.twatcher.R;
import com.tongchen.twatcher.TApp;
import com.tongchen.twatcher.base.ui.fragment.MVPFragment;
import com.tongchen.twatcher.di.component.DaggerFragmentComponent;
import com.tongchen.twatcher.di.module.FragmentModule;
import com.tongchen.twatcher.gank.model.entity.GankResult;
import com.tongchen.twatcher.gank.presenter.IPicPresenter;
import com.tongchen.twatcher.gank.ui.adapter.PicAdapter;
import com.tongchen.twatcher.gank.view.IPicView;
import com.tongchen.twatcher.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ContentPicFragment extends MVPFragment<List<GankResult>, IPicView, IPicPresenter> implements IPicView {

    public static final String TAG = "ContentTextFragment";

    private static final String ARG_GANK_RESULT = "gank_result";
    private static final String ARG_ITEM_INDEX = "item_index";

    private GankResult mGankResult;
    private int mItemIndex;

    @BindView(R.id.viewpager_pic)
    ViewPager mPicViewPager;

    //  上一个图片的下标
    private int mLastCount = 0;

    private List<GankResult> mGankResultList = new ArrayList<>();
    private PicAdapter mPicAdapter;
    private int mPage;
    private int mCurIndex;

    public ContentPicFragment() {
    }

    public static ContentPicFragment newInstance(GankResult result, int index) {
        ContentPicFragment fragment = new ContentPicFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_GANK_RESULT, result);
        args.putInt(ARG_ITEM_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGankResult = getArguments().getParcelable(ARG_GANK_RESULT);
            mItemIndex = getArguments().getInt(ARG_ITEM_INDEX);
        }
    }

    @Override
    protected void injectFragment() {
        DaggerFragmentComponent.builder()
                .appComponent(TApp.getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject2Fragment(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.gank_fragment_content_pic;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



       /* mGankResultList.add(mGankResult);
        mGankResultList.add(mGankResult);*/

        mPicAdapter = new PicAdapter(mActivity, mGankResultList);
        mPicViewPager.setAdapter(mPicAdapter);
        mLastCount = mGankResultList.size();

        mPage = mItemIndex / 10 + 1;
        mPresenter.getGankData4Pic(10, mPage);

        mPicViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                LogUtils.d("TTT", "----run");
                //mPresenter.getGankData4Pic(1, 1);
                mCurIndex = i;
                if (mCurIndex >= 10 * mPage - 1) {
                    mPresenter.getGankData4Pic(10, ++mPage);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void refreshSucceed(List<GankResult> result) {
        mGankResultList.addAll(result);
        mPicAdapter.notifyDataSetChanged();
        mPicViewPager.setCurrentItem(mItemIndex);
    }

    @Override
    public void refreshFailed(String errorMsg) {

    }

    @Override
    public void loadMoreSucceed(List<GankResult> result) {

    }

    @Override
    public void loadMoreFailed(String errorMsg) {

    }
}
