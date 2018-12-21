package com.tongchen.twatcher;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tongchen.twatcher.base.ui.activity.BaseActivity;
import com.tongchen.twatcher.gank.ui.fragment.GankFragment;
import com.tongchen.twatcher.widget.TDrawerLayout;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    //  不同分类的最底层Fragment
    public static final String TAG_FRAGMENT_MAIN = "MainFragment";

    @BindView(R.id.tdrawerlyt)
    TDrawerLayout mTDrawerLyt;
    @BindView(R.id.fl_main_container)
    FrameLayout mMainContainerFLyt;

    private FragmentManager mFragmentManager;
    private GankFragment mGankFragment;

    //  点击2次返回才退出
    private long firstTime = 0;
    private OnBackPressedListener mBackPressedListener;

    @Override
    public int bindLayout() {
        return R.layout.main_activity;
    }

    @Override
    protected void loadView() {
        mFragmentManager = getSupportFragmentManager();
        mGankFragment = GankFragment.newInstance();
        loadFragment(mGankFragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.fl_main_container, fragment, TAG_FRAGMENT_MAIN);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = mFragmentManager.findFragmentById(R.id.fl_main_container);
        //  当前显示的为主Fragment时执行退出APP流程
        if (TextUtils.equals(fragment.getTag(), TAG_FRAGMENT_MAIN)) {
            //  如果当前菜单展开，先隐藏菜单
            if (mTDrawerLyt.isMenuOpen()) {
                mTDrawerLyt.toggleMenu();
                return;
            }
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime < 2000) {
                System.exit(0);
            } else {
                Toast.makeText(this, R.string.sys_exit, Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }
        } else {
//            mFragmentManager.beginTransaction().hide(fragment).commit();
            mBackPressedListener.onBackPressed();
        }

    }

    public void isDispatchEvent2DrawerLayout(boolean isDispatch) {
        mTDrawerLyt.setConsume(isDispatch);
    }

    public void toggleDrawerLyt() {
        mTDrawerLyt.toggleMenu();
    }

    public interface OnBackPressedListener {
        void onBackPressed();
    }

    public void setOnBackPressedListener(OnBackPressedListener listener) {
        mBackPressedListener = listener;
    }
}
