package com.tongchen.twatcher.di.module;

import android.support.v4.app.Fragment;

import com.tongchen.twatcher.di.scope.FragmentScope;
import com.tongchen.twatcher.base.http.IAppApiHelper;
import com.tongchen.twatcher.gank.presenter.CategoryPresenter;
import com.tongchen.twatcher.gank.presenter.ICategoryPresenter;
import com.tongchen.twatcher.mzitu.presenter.IMZiTuCategoryPresenter;
import com.tongchen.twatcher.mzitu.presenter.MZiTuCategoryPresenter;

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
    ICategoryPresenter provideCategoryPresenter(IAppApiHelper iAppApiHelper) {
        return new CategoryPresenter(iAppApiHelper);
    }

    @FragmentScope
    @Provides
    IMZiTuCategoryPresenter provideMZiTuCategoryPresenter(IAppApiHelper iAppApiHelper) {
        return new MZiTuCategoryPresenter(iAppApiHelper);
    }
}
