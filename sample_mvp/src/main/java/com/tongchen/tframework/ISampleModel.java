package com.tongchen.tframework;

import com.tongchen.tmvp.model.IMVPModel;

/**
 * Created by TongChen at 12:00 on 2018/7/8.
 * <p>
 * Description:该文件实现的功能
 */
public interface ISampleModel extends IMVPModel {

    void getAndroidDataByPage(String categoryName, String page, String size);
}
