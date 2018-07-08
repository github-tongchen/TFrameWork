package com.tongchen.tframework;

import com.tongchen.tmvp.model.MVPModel;

/**
 * Created by TongChen at 14:06 on 2018/7/8.
 * <p>
 * Description:该文件实现的功能
 */
public class SampleModel extends MVPModel<ISamplePresenter> implements ISampleModel {

    @Override
    public void requestUser(String name, String age) {

        ((SamplePresenter) mPresenter).requestSucceed(new User());
    }
}
