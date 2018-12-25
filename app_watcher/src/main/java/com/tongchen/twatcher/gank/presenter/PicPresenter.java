package com.tongchen.twatcher.gank.presenter;

import android.annotation.SuppressLint;

import com.tongchen.twatcher.base.presenter.MVPPresenter;
import com.tongchen.twatcher.gank.model.entity.GankData;
import com.tongchen.twatcher.gank.model.entity.GankResult;
import com.tongchen.twatcher.gank.model.http.HttpService;
import com.tongchen.twatcher.gank.view.IPicView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TongChen at 18:38 on 2018/12/25.
 * <p>
 * Description: 描述该文件实现的功能
 */
public class PicPresenter extends MVPPresenter<IPicView, GankData<List<GankResult>>> implements IPicPresenter {

    private HttpService mHttpService;

    public PicPresenter(HttpService httpService) {
        mHttpService = httpService;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getGankData4Pic(int size, int page) {
        mHttpService.getGankData4Pic(size, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(listGankData -> {
                    if (listGankData.getError()) {
                        requestFailed("error = " + listGankData.getError());
                    } else {
                        requestSucceed(listGankData);
                    }
                }, throwable -> requestFailed(throwable.toString()));
    }

    @Override
    public void requestSucceed(GankData<List<GankResult>> result) {
        if (getView() == null) {
            return;
        }
        getView().hideLoading();
        getView().refreshSucceed(result.getResult());
    }

    @Override
    public void requestFailed(String errorMsg) {
        if (getView() == null) {
            return;
        }
        getView().refreshFailed(errorMsg);
    }

}
