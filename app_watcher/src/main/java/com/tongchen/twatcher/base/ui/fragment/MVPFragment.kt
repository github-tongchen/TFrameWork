package com.tongchen.twatcher.base.ui.fragment

import android.os.Bundle

import com.tongchen.twatcher.TApp
import com.tongchen.twatcher.base.presenter.IMVPPresenter
import com.tongchen.twatcher.base.view.IMVPView
import com.tongchen.twatcher.di.component.DaggerFragmentComponent
import com.tongchen.twatcher.di.component.FragmentComponent
import com.tongchen.twatcher.di.module.FragmentModule

import javax.inject.Inject

/**
 * Created by TongChen at 22:43 on 2018/6/24.
 *
 *
 * Description: MVP Fragment 基类
 */
abstract class MVPFragment<E, V : IMVPView<E>, P : IMVPPresenter<V>> : BaseFragment() {

    @Inject
    lateinit var mPresenter: P

    val fragmentComponent: FragmentComponent
        get() = DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule(this))
                .appComponent(TApp.getAppComponent())
                .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectFragment()
        mPresenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    protected abstract fun injectFragment()

}
