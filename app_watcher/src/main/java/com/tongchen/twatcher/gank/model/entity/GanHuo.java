package com.tongchen.twatcher.gank.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * JSON的最外层
 */

public class GanHuo {

    @SerializedName("error")
    private Boolean mError;
    @SerializedName("results")
    private List<Result> mResults;

    public Boolean getError() {
        return mError;
    }

    public void setError(Boolean error) {
        mError = error;
    }

    public List<Result> getResults() {
        return mResults;
    }

    public void setResults(List<Result> results) {
        mResults = results;
    }

}
