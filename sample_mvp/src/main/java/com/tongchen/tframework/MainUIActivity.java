package com.tongchen.tframework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tongchen.tmvp.ui.activity.MVPActivity;

public class MainUIActivity extends MVPActivity<User, ISampleView, ISamplePresenter> implements ISampleView {

    private ISamplePresenter mISamplePresenter;


    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, MainUIActivity.class);
        intent.putExtra("param1", data1);
        intent.putExtra("param2", data2);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mISamplePresenter.requestSample("", "");
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
    public void requestSucceed(User result) {

    }

    @Override
    public void requestFailed(String errorMsg) {

    }


}
