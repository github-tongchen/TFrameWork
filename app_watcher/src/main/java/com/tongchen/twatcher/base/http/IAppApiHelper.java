package com.tongchen.twatcher.base.http;


import com.tongchen.twatcher.gank.model.entity.GankData;
import com.tongchen.twatcher.gank.model.entity.GankResult;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by TongChen at 17:44 on 2018/7/8.
 * <p>
 * Description:该文件实现的功能
 */
public interface IAppApiHelper {

    Observable<GankData<List<GankResult>>> getGankDataByPage(String category, int size, int page);


//    Observable<BaseResult<List<MZiTu>>> listMZiTu(String tag, int page, boolean pullToRefresh);
}
