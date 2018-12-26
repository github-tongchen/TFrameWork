package com.tongchen.twatcher.di.module;

import android.support.v4.app.Fragment;

import com.tongchen.twatcher.di.scope.FragmentScope;
import com.tongchen.twatcher.gank.model.http.HttpService;
import com.tongchen.twatcher.gank.presenter.CategoryPresenter;
import com.tongchen.twatcher.gank.presenter.ICategoryPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TongChen at 14:39 on 2018/9/30.
 * <p>
 * Description:该文件实现的功能
 */

@Module
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    @FragmentScope
    @Provides
    Fragment provideFragment() {
        return mFragment;
    }

    @FragmentScope
    @Provides
    ICategoryPresenter provideCategoryProvider(HttpService httpService) {
        return new CategoryPresenter(httpService);
    }

}
