package com.tongchen.twatcher.gank.presenter;

import android.annotation.SuppressLint;

import com.tongchen.twatcher.base.presenter.MVPPresenter;
import com.tongchen.twatcher.gank.model.entity.GankResult;
import com.tongchen.twatcher.gank.model.entity.GankData;
import com.tongchen.twatcher.base.http.IAppApiHelper;
import com.tongchen.twatcher.gank.view.ICategoryView;
import com.tongchen.twatcher.util.LogUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TongChen at 22:53 on 2018/11/1.
 * <p>
 * Description:该文件实现的功能
 */
public class CategoryPresenter extends MVPPresenter<ICategoryView, GankData<List<GankResult>>> implements ICategoryPresenter {

    private IAppApiHelper mIAppApiHelper;
    //  请求的方式：0 refresh;1 more
    private int mMode = -1;
    public static final int MODE_REFRESH = 0;
    public static final int MODE_MORE = 1;

    public CategoryPresenter(IAppApiHelper iAppApiHelper) {
        mIAppApiHelper = iAppApiHelper;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getGankDataByPage(String category, int size, int page, int mode) {
        mMode = mode;
        mIAppApiHelper.getGankDataByPage(category, size, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listGankData -> {
                            if (listGankData.getError()) {
                                requestFailed("error = " + listGankData.getError());
                            } else {
                                requestSucceed(listGankData);
                            }
                        },
                        throwable -> requestFailed(throwable.toString()));
    }


    @Override
    public void requestSucceed(GankData<List<GankResult>> result) {
        if (getView() == null) {
            return;
        }
        LogUtils.d("CategoryPresenter", "requestSucceed" + result.toString());

        switch (mMode) {
            case MODE_REFRESH:
                getView().hideLoading();
                getView().refreshSucceed(result.getResult());
                break;
            case MODE_MORE:
                getView().loadMoreSucceed(result.getResult());
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
        LogUtils.d("CategoryPresenter", "requestFailed" + errorMsg);

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
