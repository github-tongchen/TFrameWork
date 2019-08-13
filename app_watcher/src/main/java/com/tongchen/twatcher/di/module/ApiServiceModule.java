package com.tongchen.twatcher.di.module;

import android.content.Context;

import com.tongchen.twatcher.base.http.Api;
import com.tongchen.twatcher.base.http.CommonHeaderInterceptor;
import com.tongchen.twatcher.base.http.MultipleConverterFactory;
import com.tongchen.twatcher.base.http.SSLSocketFactoryCompat;
import com.tongchen.twatcher.gank.model.http.GankServiceApi;
import com.tongchen.twatcher.mzitu.model.MZiTuServiceApi;
import com.tongchen.twatcher.util.LogUtils;

import java.io.File;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * Created by TongChen on 2017/11/11.
 * <p>
 * Description: 对全App 提供 API
 */

@Module
public class ApiServiceModule {

    /*private Retrofit createRetrofit(Retrofit.Builder builder, String url, OkHttpClient client) {
        return builder
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    GankServiceApi provideGankServiceApi(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, HttpUrl.GANK_BASE, client).create(GankServiceApi.class);
    }*/

    @Singleton
    @Provides
    MZiTuServiceApi provideMZiTuServiceApi(Retrofit retrofit) {
        return retrofit.create(MZiTuServiceApi.class);
    }

    @Singleton
    @Provides
    GankServiceApi provideGankServiceApi(Retrofit retrofit) {
        return retrofit.create(GankServiceApi.class);
    }

    @Singleton
    @Provides
    Retrofit providesRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Api.DOMAIN_GANK)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MultipleConverterFactory.create())
                .build();
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
    OkHttpClient provideOkHttpClient(Context context, CommonHeaderInterceptor commonHeaderInterceptor, HttpLoggingInterceptor loggingInterceptor, SSLSocketFactory sslSocketFactory, X509TrustManager trustAllCert) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        int cacheSize = 10 * 1024 * 1024;
        File dir = new File(context.getCacheDir(), "Cache");
        Cache cache = new Cache(dir, cacheSize);
        //  设置缓存
        builder.cache(cache);
        builder.addInterceptor(commonHeaderInterceptor);
        builder.addInterceptor(loggingInterceptor);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        //  解决https证书过期无法访问的问题
        builder.sslSocketFactory(sslSocketFactory, trustAllCert);

        return RetrofitUrlManager.getInstance().with(builder).build();
    }

    @Singleton
    @Provides
    SSLSocketFactory provideSSLSocketFactory(X509TrustManager trustAllCert) {
        return new SSLSocketFactoryCompat(trustAllCert);
    }

    @Singleton
    @Provides
    X509TrustManager provideX509TrustManager() {
        // 自定义一个信任所有证书的TrustManager
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        };
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
