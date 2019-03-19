package com.tongchen.twatcher.base.http;

import com.tongchen.twatcher.gank.model.entity.GankData;
import com.tongchen.twatcher.gank.model.entity.GankResult;
import com.tongchen.twatcher.gank.model.http.GankServiceApi;
import com.tongchen.twatcher.mzitu.model.MZiTuServiceApi;
import com.tongchen.twatcher.mzitu.model.entity.BaseResult;
import com.tongchen.twatcher.mzitu.model.entity.MZiTu;
import com.tongchen.twatcher.mzitu.model.parse.MZiTuParser;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by TongChen at 14:56 on 2018/12/28.
 * <p>
 * Description: 全App的 API 帮助实现类
 */
public class AppApiHelper implements IAppApiHelper {

    private GankServiceApi mGankServiceApi;

    private MZiTuServiceApi mMZiTuServiceApi;

    @Inject
    public AppApiHelper(GankServiceApi gankServiceApi, MZiTuServiceApi mZiTuServiceApi) {
        mGankServiceApi = gankServiceApi;
        mMZiTuServiceApi = mZiTuServiceApi;
    }

    @Override
    public Observable<GankData<List<GankResult>>> getGankDataByPage(String category, int size, int page) {
        return mGankServiceApi.getGankDataByPage(category, size, page);
    }

    @Override
    public Observable<BaseResult<List<MZiTu>>> listMZiTu(String tag, int page, boolean pullToRefresh) {
        switch (tag) {
            case "index":
                return action(mMZiTuServiceApi.mZiTuIndex(page), tag, page, pullToRefresh);
            case "hot":
                return action(mMZiTuServiceApi.mZiTuHot(page), tag, page, pullToRefresh);
            case "best":
                return action(mMZiTuServiceApi.mZiTuBest(page), tag, page, pullToRefresh);
            case "japan":
                return action(mMZiTuServiceApi.mZiTuJapan(page), tag, page, pullToRefresh);
            case "taiwan":
                return action(mMZiTuServiceApi.mZiTuTaiwan(page), tag, page, pullToRefresh);
            case "xinggan":
                return action(mMZiTuServiceApi.mZiTuSexy(page), tag, page, pullToRefresh);
            case "mm":
                return action(mMZiTuServiceApi.mZiTuMm(page), tag, page, pullToRefresh);
            default:
                return null;
        }
    }

    private Observable<BaseResult<List<MZiTu>>> action(Observable<String> stringObservable, String tag, final int page, final boolean pullToRefresh) {

        return stringObservable.map(new Function<String, BaseResult<List<MZiTu>>>() {
            @Override
            public BaseResult<List<MZiTu>> apply(String s) throws Exception {
                return MZiTuParser.parseAlbumCoverListByCategory(s, page);
            }
        });
    }
}
