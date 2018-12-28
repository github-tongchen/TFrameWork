package com.tongchen.twatcher.mzitu.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


public interface MZiTuServiceApi {

    String APP_MZITU_DOMAIN = "http://www.mzitu.com/";
    String MZITU_DOMAIN_NAME = "mei_zi_tu_domain_name";

    @Headers({"Referer: " + APP_MZITU_DOMAIN,
            "Domain-Name: " + MZITU_DOMAIN_NAME})
    @GET("page/{page}/")
    Observable<String> mZiTuIndex(@Path("page") int page);

    @Headers({"Referer: " + APP_MZITU_DOMAIN,
            "Domain-Name: " + MZITU_DOMAIN_NAME})
    @GET("hot/page/{page}/")
    Observable<String> mZiTuHot(@Path("page") int page);

    @Headers({"Referer: " + APP_MZITU_DOMAIN,
            "Domain-Name: " + MZITU_DOMAIN_NAME})
    @GET("best/page/{page}/")
    Observable<String> mZiTuBest(@Path("page") int page);

    @Headers({"Referer: " + APP_MZITU_DOMAIN,
            "Domain-Name: " + MZITU_DOMAIN_NAME})
    @GET("xinggan/page/{page}/")
    Observable<String> mZiTuSexy(@Path("page") int page);

    @Headers({"Referer: " + APP_MZITU_DOMAIN,
            "Domain-Name: " + MZITU_DOMAIN_NAME})
    @GET("japan/page/{page}/")
    Observable<String> mZiTuJapan(@Path("page") int page);

    @Headers({"Referer: " + APP_MZITU_DOMAIN,
            "Domain-Name: " + MZITU_DOMAIN_NAME})
    @GET("taiwan/page/{page}/")
    Observable<String> mZiTuTaiwan(@Path("page") int page);

    @Headers({"Referer: " + APP_MZITU_DOMAIN,
            "Domain-Name: " + MZITU_DOMAIN_NAME})
    @GET("mm/page/{page}/")
    Observable<String> mZiTuMm(@Path("page") int page);

    @Headers({"Referer: " + APP_MZITU_DOMAIN,
            "Domain-Name: " + MZITU_DOMAIN_NAME})
    @GET("{id}")
    Observable<String> mZiTuImageList(@Path("id") int id);
}
