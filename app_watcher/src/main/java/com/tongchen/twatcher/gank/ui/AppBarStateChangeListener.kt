package com.tongchen.twatcher.gank.ui

import android.support.design.widget.AppBarLayout
import kotlin.math.abs

/**
 * @author TongChen
 * @date 2019/11/22  10:50
 * <p>
 * Desc: 对CollapsingToolbarLayout 的 OnOffsetChangedListener 进行一次封装
 */
abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {

    enum class State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private var mCurrentState = State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        when {
            (verticalOffset == 0) -> {
                if (mCurrentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED)
                }
                mCurrentState = State.EXPANDED
            }

            (abs(verticalOffset) >= appBarLayout.totalScrollRange) -> {
                if (mCurrentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED)
                }
                mCurrentState = State.COLLAPSED
            }

            else -> {
                if (mCurrentState != State.IDLE) {
                    onStateChanged(appBarLayout, State.IDLE)
                }
                mCurrentState = State.IDLE
            }
        }
    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout, state: State)
}