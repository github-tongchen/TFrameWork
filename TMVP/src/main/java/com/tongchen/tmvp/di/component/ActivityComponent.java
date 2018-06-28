package com.tongchen.tmvp.di.component;

import com.tongchen.tmvp.di.module.ActivityModule;
import com.tongchen.tmvp.di.scope.ActivityScope;
import com.tongchen.tmvp.ui.activity.BaseActivity;

import dagger.Component;

/**
 * Created by TongChen at 0:17 on 2018/6/23.
 * <p>
 * Description:该文件实现的功能
 */

@ActivityScope
@Component(modules = {ActivityModule.class}, dependencies = {AppComponent.class})
public interface ActivityComponent {

    void inject(BaseActivity activity);
}
