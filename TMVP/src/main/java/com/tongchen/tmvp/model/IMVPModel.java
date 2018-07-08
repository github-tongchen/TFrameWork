package com.tongchen.tmvp.model;

/**
 * Created by TongChen at 22:55 on 2018/6/24.
 * <p>
 * Description:该文件实现的功能
 * <p>
 * 参考链接如下：
 * <p>
 * https://www.baidu.com/s?wd=Activity%3CV%20extends%20MvpView%2C%20P%20extends%20MvpPresenter%3CV%3E%3E&pn=0&oq=Activity%3CV%20extends%20MvpView%2C%20P%20extends%20MvpPresenter%3CV%3E%3E&ie=utf-8&rsv_idx=1&rsv_pq=9da2067d0000f70a&rsv_t=cb67b4esj9IMF%2FMlc8sSrfVAZX37hzmfr6gUyXLgcEYQdZIgGOaXSL1xAuY
 * <p>
 * https://blog.csdn.net/baidu_27419681/article/details/77803374
 * <p>
 * https://blog.csdn.net/u013278099/article/details/52514882
 * <p>
 * https://www.jianshu.com/p/1652b968101c
 */
public interface IMVPModel {


    void requestFailed(String errorMsg);
}
