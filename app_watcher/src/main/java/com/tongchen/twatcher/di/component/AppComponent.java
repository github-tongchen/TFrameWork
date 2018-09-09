package com.tongchen.twatcher.di.component;

import android.content.Context;

import com.tongchen.twatcher.mvp.model.http.HttpService;
import com.tongchen.twatcher.di.module.AppModule;
import com.tongchen.twatcher.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by TongChen at 10:58 on 2018/6/22.
 * <p>
 * Description:该文件实现的功能
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    Context provideApplicationContext();

    HttpService provideHttpService();
}
