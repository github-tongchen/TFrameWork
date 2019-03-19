package com.tongchen.twatcher.mzitu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.tongchen.twatcher.R;
import com.tongchen.twatcher.base.ui.fragment.BaseFragment;
import com.tongchen.twatcher.mzitu.model.entity.MZiTuCategory;
import com.tongchen.twatcher.mzitu.ui.adapter.MZiTuFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MZiTuFragment extends BaseFragment {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private MZiTuFragmentAdapter mAdapter;
    private List<MZiTuCategory> mCategoryList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();


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

        initCategories();
        initFragments();

        mAdapter = new MZiTuFragmentAdapter(getFragmentManager(), mFragmentList, mCategoryList);
        mViewPager.setAdapter(mAdapter);
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
