package com.tongchen.twatcher.mvp.presenter;

import android.annotation.SuppressLint;

import com.tongchen.twatcher.base.presenter.MVPPresenter;
import com.tongchen.twatcher.mvp.model.entity.Android;
import com.tongchen.twatcher.mvp.model.entity.GankData;
import com.tongchen.twatcher.mvp.model.http.HttpService;
import com.tongchen.twatcher.mvp.view.ISampleView;
import com.tongchen.twatcher.util.LogUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TongChen at 11:55 on 2018/7/8.
 * <p>
 * Description:该文件实现的功能
 */
public class SamplePresenter extends MVPPresenter<ISampleView, GankData<List<Android>>> implements ISamplePresenter {

    private HttpService mHttpService;

    //    @Inject
    public SamplePresenter(HttpService httpService) {
        this.mHttpService = httpService;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getAndroidDataByPage(String category, String size, String page) {
        if (getView() != null) {
            getView().showLoading();
        }

        mHttpService.getAndroidDataByPage(category, size, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(androidGankData -> requestSucceed(androidGankData),
                        throwable -> requestFailed(throwable.toString()));

    }

    @Override
    public void requestSucceed(GankData<List<Android>> result) {
        if (getView() == null) {
            return;
        }
        LogUtils.d("SamplePresenter", "requestSucceed" + result.toString());

        getView().hideLoading();
        getView().requestSucceed(result);
    }

    @Override
    public void requestFailed(String errorMsg) {
        if (getView() == null) {
            return;
        }
        LogUtils.d("SamplePresenter", "requestFailed" + errorMsg.toString());


        getView().hideLoading();
        getView().requestFailed(errorMsg);
    }
}
