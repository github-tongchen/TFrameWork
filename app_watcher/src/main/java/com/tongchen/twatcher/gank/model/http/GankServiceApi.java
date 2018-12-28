package com.tongchen.twatcher.gank.model.http;

import com.tongchen.twatcher.base.http.Api;
import com.tongchen.twatcher.gank.model.entity.GankData;
import com.tongchen.twatcher.gank.model.entity.GankResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by TongChen at 12:29 on 2018/12/28.
 * <p>
 * Description: 干货集中营 API
 */
public interface GankServiceApi {

    @Headers({"Domain-Name: " + Api.DOMAIN_NAME_GANK})
    @GET("data/{category}/{size}/{page}")
    Observable<GankData<List<GankResult>>> getGankDataByPage(@Path("category") String category, @Path("size") int size, @Path("page") int page);
}
