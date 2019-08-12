package com.tongchen.twatcher.mzitu.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tongchen.twatcher.MainActivity;
import com.tongchen.twatcher.R;
import com.tongchen.twatcher.base.ui.fragment.BaseFragment;
import com.tongchen.twatcher.mzitu.model.entity.MZiTuCategory;
import com.tongchen.twatcher.mzitu.ui.adapter.MZiTuFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MZiTuFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabLyt)
    TabLayout mTabLyt;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private List<MZiTuCategory> mCategoryList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();

    private MZiTuFragmentAdapter mAdapter;

    public static MZiTuFragment newInstance() {
        return new MZiTuFragment();
    }

    @Override
    public int bindLayout() {
        return R.layout.mzitu_fragment;
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

        mAdapter = new MZiTuFragmentAdapter(getFragmentManager(), mFragmentList, mCategoryList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        mTabLyt.setupWithViewPager(mViewPager);
    }

    private void initCategories() {
        mCategoryList.add(new MZiTuCategory.Builder().categoryName("主页").requestName("index").build());
        mCategoryList.add(new MZiTuCategory.Builder().categoryName("最热").requestName("hot").build());
        mCategoryList.add(new MZiTuCategory.Builder().categoryName("推荐").requestName("best").build());
        mCategoryList.add(new MZiTuCategory.Builder().categoryName("性感妹子").requestName("xinggan").build());
        mCategoryList.add(new MZiTuCategory.Builder().categoryName("日本妹子").requestName("japan").build());
        mCategoryList.add(new MZiTuCategory.Builder().categoryName("台湾妹子").requestName("taiwan").build());
        mCategoryList.add(new MZiTuCategory.Builder().categoryName("清纯妹子").requestName("mm").build());
    }

    private void initFragments() {
        for (int i = 0; i < mCategoryList.size(); i++) {
            MZiTuCategoryFragment fragment = MZiTuCategoryFragment.newInstance(mCategoryList.get(i));
            mFragmentList.add(fragment);
        }
    }
}
