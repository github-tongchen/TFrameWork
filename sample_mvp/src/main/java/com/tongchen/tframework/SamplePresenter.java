package com.tongchen.tframework;

import com.tongchen.tmvp.presenter.MVPPresenter;

/**
 * Created by TongChen at 11:55 on 2018/7/8.
 * <p>
 * Description:该文件实现的功能
 */
public class SamplePresenter extends MVPPresenter<ISampleView, User,ISampleModel> implements ISamplePresenter {


    @Override
    public void requestSample(String name, String age) {
        mModel.requestUser(name,age);

    }

    @Override
    public void requestSucceed(User result) {
        if (getView() == null) {
            return;
        }
        getView().requestSucceed(result);
    }
}
