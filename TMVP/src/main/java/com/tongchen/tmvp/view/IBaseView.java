package com.tongchen.tmvp.view;

/**
 * Created by TongChen at 22:51 on 2018/6/21.
 * <p>
 * Description:该文件实现的功能
 */
public interface IBaseView {

    boolean isActive();

    void showLoading();

    void hideLoading();

    void requestSucceed(String t);

    void requestFailed(String errorMsg);

}
