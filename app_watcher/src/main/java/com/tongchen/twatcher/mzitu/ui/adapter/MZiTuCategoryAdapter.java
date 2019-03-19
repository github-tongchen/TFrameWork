package com.tongchen.twatcher.mzitu.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tongchen.twatcher.R;
import com.tongchen.twatcher.mzitu.model.entity.MZiTu;
import com.tongchen.twatcher.util.ScreenUtils;

import java.util.List;

/**
 * Created by TongChen at 19:44 on 2019/1/7.
 * <p>
 * Description:该文件实现的功能
 */
public class MZiTuCategoryAdapter extends BaseQuickAdapter<MZiTu, BaseViewHolder> {

    public MZiTuCategoryAdapter(@Nullable List<MZiTu> data) {
        super(R.layout.mzitu_recycle_item_img, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MZiTu item) {
//        Glide.with(mContext).load(item.getThumbUrl()).into((ImageView) helper.getView(R.id.iv_preview));

        Glide.with(mContext)
                .load(buildGlideUrl(item.getThumbUrl()))
                .transition(new DrawableTransitionOptions().crossFade(300))
                .into((ImageView) helper.getView(R.id.iv_preview));

        int width= (int) (ScreenUtils.getScreenWidth(mContext)/2);
        int height = item.getHeight() * width / item.getWidth();

        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) helper.itemView.getLayoutParams();
        layoutParams.height = height;
        helper.itemView.setLayoutParams(layoutParams);
    }

    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder()
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9,zh-TW;q=0.8")
                    .addHeader("Host", "i.meizitu.net")
                    .addHeader("Referer", "http://www.mzitu.com/")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
                    .build());
        }
    }
}
