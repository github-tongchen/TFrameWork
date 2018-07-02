package com.tongchen.tmvp.di.module;

import android.content.Context;

import com.tongchen.tmvp.util.LogUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TongChen on 2017/11/11.
 * <p>
 * Description:
 */

@Module
public class HttpModule {

    private Retrofit createRetrofit(Retrofit.Builder builder, String url, OkHttpClient client) {
        return builder
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /*@Singleton
    @Provides
    GankApi provideGankApi(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, Url.GANK_BASE, client).create(GankApi.class);
    }*/

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder, Context context, HttpLoggingInterceptor interceptor) {
        int cacheSize = 10 * 1024 * 1024;
        File dir = new File(context.getCacheDir(), "Cache");
        Cache cache = new Cache(dir, cacheSize);
        return builder
                .cache(cache)
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor(message -> {
            //打印retrofit日志
            LogUtils.i("RetrofitLog", "retrofitBack = " + message);
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

}
