package com.tongchen.twatcher.gank.presenter;

import android.annotation.SuppressLint;

import com.tongchen.twatcher.base.presenter.MVPPresenter;
import com.tongchen.twatcher.gank.model.entity.GankResult;
import com.tongchen.twatcher.gank.model.entity.GankData;
import com.tongchen.twatcher.gank.model.http.HttpService;
import com.tongchen.twatcher.gank.view.ISampleView;
import com.tongchen.twatcher.util.LogUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TongChen at 11:55 on 2018/7/8.
 * <p>
 * Description:该文件实现的功能
 */
public class SamplePresenter extends MVPPresenter<ISampleView, GankData<List<GankResult>>> implements ISamplePresenter {

    private HttpService mHttpService;

    public SamplePresenter(HttpService httpService) {
        this.mHttpService = httpService;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getAndroidDataByPage(String category, int size, int page) {
        if (getView() != null) {
            getView().showLoading();
        }

        mHttpService.getGankDataByPage(category, size, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankData<List<GankResult>>>() {
                    @Override
                    public void accept(GankData<List<GankResult>> listGankData) throws Exception {
                        requestSucceed(listGankData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable.toString());
                    }
                });

    }

    @Override
    public void requestSucceed(GankData<List<GankResult>> result) {
        if (getView() == null) {
            return;
        }
        LogUtils.d("SamplePresenter", "requestSucceed" + result.toString());

        getView().hideLoading();
        getView().refreshSucceed(result);
    }

    @Override
    public void requestFailed(String errorMsg) {
        if (getView() == null) {
            return;
        }
        LogUtils.d("SamplePresenter", "requestFailed" + errorMsg);


        getView().hideLoading();
        getView().refreshFailed(errorMsg);
    }
}
