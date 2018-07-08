package com.tongchen.tframework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tongchen.tmvp.Android;
import com.tongchen.tmvp.GankData;
import com.tongchen.tmvp.ui.activity.MVPActivity;
import com.tongchen.tmvp.util.LogUtils;

public class MainUIActivity extends MVPActivity<GankData<Android>, ISampleView, ISamplePresenter> implements ISampleView {

    private static final String TAG = "MainUIActivity";

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, MainUIActivity.class);
        intent.putExtra("param1", data1);
        intent.putExtra("param2", data2);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.getAndroidDataByPage("Android", "1", "5");
    }

    @Override
    public int bindLayout() {
        return R.layout.tmvp_activity_base;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void requestSucceed(GankData<Android> result) {

        LogUtils.d(TAG, result.toString());
    }

    @Override
    public void requestFailed(String errorMsg) {

    }


}
