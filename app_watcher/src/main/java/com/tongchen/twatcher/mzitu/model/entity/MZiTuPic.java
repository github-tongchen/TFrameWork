package com.tongchen.twatcher.mzitu.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TongChen at 17:49 on 2019/08/13.
 * <p>
 * Description:该文件实现的功能
 */
public class MZiTuPic implements Parcelable {

    public MZiTuPic(Parcel in) {

    }

    public static final Creator<MZiTuPic> CREATOR = new Creator<MZiTuPic>() {
        @Override
        public MZiTuPic createFromParcel(Parcel in) {
            return new MZiTuPic(in);
        }

        @Override
        public MZiTuPic[] newArray(int size) {
            return new MZiTuPic[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
