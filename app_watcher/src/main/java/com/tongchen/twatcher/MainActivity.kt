package com.tongchen.twatcher

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.tongchen.twatcher.base.ui.BackHandleInterface
import com.tongchen.twatcher.base.ui.activity.BaseActivity
import com.tongchen.twatcher.base.ui.fragment.BaseFragment
import com.tongchen.twatcher.gank.ui.fragment.GankMainFragment
import com.tongchen.twatcher.mzitu.ui.fragment.MZiTuMainFragment
import com.tongchen.twatcher.util.ToastUtils
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_include_menu.*
import kotlin.system.exitProcess

/**
 * @author TongChen
 * @date 2019/11/29  18:30
 * <p>
 * Desc:
 */
class MainActivity : BaseActivity(), BackHandleInterface, View.OnClickListener {

    private val TAG = "MainActivity"
    //  不同分类的最底层Fragment
    private val TAG_FRAGMENT_MAIN = "MainFragment"

    private lateinit var mFragmentManager: FragmentManager
    private var mGankMainFragment: GankMainFragment? = null
    private var mMZiTuMainFragment: MZiTuMainFragment? = null
    private var mBaseFragment: BaseFragment? = null
    //  点击2次返回才退出
    private var firstTime: Long = 0

    override fun bindLayout() = R.layout.main_activity

    override fun loadView() {
        tv_gank.setOnClickListener(this)
        tv_mzitu.setOnClickListener(this)

        mFragmentManager = supportFragmentManager
        mGankMainFragment = GankMainFragment.newInstance()
        mMZiTuMainFragment = MZiTuMainFragment.newInstance()

        loadFragment(mGankMainFragment as Fragment)
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = mFragmentManager.beginTransaction()
        transaction.add(R.id.fl_content_container, fragment, TAG_FRAGMENT_MAIN)
        transaction.commit()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_gank -> {
                if (mGankMainFragment == null) {
                    mGankMainFragment = GankMainFragment.newInstance()
                }
                if (!mGankMainFragment!!.isAdded) {
                    loadFragment(mGankMainFragment as Fragment)
                    mFragmentManager.beginTransaction().hide(mMZiTuMainFragment as Fragment).commit()

                } else {
                    if (!mGankMainFragment!!.isVisible) {
                        mFragmentManager.beginTransaction().hide(mMZiTuMainFragment as Fragment).show(mGankMainFragment as Fragment).commit()
                    }
                }
            }

            R.id.tv_mzitu -> {
                if (mMZiTuMainFragment == null) {
                    mMZiTuMainFragment = MZiTuMainFragment.newInstance()
                }
                if (!mMZiTuMainFragment!!.isAdded) {
                    loadFragment(mMZiTuMainFragment as Fragment)
                    mFragmentManager.beginTransaction().hide(mGankMainFragment as Fragment).commit()

                } else {
                    if (!mMZiTuMainFragment!!.isVisible) {
                        mFragmentManager.beginTransaction().hide(mGankMainFragment as Fragment).show(mMZiTuMainFragment as Fragment).commit()
                    }
                }
            }
        }

        toggleDrawerLyt()
    }

    override fun onBackPressed() {
        if (mBaseFragment == null || !mBaseFragment!!.onBackPressed()) {
            if (supportFragmentManager.backStackEntryCount == 0) {
                //  如果当前菜单展开，先隐藏菜单
                if (tdrawerlyt.isMenuOpen) {
                    tdrawerlyt.toggleMenu()
                    return
                }
                val secondTime = System.currentTimeMillis()
                if (secondTime - firstTime < 2000) {
                    exitProcess(0)
                } else {
                    ToastUtils.showShort(R.string.sys_exit)
                    firstTime = System.currentTimeMillis()
                }
            } else {
                supportFragmentManager.popBackStack()
            }
        }
    }

    fun isDispatchEvent2DrawerLayout(isDispatch: Boolean) {
        tdrawerlyt.setConsume(isDispatch)
    }

    fun toggleDrawerLyt() {
        tdrawerlyt.toggleMenu()
    }

    override fun setSelectedFragment(selectedFragment: BaseFragment?) {
        mBaseFragment = selectedFragment
    }

    fun startFragment(fragment: Fragment) {
        //  mTDrawerLyt.setConsume(false);
        val transaction = mFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fragment_translate_in, R.anim.fragment_translate_out, R.anim.fragment_translate_in, R.anim.fragment_translate_out)
        transaction.add(R.id.fl_content_container, fragment).addToBackStack(null)
        transaction.commit()
    }
}