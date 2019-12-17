package com.tongchen.twatcher.base.presenter

import com.tongchen.twatcher.base.view.IMVPView

import java.lang.ref.WeakReference

/**
 * Created by TongChen at 20:09 on 2018/6/24.
 *
 *
 * Description: MVP Presenter 基类
 */
abstract class MVPPresenter<V : IMVPView<*>, E> : IMVPPresenter<V> {

    //  将View置为弱引用，当view被销毁回收时，依赖于view的对象（即Presenter）也会被回收，而不会造成内存泄漏
    private var mViewRef: WeakReference<V>? = null

    val view: V?
        get() = if (isViewAttached) {
            mViewRef!!.get()
        } else null

    /**
     * 判断View和Presenter是否已绑定
     *
     * @return View和Presenter是否已绑定
     */
    val isViewAttached: Boolean
        get() = if (mViewRef != null && mViewRef!!.get() != null) {
            true
        } else {
            throw IllegalStateException("View " + " not attached to Presenter " + this.javaClass.simpleName)
        }

    override fun attachView(view: V?) {
        if (view != null) {
            if (mViewRef == null) {
                mViewRef = WeakReference(view)
            }
        } else {
            throw NullPointerException("View can not be null when use method attachView() in MVPPresenter")
        }
    }

    override fun detachView() {
        if (mViewRef != null) {
            mViewRef!!.clear()
            mViewRef = null
        }
    }

    abstract fun requestSucceed(result: E?)

    abstract fun requestFailed(errorMsg: String)

    /**
     * 继承BasePresenter的子类名称
     *
     * @return 子类名称
     */
    //    public abstract String subClassName();


}
