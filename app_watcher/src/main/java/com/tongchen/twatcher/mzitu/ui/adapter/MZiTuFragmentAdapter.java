package com.tongchen.twatcher.mzitu.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tongchen.twatcher.gank.model.entity.Category;
import com.tongchen.twatcher.mzitu.model.entity.MZiTuCategory;

import java.util.List;

/**
 * Created by TongChen on 2019/1/7.
 * <p>
 * Description:
 */

public class MZiTuFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private List<MZiTuCategory> mCategoryList;

    public MZiTuFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public MZiTuFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<MZiTuCategory> categories) {
        super(fm);
        mFragmentList = fragments;
        mCategoryList = categories;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCategoryList.get(position).getCategoryName();
    }
}
