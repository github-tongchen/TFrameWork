package com.tongchen.tmvp.presenter;

import android.support.annotation.NonNull;

import com.tongchen.tmvp.model.IBaseModel;
import com.tongchen.tmvp.view.IBaseView;

import java.lang.ref.WeakReference;

/**
 * Created by TongChen at 20:09 on 2018/6/24.
 * <p>
 * Description:该文件实现的功能
 */
public abstract class BasePresenter<V extends IBaseView, M extends IBaseModel> implements IBasePresenter<V> {

    //  将View置为弱引用，当view被销毁回收时，依赖于view的对象（即Presenter）也会被回收，而不会造成内存泄漏
    private WeakReference<V> mView;


    @Override
    public void attachView(V view) {
        if (view != null) {
            mView = new WeakReference<>(view);
        } else {
            throw new NullPointerException("View can not be null when in attachView() in BasePresenter");
        }
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView.clear();
            mView = null;
        }
    }

    @Override
    public boolean isViewAttached() {
        if (mView != null && mView.get() != null) {
            return true;
        } else {
            throw new IllegalStateException("View " + " not attached to Presenter " + this.getClass().getSimpleName());
        }
    }

    /**
     * 继承BasePresenter的子类名称
     *
     * @return 子类名称
     */
//    public abstract String subClassName();


}