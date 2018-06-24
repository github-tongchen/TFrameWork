package com.tongchen.tmvp.ui.activity;

import android.os.Bundle;

import com.tongchen.tmvp.presenter.BasePresenter;
import com.tongchen.tmvp.presenter.IBasePresenter;
import com.tongchen.tmvp.view.IBaseView;

/**
 * Created by TongChen at 22:43 on 2018/6/24.
 * <p>
 * Description:该文件实现的功能
 */
public abstract class BaseDataActivity<V extends IBaseView, P extends IBasePresenter<V>>
        extends BaseActivity implements IBaseView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
