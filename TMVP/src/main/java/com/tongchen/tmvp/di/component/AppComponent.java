package com.tongchen.tmvp.di.component;

import com.tongchen.tmvp.TApp;
import com.tongchen.tmvp.di.module.AppModule;

import dagger.Component;

/**
 * Created by TongChen at 10:58 on 2018/6/22.
 * <p>
 * Description:该文件实现的功能
 */

@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(TApp app);
}
