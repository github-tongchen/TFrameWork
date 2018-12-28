package com.tongchen.twatcher.mzitu.presenter;

import com.tongchen.twatcher.base.presenter.MVPPresenter;
import com.tongchen.twatcher.base.http.IAppApiHelper;
import com.tongchen.twatcher.mzitu.model.entity.MZiTu;
import com.tongchen.twatcher.mzitu.view.IMZiTuCategoryView;

import java.util.List;

/**
 * Created by TongChen at 11:34 on 2018/12/28.
 * <p>
 * Description: 妹子图按分类获取封面列表
 */
public class MZiTuCategoryPresenter extends MVPPresenter<IMZiTuCategoryView, List<MZiTu>> implements IMZiTuCategoryPresenter {

    private IAppApiHelper mIAppApiHelper;
    //  请求的方式：0 refresh;1 more
    private int mMode = -1;
    public static final int MODE_REFRESH = 0;
    public static final int MODE_MORE = 1;

    public MZiTuCategoryPresenter(IAppApiHelper iAppApiHelper) {
        mIAppApiHelper = iAppApiHelper;
    }

    @Override
    public void getMZiTuCoverListByCategory(String category, boolean pullToRefresh) {

    }

    @Override
    public void requestSucceed(List<MZiTu> result) {

    }

    @Override
    public void requestFailed(String errorMsg) {

    }
}
