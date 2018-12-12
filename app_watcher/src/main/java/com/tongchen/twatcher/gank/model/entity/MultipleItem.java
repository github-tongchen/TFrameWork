package com.tongchen.twatcher.gank.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by TongChen at 22:12 on 2018/12/12.
 * <p>
 * Description:BaseRecyclerViewAdapterHelper 多布局时需要
 */
public class MultipleItem implements MultiItemEntity {

    public static final int TYPE_TEXT = 0;
    public static final int TYPE_IMG = 1;

    private int mItemType;
    private GankResult mData;

    public MultipleItem(int itemType, GankResult data) {
        mItemType = itemType;
        mData = data;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public GankResult getData() {
        return mData;
    }
}
