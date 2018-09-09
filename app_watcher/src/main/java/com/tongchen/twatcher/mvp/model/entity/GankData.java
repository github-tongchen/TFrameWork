package com.tongchen.twatcher.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TongChen on 2017/11/10.
 * <p>
 * Description:GSON使用方式参考：http://www.jianshu.com/p/e740196225a4
 */

public class GankData<T> {

    @SerializedName("error")
    private Boolean mError;

    @SerializedName("results")
    private T mResult;

    public Boolean getError() {
        return mError;
    }

    public void setError(Boolean error) {
        this.mError = error;
    }

    public T getResult() {
        return mResult;
    }

    public void setResult(T result) {
        this.mResult = result;
    }
}
