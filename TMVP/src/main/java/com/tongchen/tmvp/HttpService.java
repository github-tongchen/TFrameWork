package com.tongchen.tmvp;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by TongChen at 17:44 on 2018/7/8.
 * <p>
 * Description:该文件实现的功能
 */
public interface HttpService {

    @GET
    Observable<GankData<Android>> getAndroidDataByPage(String categoryName, String page, String size);

}
