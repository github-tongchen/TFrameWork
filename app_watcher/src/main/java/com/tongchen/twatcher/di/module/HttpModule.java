package com.tongchen.twatcher.di.module;

import android.content.Context;

import com.tongchen.twatcher.mvp.model.http.HttpService;
import com.tongchen.twatcher.mvp.model.http.HttpUrl;
import com.tongchen.twatcher.util.LogUtils;
import com.tongchen.twatcher.base.http.SSLSocketFactoryCompat;

import java.io.File;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

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

    @Singleton
    @Provides
    HttpService provideHttpService(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, HttpUrl.GANK_BASE, client).create(HttpService.class);
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    /*@Singleton
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
    }*/

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder, Context context, HttpLoggingInterceptor interceptor) {
        try {
            // 自定义一个信任所有证书的TrustManager，添加SSLSocketFactory的时候要用到
            final X509TrustManager trustAllCert = new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            };
            final SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
            builder.sslSocketFactory(sslSocketFactory, trustAllCert);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
