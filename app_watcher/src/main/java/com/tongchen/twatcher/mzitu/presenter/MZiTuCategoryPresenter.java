package com.tongchen.twatcher.mzitu.presenter;

import android.annotation.SuppressLint;

import com.tongchen.twatcher.base.http.IAppApiHelper;
import com.tongchen.twatcher.base.presenter.MVPPresenter;
import com.tongchen.twatcher.mzitu.model.entity.MZiTu;
import com.tongchen.twatcher.mzitu.view.IMZiTuCategoryView;
import com.tongchen.twatcher.util.LogUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    private int page = 1;
    private int totalPage = 1;

    public MZiTuCategoryPresenter(IAppApiHelper iAppApiHelper) {
        mIAppApiHelper = iAppApiHelper;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getMZiTuCoverListByCategory(String category, boolean pullToRefresh) {
        if (pullToRefresh) {
            page = 1;
        }
        mIAppApiHelper.listMZiTu(category, page, pullToRefresh)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listBaseResult -> {
                            requestSucceed(listBaseResult.getData());
                        },
                        throwable -> requestFailed(throwable.toString()));
    }

    @Override
    public void requestSucceed(List<MZiTu> result) {
        if (getView() == null) {
            return;
        }
        LogUtils.d("MZiTuCategoryPresenter", "requestSucceed:" + result.toString());
        mMode=0;
        switch (mMode) {
            case MODE_REFRESH:
                getView().hideLoading();
                getView().refreshSucceed(result);
                break;
            case MODE_MORE:
                getView().loadMoreSucceed(result);
                break;
            default:
                break;
        }
    }

    @Override
    public void requestFailed(String errorMsg) {
        if (getView() == null) {
            return;
        }
        LogUtils.d("MZiTuCategoryPresenter", "requestFailed:" + errorMsg);

        switch (mMode) {
            case MODE_REFRESH:
                getView().hideLoading();
                getView().refreshFailed(errorMsg);
                break;
            case MODE_MORE:
                getView().loadMoreFailed(errorMsg);
                break;
            default:
                break;
        }
    }
}
