package com.tongchen.twatcher.gank.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TongChen at 18:25 on 2018/12/12.
 * <p>
 * Description: 分类的数据
 */
public class ContentCategory implements Parcelable {

    private String mCategoryName;
    private String mRequestName;

    public ContentCategory(String categoryName, String requestName) {
        mCategoryName = categoryName;
        mRequestName = requestName;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public String getRequestName() {
        return mRequestName;
    }

    protected ContentCategory(Parcel in) {
        mCategoryName = in.readString();
        mRequestName = in.readString();
    }

    public static final Creator<ContentCategory> CREATOR = new Creator<ContentCategory>() {
        @Override
        public ContentCategory createFromParcel(Parcel in) {
            return new ContentCategory(in);
        }

        @Override
        public ContentCategory[] newArray(int size) {
            return new ContentCategory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCategoryName);
        dest.writeString(mRequestName);
    }
}
