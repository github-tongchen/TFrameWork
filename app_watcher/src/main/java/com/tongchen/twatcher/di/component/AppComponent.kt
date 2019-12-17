package com.tongchen.twatcher.di.component

import android.content.Context

import com.tongchen.twatcher.di.module.AppModule
import com.tongchen.twatcher.di.module.ApiServiceModule
import com.tongchen.twatcher.base.http.IAppApiHelper

import javax.inject.Singleton

import dagger.Component


/**
 * Created by TongChen at 10:58 on 2018/6/22.
 *
 *
 * Description: Dagger2 Application 的 Component
 */

@Singleton
@Component(modules = [AppModule::class, ApiServiceModule::class])
interface AppComponent {

    fun provideApplicationContext(): Context

    fun provideAppApiHelper(): IAppApiHelper

}
