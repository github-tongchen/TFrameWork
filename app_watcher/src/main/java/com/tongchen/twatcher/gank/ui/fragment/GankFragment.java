package com.tongchen.twatcher.gank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tongchen.twatcher.MainActivity;
import com.tongchen.twatcher.R;
import com.tongchen.twatcher.base.ui.fragment.BaseFragment;
import com.tongchen.twatcher.gank.ui.adapter.FragmentAdapter;
import com.tongchen.twatcher.gank.model.entity.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GankFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabLyt)
    TabLayout mTabLyt;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.fab)
    FloatingActionButton mFABtn;

    private List<Category> mCategoryList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();

    private FragmentAdapter mAdapter;

    public GankFragment() {

    }

    public static GankFragment newInstance() {
        return new GankFragment();
    }

    @Override
    public int bindLayout() {
        return R.layout.gank_fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initDatas();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivity instanceof MainActivity) {
                    ((MainActivity) mActivity).toggleDrawerLyt();
                }
            }
        });
    }


    private void initDatas() {
        initCategories();
        initFragments();

        mAdapter = new FragmentAdapter(getFragmentManager(), mFragmentList, mCategoryList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        mTabLyt.setupWithViewPager(mViewPager);
    }

    private void initCategories() {
        //  改为builder模式构造
        mCategoryList.add(new Category.Builder().categoryName("全部").requestName("all").index(0).build());
        mCategoryList.add(new Category.Builder().categoryName("Android").requestName("Android").index(1).build());
        mCategoryList.add(new Category.Builder().categoryName("iOS").requestName("iOS").index(2).build());
        mCategoryList.add(new Category.Builder().categoryName("前端").requestName("前端").index(3).build());
        mCategoryList.add(new Category.Builder().categoryName("拓展资源").requestName("拓展资源").index(4).build());
        mCategoryList.add(new Category.Builder().categoryName("休息视频").requestName("休息视频").index(5).build());
        mCategoryList.add(new Category.Builder().categoryName("瞎推荐").requestName("瞎推荐").index(6).build());
        mCategoryList.add(new Category.Builder().categoryName("App").requestName("App").index(7).build());
        mCategoryList.add(new Category.Builder().categoryName("福利").requestName("福利").index(8).build());
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
                int index = mViewPager.getCurrentItem();
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
            if (mActivity instanceof MainActivity) {
                ((MainActivity) mActivity).isDispatchEvent2DrawerLayout(true);
            }
        } else {
            if (mActivity instanceof MainActivity) {
                ((MainActivity) mActivity).isDispatchEvent2DrawerLayout(false);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
