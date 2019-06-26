package com.tongchen.twatcher.base.http;

import android.support.annotation.NonNull;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by TongChen at 20:14 on 2019/1/7.
 * <p>
 * Description: 统一设置请求头，解决部分网站的反爬虫问题
 */
@Singleton
public class CommonHeaderInterceptor implements Interceptor {

    @Inject
    public CommonHeaderInterceptor() {
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        //统一设置请求头
        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder();
        requestBuilder.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299");
        requestBuilder.header("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5");
        requestBuilder.header("Proxy-Connection", "keep-alive");
        requestBuilder.header("Cache-Control", "max-age=0");
        // requestBuilder.header("X-Forwarded-For","114.114.114.117")
        requestBuilder.method(original.method(), original.body());

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}