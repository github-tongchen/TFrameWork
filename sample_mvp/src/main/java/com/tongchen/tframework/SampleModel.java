package com.tongchen.tframework;

import android.annotation.SuppressLint;

import com.tongchen.tmvp.model.MVPModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TongChen at 14:06 on 2018/7/8.
 * <p>
 * Description:该文件实现的功能
 */
public class SampleModel extends MVPModel<ISamplePresenter> implements ISampleModel {

    @Inject
    public SampleModel() {
    }

    @SuppressLint("CheckResult")
    @Override
    public void getAndroidDataByPage(String categoryName, String page, String size) {
        mHttpService.getAndroidDataByPage(categoryName, page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        androidGankData -> ((SamplePresenter) mPresenter).requestSucceed(androidGankData),
                        throwable -> ((SamplePresenter) mPresenter).requestFailed(throwable.toString()));

    }

}
