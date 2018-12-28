package com.tongchen.twatcher.mzitu.view;

import com.tongchen.twatcher.base.view.IMVPView;
import com.tongchen.twatcher.mzitu.model.entity.MZiTu;

import java.util.List;

/**
 * Created by TongChen at 11:02 on 2018/12/28.
 * <p>
 * Description: 妹子图按分类获取封面列表
 */
public interface IMZiTuCategoryView extends IMVPView<List<MZiTu>> {

    void noMoreData();

}
