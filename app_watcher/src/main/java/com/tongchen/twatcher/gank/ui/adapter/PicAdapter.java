package com.tongchen.twatcher.gank.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tongchen.twatcher.gank.model.entity.GankResult;

import java.util.List;

/**
 * Created by TongChen at 16:16 on 2018/12/25.
 * <p>
 * Description: 干货集中营图片查看适配器
 */
public class PicAdapter extends PagerAdapter {

    private static final String TAG = "PicAdapter";

    private Context mContext;
    private List<GankResult> mGankResultList;

    public PicAdapter(Context context, List<GankResult> gankResultList) {
        mContext = context;
        mGankResultList = gankResultList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView picIv = new ImageView(mContext);
        Glide.with(mContext).load(mGankResultList.get(position).getUrl()).into(picIv);
        container.addView(picIv);
        return picIv;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mGankResultList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
