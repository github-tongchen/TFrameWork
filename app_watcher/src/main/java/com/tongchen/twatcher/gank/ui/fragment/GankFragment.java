package com.tongchen.twatcher.gank.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tongchen.twatcher.MainActivity;
import com.tongchen.twatcher.R;
import com.tongchen.twatcher.base.ui.fragment.BaseFragment;
import com.tongchen.twatcher.gank.FragmentAdapter;
import com.tongchen.twatcher.gank.model.entity.Category;

import java.util.ArrayList;
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

    private List<Category> mCategoryList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();

    private FragmentAdapter mAdapter;
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
    public int bindLayout() {
        return R.layout.gank_fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initDatas();
    }

    private void initDatas() {
        initCategories();
        initFragments();

        mAdapter = new FragmentAdapter(getFragmentManager(), mFragmentList, mCategoryList);
        viewpager.setAdapter(mAdapter);
        viewpager.addOnPageChangeListener(this);
        tabLyt.setupWithViewPager(viewpager);
    }

    private void initCategories() {
        mCategoryList.add(new Category("全部", "all", 0, 9));
        mCategoryList.add(new Category("Android", "Android", 1, 9));
        mCategoryList.add(new Category("iOS", "iOS", 2, 9));
        mCategoryList.add(new Category("前端", "前端", 3, 9));
        mCategoryList.add(new Category("拓展资源", "拓展资源", 4, 9));
        mCategoryList.add(new Category("休息视频", "休息视频", 5, 9));
        mCategoryList.add(new Category("瞎推荐", "瞎推荐", 6, 9));
        mCategoryList.add(new Category("App", "App", 7, 9));
        mCategoryList.add(new Category("福利", "福利", 8, 9));
    }

    private void initFragments() {
        for (int i = 0; i < mCategoryList.size(); i++) {
            CategoryFragment fragment = CategoryFragment.newInstance(mCategoryList.get(i));
            mFragmentList.add(fragment);
        }
    }

    @OnClick(R.id.fab)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                int index = viewpager.getCurrentItem();
                ((CategoryFragment) mFragmentList.get(index)).back2Top();
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
