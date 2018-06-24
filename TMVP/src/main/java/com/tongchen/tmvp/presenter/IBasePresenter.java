package com.tongchen.tmvp.presenter;

import com.tongchen.tmvp.model.IBaseModel;
import com.tongchen.tmvp.view.IBaseView;

/**
 * Created by TongChen at 22:51 on 2018/6/21.
 * <p>
 * Description:该文件实现的功能
 *
 * @param <V> 需要绑定的目标View
 */
public interface IBasePresenter<V extends IBaseView> {


    /**
     * 绑定Presenter和目标View
     *
     * @param view 目标View
     */
    void attachView(V view);

    /**
     * 解绑Presenter和目标View
     */
    void detachView();

    /**
     * 判断View和Presenter是否已绑定
     *
     * @return View和Presenter是否已绑定
     */
    boolean isViewAttached();

    /**
     * @return 继承IBasePresenter的子类名称
     */
}
