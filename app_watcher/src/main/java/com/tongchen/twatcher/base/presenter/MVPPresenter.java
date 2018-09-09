package com.tongchen.twatcher.base.presenter;

import com.tongchen.twatcher.base.view.IMVPView;

import java.lang.ref.WeakReference;

/**
 * Created by TongChen at 20:09 on 2018/6/24.
 * <p>
 * Description: MVP Presenter 基类
 */
public abstract class MVPPresenter<V extends IMVPView, E> implements IMVPPresenter<V> {

    //  将View置为弱引用，当view被销毁回收时，依赖于view的对象（即Presenter）也会被回收，而不会造成内存泄漏
    private WeakReference<V> mViewRef;

    @Override
    public void attachView(V view) {
        if (view != null) {
            if (mViewRef == null) {
                mViewRef = new WeakReference<>(view);
            }
        } else {
            throw new NullPointerException("View can not be null when use method attachView() in MVPPresenter");
        }
    }

    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public V getView() {
        if (isViewAttached()) {
            return mViewRef.get();
        }
        return null;
    }

    /**
     * 判断View和Presenter是否已绑定
     *
     * @return View和Presenter是否已绑定
     */
    public boolean isViewAttached() {
        if (mViewRef != null && mViewRef.get() != null) {
            return true;
        } else {
            throw new IllegalStateException("View " + " not attached to Presenter " + this.getClass().getSimpleName());
        }
    }

    public abstract void requestSucceed(E result);

    public abstract void requestFailed(String errorMsg);

    /**
     * 继承BasePresenter的子类名称
     *
     * @return 子类名称
     */
//    public abstract String subClassName();


}
