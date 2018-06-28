package com.tongchen.tmvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.tongchen.tmvp.view.IBaseView;


public abstract class BaseActivity extends AppCompatActivity implements IBaseView{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());

    }

    @LayoutRes
    public abstract int bindLayout();

}
