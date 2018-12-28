package com.tongchen.twatcher.di.component;

import com.tongchen.twatcher.di.module.ActivityModule;
import com.tongchen.twatcher.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by TongChen at 0:17 on 2018/6/23.
 * <p>
 * Description: Dagger2 Activity 的 Component
 */
@ActivityScope
@Component(modules = {ActivityModule.class}, dependencies = {AppComponent.class})
public interface ActivityComponent {

}

