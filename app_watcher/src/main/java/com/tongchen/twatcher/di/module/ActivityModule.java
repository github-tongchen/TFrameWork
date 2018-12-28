package com.tongchen.twatcher.di.module;

import android.app.Activity;

import com.tongchen.twatcher.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TongChen at 0:21 on 2018/6/23.
 * <p>
 * Description: Dagger2 Activity 的Module
 */
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    Activity provideActivity() {
        return mActivity;
    }

}
