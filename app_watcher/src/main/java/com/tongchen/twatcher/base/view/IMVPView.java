package com.tongchen.twatcher.base.view;

/**
 * Created by TongChen at 22:51 on 2018/6/21.
 * <p>
 * Description:该文件实现的功能
 * <p>
 * E: Data Model 数据类型
 */
public interface IMVPView<E> {

    void showLoading();

    void hideLoading();

    void requestSucceed(E result);

    void requestFailed(String errorMsg);

}
