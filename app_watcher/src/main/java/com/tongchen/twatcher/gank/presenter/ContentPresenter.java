package com.tongchen.twatcher.gank.presenter;

import android.annotation.SuppressLint;

import com.tongchen.twatcher.base.presenter.MVPPresenter;
import com.tongchen.twatcher.gank.model.entity.Android;
import com.tongchen.twatcher.gank.model.entity.GankData;
import com.tongchen.twatcher.gank.model.http.HttpService;
import com.tongchen.twatcher.gank.view.IContentView;
import com.tongchen.twatcher.util.LogUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TongChen at 22:53 on 2018/11/1.
 * <p>
 * Description:该文件实现的功能
 */
public class ContentPresenter extends MVPPresenter<IContentView, GankData<List<Android>>> implements IContentPresenter {

    private HttpService mHttpService;

    public ContentPresenter(HttpService httpService) {
        mHttpService = httpService;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getGankDataByPage(String category, String size, String page) {
        mHttpService.getGankDataByPage(category, size, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listGankData -> {
                            if (listGankData.getError()) {
                                requestFailed("error = " + listGankData.getError());
                            } else {
                                requestSucceed(listGankData);
                            }
                        },
                        throwable -> requestFailed(throwable.toString()));
    }


    @Override
    public void requestSucceed(GankData<List<Android>> result) {
        if (getView() == null) {
            return;
        }
        LogUtils.d("ContentPresenter", "requestSucceed" + result.toString());

        getView().hideLoading();
        getView().requestSucceed(result.getResult());
    }

    @Override
    public void requestFailed(String errorMsg) {
        if (getView() == null) {
            return;
        }
        LogUtils.d("ContentPresenter", "requestFailed" + errorMsg.toString());

        getView().hideLoading();
        getView().requestFailed(errorMsg);
    }

}
