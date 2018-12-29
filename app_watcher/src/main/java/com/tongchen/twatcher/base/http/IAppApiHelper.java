package com.tongchen.twatcher.base.http;


import com.tongchen.twatcher.gank.model.entity.GankData;
import com.tongchen.twatcher.gank.model.entity.GankResult;
import com.tongchen.twatcher.mzitu.model.entity.BaseResult;
import com.tongchen.twatcher.mzitu.model.entity.MZiTu;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by TongChen at 17:44 on 2018/7/8.
 * <p>
 * Description: 全App的 API 帮助类
 */
public interface IAppApiHelper {

    /**
     * @param category
     * @param size
     * @param page
     * @return
     */
    Observable<GankData<List<GankResult>>> getGankDataByPage(String category, int size, int page);

    /**
     * @param tag
     * @param page
     * @param pullToRefresh
     * @return
     */
    Observable<BaseResult<List<MZiTu>>> listMZiTu(String tag, int page, boolean pullToRefresh);
}
