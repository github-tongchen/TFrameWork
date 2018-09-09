package com.tongchen.twatcher.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.tongchen.twatcher.TApp;
import com.tongchen.twatcher.base.ui.activity.MVPActivity;
import com.tongchen.twatcher.di.component.DaggerSampleComponent;
import com.tongchen.twatcher.di.module.SampleModule;
import com.tongchen.twatcher.mvp.model.entity.Android;
import com.tongchen.twatcher.mvp.model.entity.GankData;
import com.tongchen.twatcher.mvp.presenter.ISamplePresenter;
import com.tongchen.twatcher.mvp.view.ISampleView;
import com.tongchen.twatcher.util.LogUtils;

import java.util.List;

public class SampleActivity extends MVPActivity<GankData<List<Android>>, ISampleView, ISamplePresenter> implements ISampleView {

    private static final String TAG = "SampleActivity";

    private TextView mContentTv;

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, SampleActivity.class);
        intent.putExtra("param1", data1);
        intent.putExtra("param2", data2);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d("TTT", mPresenter + "---");

        mContentTv = findViewById(com.tongchen.twatcher.R.id.tv_content);

        mPresenter.getAndroidDataByPage("Android", "5", "1");
    }

    @Override
    public int bindLayout() {
        return com.tongchen.twatcher.R.layout.tmvp_activity_sample;
    }

    @Override
    public void injectActivity() {

        DaggerSampleComponent.builder()
                .sampleModule(new SampleModule(this))
                .appComponent(TApp.getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void requestSucceed(GankData<List<Android>> result) {

        LogUtils.d(TAG, "requestSucceed" + result.getResult().get(0).getCreatedAt());
        mContentTv.setText(result.toString());

    }

    @Override
    public void requestFailed(String errorMsg) {
        LogUtils.d(TAG, "requestFailed" + errorMsg);

    }


}
