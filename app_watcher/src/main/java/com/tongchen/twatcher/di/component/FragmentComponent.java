package com.tongchen.twatcher.di.component;

import com.tongchen.twatcher.di.module.FragmentModule;
import com.tongchen.twatcher.di.scope.FragmentScope;
import com.tongchen.twatcher.gank.ui.fragment.CategoryFragment;

import dagger.Component;

/**
 * Created by TongChen at 14:44 on 2018/9/30.
 * <p>
 * Description:该文件实现的功能
 */

@FragmentScope
@Component(modules = {FragmentModule.class}, dependencies = AppComponent.class)
public interface FragmentComponent {

    void inject2Fragment(CategoryFragment fragment);

}
