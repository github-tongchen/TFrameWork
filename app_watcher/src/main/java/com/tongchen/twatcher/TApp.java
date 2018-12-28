package com.tongchen.twatcher;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.tongchen.twatcher.di.component.AppComponent;
import com.tongchen.twatcher.di.component.DaggerAppComponent;
import com.tongchen.twatcher.di.module.ApiServiceModule;
import com.tongchen.twatcher.di.module.AppModule;

/**
 * Created by TongChen at 12:43 on 2018/6/20.
 * <p>
 * Description:该文件实现的功能
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
