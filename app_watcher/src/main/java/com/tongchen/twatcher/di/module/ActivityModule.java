package com.tongchen.twatcher.di.module;

import android.app.Activity;

import com.tongchen.twatcher.di.scope.ActivityScope;
import com.tongchen.twatcher.base.http.IAppApiHelper;
import com.tongchen.sample.ISamplePresenter;
import com.tongchen.sample.SamplePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TongChen at 0:21 on 2018/6/23.
 * <p>
 * Description:该文件实现的功能
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

    @ActivityScope
    @Provides
    ISamplePresenter provideSamplePresenter(IAppApiHelper iAppApiHelper) {
        return new SamplePresenter(iAppApiHelper);
    }
}
