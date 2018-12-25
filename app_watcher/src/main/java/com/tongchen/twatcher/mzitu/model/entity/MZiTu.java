package com.tongchen.twatcher.mzitu.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TongChen at 11:44 on 2018/12/25.
 * <p>
 * Description:妹子图解析实体类
 */

public class MZiTu implements Parcelable {

    private int id;
    private String name;
    private String date;
    private String viewCount;
    private String thumbUrl;
    private int height;
    private int width;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    protected MZiTu(Parcel in) {
        id = in.readInt();
        name = in.readString();
        date = in.readString();
        viewCount = in.readString();
        thumbUrl = in.readString();
        height = in.readInt();
        width = in.readInt();
    }

    public static final Creator<MZiTu> CREATOR = new Creator<MZiTu>() {
        @Override
        public MZiTu createFromParcel(Parcel in) {
            return new MZiTu(in);
        }

        @Override
        public MZiTu[] newArray(int size) {
            return new MZiTu[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(viewCount);
        dest.writeString(thumbUrl);
        dest.writeInt(height);
        dest.writeInt(width);
    }
}
