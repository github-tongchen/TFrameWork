package com.tongchen.twatcher.gank.presenter;

import com.tongchen.twatcher.base.presenter.IMVPPresenter;
import com.tongchen.twatcher.gank.view.IPicView;

/**
 * Created by TongChen at 18:33 on 2018/12/25.
 * <p>
 * Description: 分页浏览图片
 */
public interface IPicPresenter extends IMVPPresenter<IPicView> {

    void getGankData4Pic(int size, int page);
}
