package com.tongchen.twatcher.base.presenter

import com.tongchen.twatcher.base.view.IMVPView

/**
 * Created by TongChen at 22:51 on 2018/6/21.
 *
 *
 * Description:该文件实现的功能
 *
 * @param <V> 需要绑定的目标View
</V> */
interface IMVPPresenter<V : IMVPView<*>> {


    /**
     * 绑定Presenter和目标View
     *
     * @param view 目标View
     */
    fun attachView(view: V?)

    /**
     * 解绑Presenter和目标View
     */
    fun detachView()

}
