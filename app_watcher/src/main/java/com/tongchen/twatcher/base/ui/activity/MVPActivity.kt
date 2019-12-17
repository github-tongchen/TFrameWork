package com.tongchen.twatcher.base.ui.activity

import android.os.Bundle

import com.tongchen.twatcher.base.presenter.IMVPPresenter
import com.tongchen.twatcher.base.view.IMVPView

import javax.inject.Inject

/**
 * Created by TongChen at 22:43 on 2018/6/24.
 *
 *
 * Description: MVP Activity 基类
 */
abstract class MVPActivity<E, V : IMVPView<E>, P : IMVPPresenter<V>> : BaseActivity() {

    @Inject
    lateinit var mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectActivity()
        mPresenter.attachView(this as V)

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    protected abstract fun injectActivity()
}


