package com.tongchen.tframework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tongchen.tmvp.ui.activity.BaseActivity;

public class MainUIActivity extends BaseActivity {


    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, MainUIActivity.class);
        intent.putExtra("param1", data1);
        intent.putExtra("param2", data2);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    public void requestSucceed(String t) {

    }

    @Override
    public void requestFailed(String errorMsg) {

    }
}
