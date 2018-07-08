package com.tongchen.tmvp.ui.activity;

import android.os.Bundle;

import com.tongchen.tmvp.presenter.IMVPPresenter;
import com.tongchen.tmvp.view.IMVPView;

import javax.inject.Inject;

/**
 * Created by TongChen at 22:43 on 2018/6/24.
 * <p>
 * Description: MVP Activity 基类
 */
public abstract class MVPActivity<DM, V extends IMVPView<DM>, P extends IMVPPresenter<V>>
        extends BaseActivity implements IMVPView<DM> {

    @Inject
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

}
