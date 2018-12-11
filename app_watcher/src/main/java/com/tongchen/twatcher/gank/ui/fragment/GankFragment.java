package com.tongchen.twatcher.gank.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

import com.tongchen.twatcher.MainActivity;
import com.tongchen.twatcher.R;
import com.tongchen.twatcher.base.ui.fragment.BaseFragment;
import com.tongchen.twatcher.gank.FragmentAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GankFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.tabLyt)
    TabLayout tabLyt;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.fab)
    FloatingActionButton mFABtn;

    private List<String> mTitleList;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentAdapter mAdapter;

    private String[] mTitleArray = {"全部", "Android", "iOS", "前端", "拓展资源", "休息视频", "瞎推荐", "App", "福利"};
    //  上一个选中的页面position
    private int mLastPos;
    private MainActivity mActivity;

    public GankFragment() {

    }

    public static GankFragment newInstance() {
        return new GankFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = (MainActivity) getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initDatas();
    }

    private void initDatas() {
        mTitleList = new ArrayList<>(Arrays.asList(mTitleArray));

        for (int i = 0; i < mTitleList.size(); i++) {
            ContentFragment fragment = ContentFragment.newInstance(mTitleList.get(i));
            mFragmentList.add(fragment);
        }

        mAdapter = new FragmentAdapter(getFragmentManager(), mFragmentList, mTitleList);
        viewpager.setAdapter(mAdapter);
        viewpager.addOnPageChangeListener(this);
        tabLyt.setupWithViewPager(viewpager);
    }

    @Override
    public int bindLayout() {
        return R.layout.gank_fragment;
    }

    @OnClick(R.id.fab)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                int index = viewpager.getCurrentItem();
                ((ContentFragment) mFragmentList.get(index)).back2Top();
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i == 0) {
            mActivity.isDispatchEvent2DrawerLayout(true);
        } else {
            mActivity.isDispatchEvent2DrawerLayout(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
