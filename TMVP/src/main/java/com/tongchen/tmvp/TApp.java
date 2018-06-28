package com.tongchen.tmvp;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.tongchen.tmvp.di.component.AppComponent;
import com.tongchen.tmvp.di.component.DaggerAppComponent;
import com.tongchen.tmvp.di.module.AppModule;

/**
 * Created by TongChen at 12:43 on 2018/6/20.
 * <p>
 * Description:该文件实现的功能
 */
public class TApp extends Application {

    private TApp mInstance = null;
    private AppComponent mAppComponent = null;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        FlowManager.init(this);

    }

    public AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(mInstance))
                    .build();
        }
        return mAppComponent;
    }
}
