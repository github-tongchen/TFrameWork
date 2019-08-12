package com.tongchen.twatcher.mzitu.model;

import com.tongchen.twatcher.base.http.Api;
import com.tongchen.twatcher.base.http.ResponseFormat;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


public interface MZiTuServiceApi {


    @Headers({"Referer: " + Api.DOMAIN_MZITU,
            "Domain-Name: " + Api.DOMAIN_NAME_MZITU})
    @GET("page/{page}/")
    @ResponseFormat(ResponseFormat.STRING)
    Observable<String> mZiTuIndex(@Path("page") int page);

    @Headers({"Referer: " + Api.DOMAIN_MZITU,
            "Domain-Name: " + Api.DOMAIN_NAME_MZITU})
    @GET("hot/page/{page}/")
    @ResponseFormat(ResponseFormat.STRING)
    Observable<String> mZiTuHot(@Path("page") int page);

    @Headers({"Referer: " + Api.DOMAIN_MZITU,
            "Domain-Name: " + Api.DOMAIN_NAME_MZITU})
    @GET("best/page/{page}/")
    @ResponseFormat(ResponseFormat.STRING)
    Observable<String> mZiTuBest(@Path("page") int page);

    @Headers({"Referer: " + Api.DOMAIN_MZITU,
            "Domain-Name: " + Api.DOMAIN_NAME_MZITU})
    @GET("xinggan/page/{page}/")
    @ResponseFormat(ResponseFormat.STRING)
    Observable<String> mZiTuSexy(@Path("page") int page);

    @Headers({"Referer: " + Api.DOMAIN_MZITU,
            "Domain-Name: " + Api.DOMAIN_NAME_MZITU})
    @GET("japan/page/{page}/")
    @ResponseFormat(ResponseFormat.STRING)
    Observable<String> mZiTuJapan(@Path("page") int page);

    @Headers({"Referer: " + Api.DOMAIN_MZITU,
            "Domain-Name: " + Api.DOMAIN_NAME_MZITU})
    @GET("taiwan/page/{page}/")
    @ResponseFormat(ResponseFormat.STRING)
    Observable<String> mZiTuTaiwan(@Path("page") int page);

    @Headers({"Referer: " + Api.DOMAIN_MZITU,
            "Domain-Name: " + Api.DOMAIN_NAME_MZITU})
    @GET("mm/page/{page}/")
    @ResponseFormat(ResponseFormat.STRING)
    Observable<String> mZiTuMm(@Path("page") int page);

    @Headers({"Referer: " + Api.DOMAIN_MZITU,
            "Domain-Name: " + Api.DOMAIN_NAME_MZITU})
    @GET("{id}")
    @ResponseFormat(ResponseFormat.STRING)
    Observable<String> mZiTuImageList(@Path("id") int id);
}
