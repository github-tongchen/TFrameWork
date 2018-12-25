package com.tongchen.twatcher.mzitu.model.entity;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by TongChen at 12:17 on 2018/12/25.
 * <p>
 * Description: 解析基类
 */
public class BaseResult<T> {

    public final static int SUCCESS_CODE = 1;
    public final static int ERROR_CODE = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SUCCESS_CODE, ERROR_CODE})
    @interface ResultCode {

    }

    private int mCode;
    private String mMessage;
    private T mData;
    private Integer mTotalPage;

    public int getCode() {
        return mCode;
    }

    public void setCode(@ResultCode int code) {
        mCode = code;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public Integer getTotalPage() {
        return mTotalPage;
    }

    public void setTotalPage(Integer totalPage) {
        mTotalPage = totalPage;
    }
}
