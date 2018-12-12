package com.tongchen.twatcher.gank.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tongchen.twatcher.R;
import com.tongchen.twatcher.gank.model.entity.GankResult;
import com.tongchen.twatcher.gank.model.entity.MultipleItem;
import com.tongchen.twatcher.gank.model.http.HttpUrl;
import com.tongchen.twatcher.util.LogUtils;
import com.tongchen.twatcher.util.ScreenUtils;

import java.util.List;

import retrofit2.http.Url;

/**
 * Created by TongChen at 21:46 on 2018/11/1.
 * <p>
 * Description:该文件实现的功能
 */
public class CategoryAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    public CategoryAdapter(List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.TYPE_TEXT, R.layout.gank_recycle_item_text);
        addItemType(MultipleItem.TYPE_IMG, R.layout.gank_recycle_item_img);
    }

    /*public CategoryAdapter(int layoutResId, @Nullable List<GankResult> data) {
        super(layoutResId, data);
    }*/

    /*@Override
    protected void convert(BaseViewHolder helper, GankResult item) {


    }*/

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (item.getItemType()) {
            case MultipleItem.TYPE_TEXT:
                helper.setText(R.id.tv_desc, item.getData().getDesc());
                helper.setText(R.id.tv_date, item.getData().getPublishedAt());
                if (item.getData().getImages() != null && item.getData().getImages().size() > 0) {
                    helper.getView(R.id.iv_preview).setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(item.getData().getImages().get(0)).into((ImageView) helper.getView(R.id.iv_preview));
                } else {
                    helper.getView(R.id.iv_preview).setVisibility(View.GONE);
                }
                break;
            case MultipleItem.TYPE_IMG:
                LogUtils.d("Img", "enter");

                int height = (int) (ScreenUtils.getScreenHeight(mContext) / 3);
//                helper.getView(R.id.iv_preview).getLayoutParams().width = width;
                helper.getView(R.id.iv_preview).getLayoutParams().height = height;

                if (item.getData().getUrl() != null && !TextUtils.isEmpty(item.getData().getUrl())) {
                    helper.getView(R.id.iv_preview).setVisibility(View.VISIBLE);
                    LogUtils.d("Img", item.getData().getUrl() + HttpUrl.IMAGE_WIDTH_SUFFIX + ScreenUtils.getScreenWidth(mContext) / 2);
                    Glide.with(mContext).load(item.getData().getUrl() + HttpUrl.IMAGE_WIDTH_SUFFIX + ScreenUtils.getScreenWidth(mContext) / 2).into((ImageView) helper.getView(R.id.iv_preview));
                } else {
                    helper.getView(R.id.iv_preview).setVisibility(View.GONE);
                }
                break;
        }
    }
}
