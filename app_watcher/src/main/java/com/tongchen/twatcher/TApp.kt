package com.tongchen.twatcher

import android.app.Application
import android.content.Context
import com.raizlabs.android.dbflow.config.FlowManager
import com.tongchen.twatcher.base.http.Api
import com.tongchen.twatcher.di.component.AppComponent
import com.tongchen.twatcher.di.component.DaggerAppComponent
import com.tongchen.twatcher.di.module.ApiServiceModule
import com.tongchen.twatcher.di.module.AppModule
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

/**
 * @author TongChen
 * @date 2019/12/16  15:17
 * <p>
 * Desc:
 */
class TApp : Application() {

    override fun onCreate() {
        super.onCreate()
        mInstance = this

        //  初始化DBFlow数据库
        FlowManager.init(this)
        //  RetrofitUrlManager 高级模式
        RetrofitUrlManager.getInstance().startAdvancedModel(Api.DOMAIN_GANK)
        //  添加需要动态切换的BaseUrl
        RetrofitUrlManager.getInstance().putDomain(Api.DOMAIN_NAME_GANK, Api.DOMAIN_GANK)
        RetrofitUrlManager.getInstance().putDomain(Api.DOMAIN_NAME_MZITU, Api.DOMAIN_MZITU)
    }

    companion object {
        @JvmField
        var mInstance: TApp? = null

        private var mAppComponent: AppComponent? = null

        @JvmStatic
        fun getAppComponent(): AppComponent? {
            if (mAppComponent == null) {
                mAppComponent = DaggerAppComponent.builder()
                        .appModule(AppModule(mInstance as Context))
                        .apiServiceModule(ApiServiceModule())
                        .build()
            }
            return mAppComponent
        }
    }

}