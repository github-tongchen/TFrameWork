package com.tongchen.twatcher.di.module;

import android.support.v4.app.Fragment;

import com.tongchen.twatcher.di.scope.FragmentScope;

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
    public Fragment provideFragment() {
        return mFragment;
    }

}
