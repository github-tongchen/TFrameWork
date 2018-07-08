package com.tongchen.tmvp.model;

import com.tongchen.tmvp.HttpService;
import com.tongchen.tmvp.presenter.IMVPPresenter;

import javax.inject.Inject;

/**
 * Created by TongChen at 22:59 on 2018/6/24.
 * <p>
 * Description: MVP Model基类，处理网络请求、数据库读写等数据操作
 */
public class MVPModel<P extends IMVPPresenter> implements IMVPModel {

    @Inject
    protected P mPresenter;

    @Inject
    protected HttpService mHttpService;

}
