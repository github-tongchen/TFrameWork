package com.tongchen.tmvp.model;

import com.tongchen.tmvp.presenter.IMVPPresenter;

/**
 * Created by TongChen at 22:59 on 2018/6/24.
 * <p>
 * Description:该文件实现的功能
 */
public class MVPModel<P extends IMVPPresenter> implements IMVPModel {

    private P mPresenter;


    @Override
    public void requestFailed(String errorMsg) {

    }
}
