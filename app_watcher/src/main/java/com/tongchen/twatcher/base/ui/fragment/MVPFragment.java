package com.tongchen.twatcher.base.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tongchen.twatcher.base.presenter.IMVPPresenter;
import com.tongchen.twatcher.base.view.IMVPView;

import javax.inject.Inject;

/**
 * Created by TongChen at 22:43 on 2018/6/24.
 * <p>
 * Description: MVP Fragment 基类
 */
public abstract class MVPFragment<E, V extends IMVPView<E>, P extends IMVPPresenter<V>> extends BaseFragment {

    @Inject
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectFragment();
        mPresenter.attachView((V) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract void injectFragment();
}
