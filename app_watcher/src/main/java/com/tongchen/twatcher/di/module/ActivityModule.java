package com.tongchen.twatcher.di.module;

import android.app.Activity;

import com.tongchen.twatcher.di.scope.ActivityScope;
import com.tongchen.twatcher.gank.model.http.HttpService;
import com.tongchen.twatcher.gank.presenter.ISamplePresenter;
import com.tongchen.twatcher.gank.presenter.SamplePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TongChen at 0:21 on 2018/6/23.
 * <p>
 * Description:该文件实现的功能
 */
@Module
public class ActivityModule {

    public Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public Activity provideActivity() {
        return mActivity;
    }

    @Provides
    public ISamplePresenter provideSamplePresenter(HttpService httpService) {
        return new SamplePresenter(httpService);
    }
}
