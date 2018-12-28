package com.tongchen.twatcher.mzitu.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TongChen at 18:26 on 2018/12/27.
 * <p>
 * Description: 妹子图分类
 */
public class MZiTuCategory implements Parcelable {

    //  显示的名称
    private String mCategoryName;
    //  请求用的参数
    private String mRequestName;

    public MZiTuCategory(Builder builder) {
        mCategoryName = builder.categoryName;
        mRequestName = builder.requestName;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public String getRequestName() {
        return mRequestName;
    }

    protected MZiTuCategory(Parcel in) {
        this.mCategoryName = in.readString();
        this.mRequestName = in.readString();
    }

    public static final Parcelable.Creator<MZiTuCategory> CREATOR = new Parcelable.Creator<MZiTuCategory>() {
        @Override
        public MZiTuCategory createFromParcel(Parcel source) {
            return new MZiTuCategory(source);
        }

        @Override
        public MZiTuCategory[] newArray(int size) {
            return new MZiTuCategory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCategoryName);
        dest.writeString(this.mRequestName);
    }

    public static class Builder {

        private String categoryName;
        private String requestName;

        public Builder categoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public Builder requestName(String requestName) {
            this.requestName = requestName;
            return this;
        }

        public MZiTuCategory build() {
            return new MZiTuCategory(this);
        }
    }
}
