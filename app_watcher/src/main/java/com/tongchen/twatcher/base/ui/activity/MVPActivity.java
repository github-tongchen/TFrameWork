package com.tongchen.twatcher.base.ui.activity;

import android.os.Bundle;

import com.tongchen.twatcher.base.presenter.IMVPPresenter;
import com.tongchen.twatcher.base.view.IMVPView;

import javax.inject.Inject;

/**
 * Created by TongChen at 22:43 on 2018/6/24.
 * <p>
 * Description: MVP Activity 基类
 */
public abstract class MVPActivity<E, V extends IMVPView<E>, P extends IMVPPresenter<V>>
        extends BaseActivity implements IMVPView<E> {

    @Inject
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectActivity();
        mPresenter.attachView((V) this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract void injectActivity();
}
