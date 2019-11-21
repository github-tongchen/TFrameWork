package com.tongchen.twatcher.gank.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.tongchen.twatcher.MainActivity
import com.tongchen.twatcher.R
import com.tongchen.twatcher.base.ui.fragment.BaseFragment
import com.tongchen.twatcher.gank.model.entity.Category
import com.tongchen.twatcher.gank.ui.adapter.FragmentAdapter
import kotlinx.android.synthetic.main.gank_fragment.*

/**
 * @author TongChen
 * @date 2019/11/13  14:32
 * <p>
 * Desc:
 */
class GankMainFragment : BaseFragment() {

    private lateinit var mCategoryList: MutableList<Category>
    private lateinit var mFragmentList: MutableList<Fragment>

    companion object {
        fun newInstance(): GankMainFragment {
            return GankMainFragment()
        }
    }

    override fun bindLayout() = R.layout.gank_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initDatas()
        toolbar.setNavigationOnClickListener {
            if (mActivity is MainActivity) {
                (mActivity as MainActivity).toggleDrawerLyt()
            }
        }

        fab.setOnClickListener {
            val index = viewpager.currentItem
            (mFragmentList[index] as CategoryFragment).back2Top()

        }
    }

    private fun initDatas() {
        initCategories()
        initFragments()

        val adapter = FragmentAdapter(childFragmentManager, mFragmentList, mCategoryList)
        viewpager.adapter = adapter
        viewpager.addOnPageChangeListener(this)
        tabLyt.setupWithViewPager(viewpager)
    }

    private fun initCategories() {
        mCategoryList = mutableListOf(
                Category.Builder().categoryName("全部").requestName("all").index(0).build(),
                Category.Builder().categoryName("Android").requestName("Android").index(1).build(),
                Category.Builder().categoryName("iOS").requestName("iOS").index(2).build(),
                Category.Builder().categoryName("前端").requestName("前端").index(3).build(),
                Category.Builder().categoryName("拓展资源").requestName("拓展资源").index(4).build(),
                Category.Builder().categoryName("休息视频").requestName("休息视频").index(5).build(),
                Category.Builder().categoryName("瞎推荐").requestName("瞎推荐").index(6).build(),
                Category.Builder().categoryName("App").requestName("App").index(7).build(),
                Category.Builder().categoryName("福利").requestName("福利").index(8).build())
    }

    private fun initFragments() {
        mFragmentList = mutableListOf()
        for (i in 0 until mCategoryList.size) {
            val fragment = CategoryFragment.newInstance(mCategoryList[i])
            mFragmentList.add(fragment)
        }
    }
}