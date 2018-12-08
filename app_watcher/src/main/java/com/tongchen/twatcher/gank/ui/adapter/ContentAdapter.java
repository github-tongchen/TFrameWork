package com.tongchen.twatcher.gank.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tongchen.twatcher.R;
import com.tongchen.twatcher.gank.model.entity.Android;

import java.util.List;

/**
 * Created by TongChen at 21:46 on 2018/11/1.
 * <p>
 * Description:该文件实现的功能
 */
public class ContentAdapter extends BaseQuickAdapter<Android, BaseViewHolder> {

    public ContentAdapter(int layoutResId, @Nullable List<Android> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Android item) {
        helper.setText(R.id.tv_desc, item.getDesc());
        helper.setText(R.id.tv_date, item.getPublishedAt());
        if (item.getImages() != null && item.getImages().size() > 0) {
            Glide.with(mContext).load(item.getImages().get(0)).into((ImageView) helper.getView(R.id.iv_preview));
        }
    }
}
