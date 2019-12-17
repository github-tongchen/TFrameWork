package com.tongchen.twatcher.di.module

import android.content.Context

import com.tongchen.twatcher.base.http.AppApiHelper
import com.tongchen.twatcher.base.http.IAppApiHelper

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by TongChen at 10:32 on 2018/6/22.
 *
 *
 * Description: Dagger2 Application 的Module
 */

@Module
class AppModule(private val mContext: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return mContext
    }

    @Singleton
    @Provides
    fun provideAppApiHelper(appApiHelper: AppApiHelper): IAppApiHelper {  //  AppApiHelper使用构造方法Inject注入
        return appApiHelper
    }
}
