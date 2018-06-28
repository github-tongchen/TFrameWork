package com.tongchen.tmvp.ui.activity;

import android.os.Bundle;

import com.tongchen.tmvp.presenter.IBasePresenter;
import com.tongchen.tmvp.view.IBaseView;

/**
 * Created by TongChen at 22:43 on 2018/6/24.
 * <p>
 * Description:该文件实现的功能
 */
public abstract class MVPBaseActivity<V extends IBaseView, P extends IBasePresenter<V>>
        extends BaseActivity implements IBaseView {

    private P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
