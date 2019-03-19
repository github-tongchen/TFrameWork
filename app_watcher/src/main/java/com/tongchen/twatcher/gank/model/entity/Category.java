package com.tongchen.twatcher.gank.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TongChen at 18:25 on 2018/12/12.
 * <p>
 * Description: 分类的数据，使用Parcelable序列化（Parcelable比Serializable效率高）
 */
public class Category extends BaseBean implements Parcelable {

    //  显示的名称
    private String mCategoryName;
    //  请求用的参数
    private String mRequestName;
    //  当前分类在分类List中的下标
    private int mIndex;
    //  分类List的大小
    private int mCount;

    public Category(Builder builder) {
        mCategoryName = builder.categoryName;
        mRequestName = builder.requestName;
        mIndex = builder.index;
        mCount = builder.count;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public String getRequestName() {
        return mRequestName;
    }

    public int getIndex() {
        return mIndex;
    }

    public int getCount() {
        return mCount;
    }

    protected Category(Parcel in) {
        mCategoryName = in.readString();
        mRequestName = in.readString();
        mIndex = in.readInt();
        mCount = in.readInt();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
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
        dest.writeInt(mIndex);
        dest.writeInt(mCount);
    }

    public static class Builder {

        private String categoryName;
        private String requestName;
        private int index;
        private int count = 9;

        public Builder categoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public Builder requestName(String requestName) {
            this.requestName = requestName;
            return this;
        }

        public Builder index(int index) {
            this.index = index;
            return this;
        }

        public Builder count(int count) {
            this.count = count;
            return this;
        }

        public Category build() {
            return new Category(this);
        }

    }
}
