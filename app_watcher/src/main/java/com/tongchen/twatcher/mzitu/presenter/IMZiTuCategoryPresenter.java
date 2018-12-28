package com.tongchen.twatcher.mzitu.presenter;

import com.tongchen.twatcher.base.presenter.IMVPPresenter;
import com.tongchen.twatcher.mzitu.view.IMZiTuCategoryView;

/**
 * Created by TongChen at 11:07 on 2018/12/28.
 * <p>
 * Description: 妹子图按分类获取封面列表
 */
public interface IMZiTuCategoryPresenter extends IMVPPresenter<IMZiTuCategoryView> {

    void getMZiTuCoverListByCategory(String category, boolean pullToRefresh);
}
