package com.tongchen.twatcher.di.component

import com.tongchen.twatcher.di.module.FragmentModule
import com.tongchen.twatcher.di.scope.FragmentScope
import com.tongchen.twatcher.gank.ui.fragment.CategoryFragment
import com.tongchen.twatcher.mzitu.ui.fragment.MZiTuCategoryFragment

import dagger.Component

/**
 * Created by TongChen at 14:44 on 2018/9/30.
 *
 *
 * Description: Dagger2 Fragment 的 Component
 */

@FragmentScope
@Component(modules = [FragmentModule::class], dependencies = [AppComponent::class])
interface FragmentComponent {

    fun inject2Fragment(fragment: CategoryFragment)

    fun inject2Fragment(fragment: MZiTuCategoryFragment)
}
