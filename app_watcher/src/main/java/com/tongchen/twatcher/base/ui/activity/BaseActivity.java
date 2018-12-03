package com.tongchen.twatcher.base.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(bindLayout());
        //  绑定ButterKnife
        ButterKnife.bind(this);
        loadView();

    }

    @LayoutRes
    protected abstract int bindLayout();

    protected abstract void loadView();

}
