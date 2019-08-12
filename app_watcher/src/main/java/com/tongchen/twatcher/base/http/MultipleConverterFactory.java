package com.tongchen.twatcher.base.http;

import android.text.TextUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.annotations.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jaxb.JaxbConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by TongChen at 14:54 on 2019/08/12.
 * <p>
 * Description: 自定义Factory以支持多种类型的数据解析
 */
public class MultipleConverterFactory extends Converter.Factory {

    private final Converter.Factory mJsonFactory = GsonConverterFactory.create();
    private final Converter.Factory mStringFactory = ScalarsConverterFactory.create();
    private final Converter.Factory mXmlFactory = JaxbConverterFactory.create();

    public static MultipleConverterFactory create() {
        return new MultipleConverterFactory();
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        for (Annotation annotation : annotations) {
            if (!(annotation instanceof ResponseFormat)) {
                continue;
            }
            String value = ((ResponseFormat) annotation).value();
            if (TextUtils.equals(ResponseFormat.JSON, value)) {
                return mJsonFactory.responseBodyConverter(type, annotations, retrofit);

            } else if (TextUtils.equals(ResponseFormat.STRING, value)) {
                return mStringFactory.responseBodyConverter(type, annotations, retrofit);

            } else if (TextUtils.equals(ResponseFormat.XML, value)) {
                return mXmlFactory.responseBodyConverter(type, annotations, retrofit);
            }
        }

        return super.responseBodyConverter(type, annotations, retrofit);
    }
}
