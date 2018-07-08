package com.tongchen.tframework;

import com.tongchen.tmvp.Android;
import com.tongchen.tmvp.GankData;
import com.tongchen.tmvp.presenter.MVPPresenter;

import javax.inject.Inject;

/**
 * Created by TongChen at 11:55 on 2018/7/8.
 * <p>
 * Description:该文件实现的功能
 */
public class SamplePresenter extends MVPPresenter<ISampleView, GankData<Android>, ISampleModel> implements ISamplePresenter {


    @Inject
    public SamplePresenter() {

    }

    @Override
    public void getAndroidDataByPage(String categoryName, String page, String size) {
        mModel.getAndroidDataByPage(categoryName, page, size);

        if (getView() != null) {
            getView().showLoading();
        }

    }

    @Override
    public void requestSucceed(GankData<Android> result) {
        if (getView() == null) {
            return;
        }
        getView().requestSucceed(result);
    }

    @Override
    public void requestFailed(String errorMsg) {
        if (getView() == null) {
            return;
        }
        getView().hideLoading();
        getView().requestFailed(errorMsg);
    }
}
