package com.tongchen.twatcher;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tongchen.twatcher.base.ui.activity.BaseActivity;
import com.tongchen.twatcher.gank.ui.fragment.GankFragment;
import com.tongchen.twatcher.widget.TDrawerLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tdrawer)
    TDrawerLayout mTDrawerLyt;
    @BindView(R.id.fl_main_container)
    FrameLayout mMainContainerFl;
    /*@BindView(R.id.toolbar)
    Toolbar mToolbar;*/

    private FragmentManager mFragmentManager;
    private GankFragment mGankFragment;

    //  点击2次返回才退出
    private long firstTime = 0;

    @Override
    public int bindLayout() {
        return R.layout.main_activity;
    }

    @Override
    protected void loadView() {
//        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        mFragmentManager = getSupportFragmentManager();

        mGankFragment = GankFragment.newInstance();

        loadFragment(mGankFragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.fl_main_container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //  HomeAsUp按钮的id永远都是android.R.id.home
            case android.R.id.home:
                mTDrawerLyt.toggleMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime < 2000) {
            System.exit(0);
        } else {
            Toast.makeText(this, R.string.sys_exit, Toast.LENGTH_SHORT).show();
            firstTime = System.currentTimeMillis();
        }
    }

    public void isDispatchEvent2DrawerLayout(boolean isDispatch) {
        mTDrawerLyt.setConsume(isDispatch);
    }

}
