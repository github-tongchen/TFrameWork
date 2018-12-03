package com.tongchen.twatcher.di.component;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.tongchen.twatcher.di.module.FragmentModule;
import com.tongchen.twatcher.di.scope.FragmentScope;

import dagger.Component;

/**
 * Created by TongChen at 14:44 on 2018/9/30.
 * <p>
 * Description:该文件实现的功能
 */

@FragmentScope
@Component(modules = {FragmentModule.class}, dependencies = AppComponent.class)
public interface FragmentComponent {

//    Activity provideActivity();

    void inject2Fragment(Fragment fragment);
}
