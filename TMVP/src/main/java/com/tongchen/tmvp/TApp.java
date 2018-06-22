package com.tongchen.tmvp;

import android.app.Application;

import com.tongchen.tmvp.di.component.DaggerAppComponent;
import com.tongchen.tmvp.di.module.AppModule;

/**
 * Created by TongChen at 12:43 on 2018/6/20.
 * <p>
 * Description:该文件实现的功能
 */
public class TApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    public void init() {
        DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build()
                .inject(this);
    }
}
