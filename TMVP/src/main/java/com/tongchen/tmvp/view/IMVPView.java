package com.tongchen.tmvp.view;

/**
 * Created by TongChen at 22:51 on 2018/6/21.
 * <p>
 * Description:该文件实现的功能
 * <p>
 * DM: Data Model 数据类型
 */
public interface IMVPView<DM> {

    void showLoading();

    void hideLoading();

    void requestSucceed(DM t);

    void requestFailed(String errorMsg);

}
