package com.tongchen.twatcher.base.ui

import com.tongchen.twatcher.base.ui.fragment.BaseFragment

/**
 * @author TongChen
 * @date 2019/11/22  10:40
 * <p>
 * Desc: 使Fragment能够处理返回事件
 */
interface BackHandleInterface {

    fun setSelectedFragment(selectedFragment: BaseFragment?)

}