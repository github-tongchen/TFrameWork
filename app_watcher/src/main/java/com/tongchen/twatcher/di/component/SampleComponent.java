package com.tongchen.twatcher.di.component;

import com.tongchen.twatcher.di.module.SampleModule;
import com.tongchen.twatcher.di.scope.ActivityScope;
import com.tongchen.twatcher.mvp.ui.activity.SampleActivity;

import dagger.Component;

/**
 * Created by TongChen at 3:24 on 2018/9/3.
 * <p>
 * Description:该文件实现的功能
 */

@ActivityScope
@Component(modules = SampleModule.class, dependencies = AppComponent.class)
public interface SampleComponent {

    void inject(SampleActivity activity);
}
