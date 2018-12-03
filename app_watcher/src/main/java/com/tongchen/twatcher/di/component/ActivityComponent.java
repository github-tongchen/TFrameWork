package com.tongchen.twatcher.di.component;

import com.tongchen.twatcher.di.module.ActivityModule;
import com.tongchen.twatcher.di.scope.ActivityScope;
import com.tongchen.twatcher.gank.ui.activity.SampleActivity;

import dagger.Component;

/**
 * Created by TongChen at 0:17 on 2018/6/23.
 * <p>
 * Description:该文件实现的功能
 */
@ActivityScope
@Component(modules = {ActivityModule.class}, dependencies = {AppComponent.class})
public interface ActivityComponent {

    void inject(SampleActivity activity);
}

