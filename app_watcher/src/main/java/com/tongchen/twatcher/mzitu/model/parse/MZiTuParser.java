package com.tongchen.twatcher.mzitu.model.parse;

import com.tongchen.twatcher.mzitu.model.entity.BaseResult;
import com.tongchen.twatcher.mzitu.model.entity.MZiTu;

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
        return baseResult;
    }
}
