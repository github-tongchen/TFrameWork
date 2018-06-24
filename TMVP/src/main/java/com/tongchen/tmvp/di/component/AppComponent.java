package com.tongchen.tmvp.di.component;

import android.content.Context;

import com.tongchen.tmvp.TApp;
import com.tongchen.tmvp.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by TongChen at 10:58 on 2018/6/22.
 * <p>
 * Description:该文件实现的功能
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    Context provideApplicationContext();
}
