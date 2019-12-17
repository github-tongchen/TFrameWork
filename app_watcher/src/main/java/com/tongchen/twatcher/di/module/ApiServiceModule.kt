package com.tongchen.twatcher.di.module

import android.content.Context

import com.tongchen.twatcher.base.http.Api
import com.tongchen.twatcher.base.http.CommonHeaderInterceptor
import com.tongchen.twatcher.base.http.MultipleConverterFactory
import com.tongchen.twatcher.base.http.SSLSocketFactoryCompat
import com.tongchen.twatcher.gank.model.http.GankServiceApi
import com.tongchen.twatcher.mzitu.model.http.MZiTuServiceApi
import com.tongchen.twatcher.util.LogUtils

import java.io.File
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit

import javax.inject.Singleton
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

import dagger.Module
import dagger.Provides
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by TongChen on 2017/11/11.
 *
 *
 * Description: 对全App 提供 API
 */

@Module
class ApiServiceModule {

    /*private fun createRetrofit(builder: Retrofit.Builder, url: String, client: OkHttpClient): Retrofit {
        return builder
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }*/

    /*@Singleton
    @Provides
    fun provideGankServiceApi(builder: Retrofit.Builder, client: OkHttpClient): GankServiceApi {
        return createRetrofit(builder, Api.DOMAIN_GANK, client).create(GankServiceApi::class.java)
    }*/


    @Singleton
    @Provides
    fun provideMZiTuServiceApi(retrofit: Retrofit): MZiTuServiceApi {
        return retrofit.create(MZiTuServiceApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGankServiceApi(retrofit: Retrofit): GankServiceApi {
        return retrofit.create(GankServiceApi::class.java)
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Api.DOMAIN_GANK)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //                .addConverterFactory(GsonConverterFactory.create())
                //                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MultipleConverterFactory.create())
                .build()
    }

    /*@Singleton
    @Provides
    fun provideOkHttpClient(builder: OkHttpClient.Builder, context: Context, interceptor: HttpLoggingInterceptor): OkHttpClient {
        val cacheSize: Long = 10 * 1024 * 1024;
        val dir = File(context.cacheDir, "Cache")
        val cache = Cache(dir, cacheSize)
        return builder
                .cache(cache)
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()
    }*/

    @Singleton
    @Provides
    fun provideOkHttpClient(context: Context, commonHeaderInterceptor: CommonHeaderInterceptor, loggingInterceptor: HttpLoggingInterceptor, sslSocketFactory: SSLSocketFactory, trustAllCert: X509TrustManager): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val cacheSize = 10 * 1024 * 1024
        val dir = File(context.cacheDir, "Cache")
        val cache = Cache(dir, cacheSize.toLong())
        //  设置缓存
        builder.cache(cache)
        builder.addInterceptor(commonHeaderInterceptor)
        builder.addInterceptor(loggingInterceptor)
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(10, TimeUnit.SECONDS)
        builder.writeTimeout(10, TimeUnit.SECONDS)
        //  解决https证书过期无法访问的问题
        builder.sslSocketFactory(sslSocketFactory, trustAllCert)

        return RetrofitUrlManager.getInstance().with(builder).build()
    }

    @Singleton
    @Provides
    fun provideSSLSocketFactory(trustAllCert: X509TrustManager): SSLSocketFactory {
        return SSLSocketFactoryCompat(trustAllCert)
    }

    @Singleton
    @Provides
    fun provideX509TrustManager(): X509TrustManager {
        // 自定义一个信任所有证书的TrustManager
        return object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {

            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {

            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            //打印retrofit日志
            LogUtils.i("RetrofitLog", "retrofitBack = $message")
        }.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

}
