package com.tongchen.twatcher.mzitu.model.parse;

import android.text.TextUtils;

import com.tongchen.twatcher.mzitu.model.entity.BaseResult;
import com.tongchen.twatcher.mzitu.model.entity.MZiTu;
import com.tongchen.twatcher.util.LogUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TongChen at 12:08 on 2018/12/25.
 * <p>
 * Description: 妹子图(https://www.mzitu.com)网页数据解析器
 */
public class MZiTuParser {

    public static final String TAG = "MZiTuParser";

    /**
     * 解析妹子图对应分类当前页的全部专辑的封面图片列表
     *
     * @param html
     * @param page
     * @return 对应分类当前页的全部专辑的封面图片列表
     */
    public static BaseResult<List<MZiTu>> parseAlbumCoverListByCategory(String html, int page) {
        BaseResult<List<MZiTu>> baseResult = new BaseResult<>();
        baseResult.setTotalPage(1);
        Document doc = Jsoup.parse(html);
        Element ulPins = doc.getElementById("pins");
        Elements lis = ulPins.select("li");
        List<MZiTu> mZiTuList = new ArrayList<>();
        for (Element li : lis) {
            MZiTu mZiTu = new MZiTu();
            if(li.select("a").isEmpty()){
                continue;
            }
            String contentUrl = li.select("a").first().attr("href");
            int index = contentUrl.lastIndexOf("/");
            if (index >= 0 && index + 1 < contentUrl.length()) {
                String idStr = contentUrl.substring(index + 1, contentUrl.length());
                LogUtils.i(TAG, "parseAlbumCoverListByCategory---id:" + idStr);

                if (!TextUtils.isEmpty(idStr) && TextUtils.isDigitsOnly(idStr)) {
                    mZiTu.setId(Integer.parseInt(idStr));
                }
            }

            Element imageElement = li.selectFirst("img");
            String title = imageElement.attr("alt");
            mZiTu.setName(title);

            String thumbUrl = imageElement.attr("data-original");
            mZiTu.setThumbUrl(thumbUrl);

            int height = Integer.parseInt(imageElement.attr("height"));
            mZiTu.setHeight(height);

            int width = Integer.parseInt(imageElement.attr("width"));
            mZiTu.setWidth(width);

            LogUtils.i(TAG,"parseAlbumCoverListByCategory---11111" );

            String date = li.getElementsByClass("time").first().text();
            mZiTu.setDate(date);

            LogUtils.i(TAG,"parseAlbumCoverListByCategory---22222");


            /*String viewCount = li.getElementsByClass("view").first().text();
            mZiTu.setViewCount(viewCount);

            LogUtils.i(TAG,"parseAlbumCoverListByCategory---33333");*/


            mZiTuList.add(mZiTu);
        }

        if (page == 1) {
            Elements pageElements = doc.getElementsByClass("page-numbers");
            if (pageElements != null && pageElements.size() > 3) {
                String pageStr = pageElements.get(pageElements.size() - 2).text();
                LogUtils.i(TAG,"parseAlbumCoverListByCategory---totalPage:" + pageStr);

                if (!TextUtils.isEmpty(pageStr) && TextUtils.isDigitsOnly(pageStr)) {
                    baseResult.setTotalPage(Integer.parseInt(pageStr));
                }
            }
        }

        baseResult.setData(mZiTuList);
        return baseResult;
    }


    /**
     * 解析妹子图当前专辑的图片列表
     *
     * @param html
     * @return 当前图片专辑的图片列表
     */
    public static BaseResult<List<String>> parseAlbumImageList(String html) {
        BaseResult<List<String>> baseResult = new BaseResult<>();

        Document doc = Jsoup.parse(html);
        return baseResult;
    }
}
