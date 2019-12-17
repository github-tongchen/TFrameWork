package com.tongchen.twatcher.base.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tongchen.twatcher.MainActivity
import com.tongchen.twatcher.base.ui.BackHandleInterface


abstract class BaseFragment : Fragment(), ViewPager.OnPageChangeListener {

    protected var mActivity: Activity? = null

    private lateinit var mBackHandleInterface: BackHandleInterface

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = activity

        if (activity !is BackHandleInterface) {
            throw ClassCastException("Hosting Activity must implement BackHandleInterface")
        } else {
            this.mBackHandleInterface = activity as BackHandleInterface
        }
        //告诉Activity，当前Fragment在栈顶
        mBackHandleInterface.setSelectedFragment(this)
    }

    override fun onDetach() {
        super.onDetach()
        if (activity !is BackHandleInterface) {
            throw ClassCastException("Hosting Activity must implement BackHandleInterface")
        } else {
            this.mBackHandleInterface = activity as BackHandleInterface
        }
        //告诉Activity，当前Fragment已移除
        mBackHandleInterface.setSelectedFragment(null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(bindLayout(), container, false)
    }


    @LayoutRes
    abstract fun bindLayout(): Int

    /**
     * 继承BaseFragment的子类需要自己处理返回键点击事件的时候重写此方法，默认不处理
     * MainActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
     * 如果没有Fragment消息时MainActivity自己才会消费该事件
     */
    open fun onBackPressed(): Boolean {
        return false
    }


    override fun onPageScrolled(i: Int, v: Float, i1: Int) {

    }

    override fun onPageSelected(i: Int) {
        if (i == 0) {
            if (mActivity is MainActivity) {
                (mActivity as MainActivity).isDispatchEvent2DrawerLayout(true)
            }
        } else {
            if (mActivity is MainActivity) {
                (mActivity as MainActivity).isDispatchEvent2DrawerLayout(false)
            }
        }
    }

    override fun onPageScrollStateChanged(i: Int) {

    }
}
