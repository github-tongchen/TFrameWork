package com.tongchen.twatcher.mzitu.ui.adapter

import android.content.Context
import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.tongchen.twatcher.R
import com.tongchen.twatcher.base.ui.BaseRecyclerAdapter
import com.tongchen.twatcher.mzitu.model.entity.MZiTu
import com.tongchen.twatcher.util.ScreenUtils

/**
 * @author TongChen
 * @date 2019/11/28  11:50
 * <p>
 * Desc:
 */
class MZiTuCategoryAdapter : BaseRecyclerAdapter<MZiTu> {

    private var mMZiTuList: MutableList<MZiTu>

    constructor(context: Context, list: MutableList<MZiTu>) : super(context, list) {
        mMZiTuList = list

        bindLayoutIds()
    }

    fun bindLayoutIds() {
        mLayouts.put(VIEW_TYPE_HEADER, null)
        mLayouts.put(VIEW_TYPE_BODY, R.layout.mzitu_recycle_item_img)
        mLayouts.put(VIEW_TYPE_FOOTER, null)
    }

    override fun bindViewHolder(holder: BaseViewHolder, position: Int, itemData: MZiTu) {
        Glide.with(mContext)
                .load(buildGlideUrl(itemData.thumbUrl))
                .transition(DrawableTransitionOptions().crossFade(300))
                .into(holder.getView(R.id.iv_preview))

        val width: Int = (ScreenUtils.getScreenWidth(mContext) / 2).toInt()
        val height: Int = itemData.height * width / itemData.width

        val layoutParams: StaggeredGridLayoutManager.LayoutParams = holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
        layoutParams.height = height
        holder.itemView.layoutParams = layoutParams
    }

    private fun buildGlideUrl(url: String): GlideUrl? {
        return if (TextUtils.isEmpty(url)) {
            null
        } else {
            val header = LazyHeaders.Builder()
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9,zh-TW;q=0.8")
                    //.addHeader("Host", "i.meizitu.net")
                    .addHeader("Referer", "http://www.mzitu.com/")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
                    .build()
            GlideUrl(url, header)
        }
    }
}