package com.tongchen.twatcher.gank.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TongChen on 2017/11/10.
 * <p>
 * Description:
 */
public class GankResult implements Parcelable {

    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("desc")
    private String mDesc;
    //  Video和Welfare没有images字段，要做null处理
    @SerializedName("images")
    private List<String> mImages;
    @SerializedName("publishedAt")
    private String mPublishedAt;
    @SerializedName("source")
    private String mSource;
    @SerializedName("type")
    private String mType;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("used")
    private boolean mUsed;
    @SerializedName("who")
    private String mWho;
    @SerializedName("_id")
    private String mId;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }

    public List<String> getImages() {
        return mImages;
    }

    public void setImages(List<String> images) {
        mImages = images;
    }

    public String getPublishedAt() {
        return mPublishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        mPublishedAt = publishedAt;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        mSource = source;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public boolean isUsed() {
        return mUsed;
    }

    public void setUsed(boolean used) {
        mUsed = used;
    }

    public String getWho() {
        return mWho;
    }

    public void setWho(String who) {
        mWho = who;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCreatedAt);
        dest.writeString(this.mDesc);
        dest.writeStringList(this.mImages);
        dest.writeString(this.mPublishedAt);
        dest.writeString(this.mSource);
        dest.writeString(this.mType);
        dest.writeString(this.mUrl);
        dest.writeByte(this.mUsed ? (byte) 1 : (byte) 0);
        dest.writeString(this.mWho);
        dest.writeString(this.mId);
    }

    protected GankResult(Parcel in) {
        this.mCreatedAt = in.readString();
        this.mDesc = in.readString();
        this.mImages = in.createStringArrayList();
        this.mPublishedAt = in.readString();
        this.mSource = in.readString();
        this.mType = in.readString();
        this.mUrl = in.readString();
        this.mUsed = in.readByte() != 0;
        this.mWho = in.readString();
        this.mId = in.readString();
    }

    public static final Creator<GankResult> CREATOR = new Creator<GankResult>() {
        @Override
        public GankResult createFromParcel(Parcel source) {
            return new GankResult(source);
        }

        @Override
        public GankResult[] newArray(int size) {
            return new GankResult[size];
        }
    };
}
