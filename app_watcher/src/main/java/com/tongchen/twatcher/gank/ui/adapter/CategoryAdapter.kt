package com.tongchen.twatcher.gank.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.tongchen.twatcher.R
import com.tongchen.twatcher.base.ui.BaseRecyclerAdapter
import com.tongchen.twatcher.gank.model.entity.GankResult
import com.tongchen.twatcher.gank.model.http.HttpUrl
import com.tongchen.twatcher.gank.ui.fragment.CategoryFragment
import com.tongchen.twatcher.util.ScreenUtils

/**
 * @author TongChen
 * @date 2019/11/20  16:45
 * <p>
 * Desc:
 */
class CategoryAdapter : BaseRecyclerAdapter<GankResult> {

    private var mSpanCount: Int

    constructor(context: Context, spanCount: Int, list: MutableList<GankResult>) : super(context, list) {
        mSpanCount = spanCount

        bindLayoutIds()
    }

    fun bindLayoutIds() {
        mLayouts.put(VIEW_TYPE_HEADER, null)
        if (mSpanCount == CategoryFragment.SINGLE_SPAN_COUNT) {
            mLayouts.put(VIEW_TYPE_BODY, R.layout.gank_recycle_item_text)

        } else {
            mLayouts.put(VIEW_TYPE_BODY, R.layout.gank_recycle_item_img)
        }
        mLayouts.put(VIEW_TYPE_FOOTER, null)
    }

    override fun bindViewHolder(holder: BaseViewHolder, position: Int, itemData: GankResult) {
        when (mSpanCount) {
            CategoryFragment.SINGLE_SPAN_COUNT -> {
                holder.setText(R.id.tv_desc, itemData.desc)
                holder.setText(R.id.tv_date, itemData.publishedAt)
                if (itemData.images != null && itemData.images.size > 0) {
                    holder.getView<ImageView>(R.id.iv_preview).visibility = View.VISIBLE
                    Glide.with(mContext).load(itemData.images[0]).into(holder.getView(R.id.iv_preview))
                } else {
                    holder.getView<ImageView>(R.id.iv_preview).visibility = View.GONE
                }
            }

            CategoryFragment.MULTIPLE_SPAN_COUNT -> {
                val height = (ScreenUtils.getScreenHeight(mContext) / 3).toInt()
                holder.getView<ImageView>(R.id.iv_preview).layoutParams.height = height

                if (itemData.url != null && !TextUtils.isEmpty(itemData.url)) {
                    holder.getView<ImageView>(R.id.iv_preview).visibility = View.VISIBLE
                    Glide.with(mContext).load(itemData.url + HttpUrl.IMAGE_WIDTH_SUFFIX + ScreenUtils.getScreenWidth(mContext) / 2).into(holder.getView(R.id.iv_preview))
                } else {
                    holder.getView<ImageView>(R.id.iv_preview).visibility = View.GONE
                }
            }
        }
    }

}