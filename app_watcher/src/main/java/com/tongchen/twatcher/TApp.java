package com.tongchen.twatcher;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.tongchen.twatcher.base.http.Api;
import com.tongchen.twatcher.di.component.AppComponent;
import com.tongchen.twatcher.di.component.DaggerAppComponent;
import com.tongchen.twatcher.di.module.ApiServiceModule;
import com.tongchen.twatcher.di.module.AppModule;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * Created by TongChen at 12:43 on 2018/6/20.
 * <p>
 * Description:
 */
public class TApp extends Application {

    public static TApp mInstance = null;
    private static AppComponent mAppComponent = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //  初始化DBFlow数据库
        FlowManager.init(this);
        //  RetrofitUrlManager 高级模式
        RetrofitUrlManager.getInstance().startAdvancedModel(Api.DOMAIN_GANK);

        //  添加需要动态切换的BaseUrl
        RetrofitUrlManager.getInstance().putDomain(Api.DOMAIN_NAME_GANK, Api.DOMAIN_GANK);
        RetrofitUrlManager.getInstance().putDomain(Api.DOMAIN_NAME_MZITU, Api.DOMAIN_MZITU);
    }

    public static AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(mInstance))
                    .apiServiceModule(new ApiServiceModule())
                    .build();
        }
        return mAppComponent;
    }
}
