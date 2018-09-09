package com.tongchen.twatcher.di.module;

import android.app.Activity;

import com.tongchen.twatcher.mvp.model.http.HttpService;
import com.tongchen.twatcher.mvp.presenter.ISamplePresenter;
import com.tongchen.twatcher.mvp.presenter.SamplePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TongChen at 3:20 on 2018/9/3.
 * <p>
 * Description:该文件实现的功能
 */

@Module
public class SampleModule {

    private Activity mActivity;

    public SampleModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    public Activity provideActivity() {
        return mActivity;
    }

    @Provides
    public ISamplePresenter provideSamplePresenter(HttpService httpService) {
        return new SamplePresenter(httpService);
    }

}
