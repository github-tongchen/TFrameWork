package com.tongchen.twatcher.gank.model.http

import com.tongchen.twatcher.base.http.Api
import com.tongchen.twatcher.base.http.ResponseFormat
import com.tongchen.twatcher.gank.model.entity.GankData
import com.tongchen.twatcher.gank.model.entity.GankResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * @author TongChen
 * @date 2019/11/21  17:28
 * <p>
 * Desc: 干货集中营 API
 */
interface GankServiceApi {

    @Headers("Domain-Name: " + Api.DOMAIN_NAME_GANK)
    @GET("data/{category}/{size}/{page}")
    @ResponseFormat(ResponseFormat.JSON)
    fun getGankDataByPage(@Path("category") category: String, @Path("size") size: Int, @Path("page") page: Int): Observable<GankData<MutableList<GankResult>>>

}