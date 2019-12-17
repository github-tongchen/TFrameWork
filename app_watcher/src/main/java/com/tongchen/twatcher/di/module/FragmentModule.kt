package com.tongchen.twatcher.di.module

import android.support.v4.app.Fragment

import com.tongchen.twatcher.di.scope.FragmentScope
import com.tongchen.twatcher.base.http.IAppApiHelper
import com.tongchen.twatcher.gank.presenter.CategoryPresenter
import com.tongchen.twatcher.gank.presenter.ICategoryPresenter
import com.tongchen.twatcher.mzitu.presenter.IMZiTuCategoryPresenter
import com.tongchen.twatcher.mzitu.presenter.MZiTuCategoryPresenter

import dagger.Module
import dagger.Provides

/**
 * Created by TongChen at 14:39 on 2018/9/30.
 *
 *
 * Description: Dagger2 Fragment 的Module
 */

@Module
class FragmentModule(private val mFragment: Fragment) {

    @FragmentScope
    @Provides
    fun provideFragment(): Fragment {
        return mFragment
    }

    @FragmentScope
    @Provides
    fun provideCategoryPresenter(iAppApiHelper: IAppApiHelper): ICategoryPresenter {
        return CategoryPresenter(iAppApiHelper)
    }

    @FragmentScope
    @Provides
    fun provideMZiTuCategoryPresenter(iAppApiHelper: IAppApiHelper): IMZiTuCategoryPresenter {
        return MZiTuCategoryPresenter(iAppApiHelper)
    }
}
