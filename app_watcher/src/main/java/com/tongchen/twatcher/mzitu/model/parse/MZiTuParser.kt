package com.tongchen.twatcher.mzitu.model.parse

import android.text.TextUtils
import com.tongchen.twatcher.mzitu.model.entity.BaseResult
import com.tongchen.twatcher.mzitu.model.entity.MZiTu
import com.tongchen.twatcher.util.LogUtils
import org.jsoup.Jsoup

/**
 * @author TongChen
 * @date 2019/11/28  16:36
 * <p>
 * Desc:
 */
object MZiTuParser {

    private val TAG = "MZiTuParser"

    /**
     * 解析妹子图对应分类当前页的全部专辑的封面图片列表
     *
     * @param html
     * @param page
     * @return 对应分类当前页的全部专辑的封面图片列表
     */
    fun parseAlbumCoverListByCategory(html: String, page: Int): BaseResult<MutableList<MZiTu>> {
        val baseResult = BaseResult<MutableList<MZiTu>>()
        baseResult.totalPage = 1
        val doc = Jsoup.parse(html)
        val ulPins = doc.getElementById("pins")
        val lis = ulPins.select("li")
        val mZiTuList = mutableListOf<MZiTu>()
        for (li in lis) {
            val mZiTu = MZiTu()
            if (li.select("a").isEmpty()) {
                continue
            }
            val contentUrl = li.select("a").first().attr("href")
            val index = contentUrl.lastIndexOf("/")
            if (index > 0 && index < contentUrl.length - 1) {
                val idStr = contentUrl.substring(index + 1)
                LogUtils.i(TAG, "parseAlbumCoverListByCategory---id:$idStr")

                if (!TextUtils.isEmpty(idStr) && TextUtils.isDigitsOnly(idStr)) {
                    mZiTu.id = idStr.toInt()
                }
            }

            val imgElement = li.selectFirst("img")
            val title = imgElement.attr("alt")
            mZiTu.name = title

            val thumbUrl = imgElement.attr("data-original")
            mZiTu.thumbUrl = thumbUrl

            val height = imgElement.attr("height").toInt()
            mZiTu.height = height

            val width = imgElement.attr("width").toInt()
            mZiTu.width = width

            val date = li.getElementsByClass("time").first().text()
            mZiTu.date = date

            /*val viewCount = li.getElementsByClass("view").first().text()
            mZiTu.viewCount = viewCount*/

            mZiTuList.add(mZiTu)
        }

        if (page == 1) {
            val pageElements = doc.getElementsByClass("page-numbers")
            if (pageElements != null && pageElements.size > 3) {
                val pageStr = pageElements.get(pageElements.size - 2).text()
                LogUtils.i(TAG, "parseAlbumCoverListByCategory---totalPage:$pageStr")

                if (!TextUtils.isEmpty(pageStr) && TextUtils.isDigitsOnly(pageStr)) {
                    baseResult.totalPage = pageStr.toInt()
                }
            }
        }

        baseResult.data = mZiTuList
        return baseResult
    }

    /**
     * 解析妹子图当前专辑的图片列表
     *
     * @param html
     * @return 当前图片专辑的图片列表
     */
    fun parseAlbumImageList(html: String): BaseResult<MutableList<String>> {
        val baseResult = BaseResult<MutableList<String>>()

        val doc = Jsoup.parse(html)
        return baseResult
    }
}