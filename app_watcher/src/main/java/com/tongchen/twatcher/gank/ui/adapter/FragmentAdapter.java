package com.tongchen.twatcher.gank.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tongchen.twatcher.gank.model.entity.Category;

import java.util.List;

/**
 * Created by TongChen on 2018/9/30.
 * <p>
 * Description:
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private List<Category> mCategoryList;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<Category> categories) {
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
