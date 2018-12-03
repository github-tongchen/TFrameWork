package com.tongchen.twatcher.gank.model.http;


import com.tongchen.twatcher.gank.model.entity.Android;
import com.tongchen.twatcher.gank.model.entity.GankData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by TongChen at 17:44 on 2018/7/8.
 * <p>
 * Description:该文件实现的功能
 */
public interface HttpService {

    @GET("data/{category}/{size}/{page}")
    Observable<GankData<List<Android>>> getGankDataByPage(@Path("category") String category, @Path("size") String size, @Path("page") String page);

}
