package com.tongchen.twatcher.di.module

import android.app.Activity

import com.tongchen.twatcher.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

/**
 * Created by TongChen at 0:21 on 2018/6/23.
 *
 *
 * Description: Dagger2 Activity 的Module
 */
@Module
class ActivityModule(private val mActivity: Activity) {

    @ActivityScope
    @Provides
    fun provideActivity(): Activity {
        return mActivity
    }

}
