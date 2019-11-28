package com.tongchen.twatcher.mzitu.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.tongchen.twatcher.mzitu.model.entity.MZiTuCategory

/**
 * @author TongChen
 * @date 2019/11/28  10:48
 * <p>
 * Desc:
 */
class MZiTuFragmentAdapter : FragmentPagerAdapter {

    private lateinit var mFragmentList: MutableList<Fragment>
    private lateinit var mCategoryList: MutableList<MZiTuCategory>

    private constructor(fm: FragmentManager?) : super(fm) {

    }

    constructor(fm: FragmentManager?, fragmentList: MutableList<Fragment>, categoryList: MutableList<MZiTuCategory>) : super(fm) {
        mFragmentList = fragmentList
        mCategoryList = categoryList
    }


    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mCategoryList[position].categoryName
    }
}