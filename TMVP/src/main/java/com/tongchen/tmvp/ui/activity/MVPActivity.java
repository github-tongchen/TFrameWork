package com.tongchen.tmvp.ui.activity;

import android.os.Bundle;

import com.tongchen.tmvp.presenter.IMVPPresenter;
import com.tongchen.tmvp.view.IMVPView;

/**
 * Created by TongChen at 22:43 on 2018/6/24.
 * <p>
 * Description:该文件实现的功能
 */
public abstract class MVPActivity<V extends IMVPView, P extends IMVPPresenter<V>> extends BaseActivity implements IMVPView {

    private P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void requestSucceed(String t) {

    }

    @Override
    public void requestFailed(String errorMsg) {

    }
}
