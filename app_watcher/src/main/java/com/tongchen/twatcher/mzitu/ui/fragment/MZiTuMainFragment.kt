package com.tongchen.twatcher.mzitu.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.tongchen.twatcher.MainActivity
import com.tongchen.twatcher.R
import com.tongchen.twatcher.base.ui.fragment.BaseFragment
import com.tongchen.twatcher.mzitu.model.entity.MZiTuCategory
import com.tongchen.twatcher.mzitu.ui.adapter.MZiTuFragmentAdapter
import kotlinx.android.synthetic.main.gank_fragment.*
import kotlinx.android.synthetic.main.gank_fragment_content_text.toolbar

/**
 * @author TongChen
 * @date 2019/11/28  17:05
 * <p>
 * Desc:
 */
class MZiTuMainFragment : BaseFragment() {

    private lateinit var mAdapter: MZiTuFragmentAdapter

    private val mCategoryList = mutableListOf<MZiTuCategory>()
    private val mFragmentList = mutableListOf<Fragment>()

    companion object {
        @JvmStatic
        fun newInstance() = MZiTuMainFragment()
    }

    override fun bindLayout() = R.layout.mzitu_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initData()
        toolbar.setNavigationOnClickListener {
            if (mActivity is MainActivity) {
                (mActivity as MainActivity).toggleDrawerLyt()
            }
        }
    }

    fun initData() {
        initCategories()
        initFragments()

        mAdapter = MZiTuFragmentAdapter(childFragmentManager, mFragmentList, mCategoryList)
        viewpager.adapter = mAdapter
        viewpager.addOnPageChangeListener(this)
        tabLyt.setupWithViewPager(viewpager)
    }

    fun initCategories() {
        mCategoryList.add(MZiTuCategory.Builder().categoryName("主页").requestName("index").build())
        mCategoryList.add(MZiTuCategory.Builder().categoryName("最热").requestName("hot").build())
        mCategoryList.add(MZiTuCategory.Builder().categoryName("推荐").requestName("best").build())
        mCategoryList.add(MZiTuCategory.Builder().categoryName("性感妹子").requestName("xinggan").build())
        mCategoryList.add(MZiTuCategory.Builder().categoryName("日本妹子").requestName("japan").build())
        mCategoryList.add(MZiTuCategory.Builder().categoryName("台湾妹子").requestName("taiwan").build())
        mCategoryList.add(MZiTuCategory.Builder().categoryName("清纯妹子").requestName("mm").build())
    }

    fun initFragments() {
        for (i in 0 until mCategoryList.size) {
            val fragment = MZiTuCategoryFragment.newInstance(mCategoryList[i])
            mFragmentList.add(fragment)
        }
    }
}