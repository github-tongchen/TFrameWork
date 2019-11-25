package com.tongchen.twatcher;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tongchen.twatcher.base.ui.BackHandleInterface;
import com.tongchen.twatcher.base.ui.activity.BaseActivity;
import com.tongchen.twatcher.base.ui.fragment.BaseFragment;
import com.tongchen.twatcher.gank.ui.fragment.GankMainFragment;
import com.tongchen.twatcher.mzitu.ui.fragment.MZiTuFragment;
import com.tongchen.twatcher.util.LogUtils;
import com.tongchen.twatcher.util.ToastUtils;
import com.tongchen.twatcher.widget.TDrawerLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements BackHandleInterface {

    private static final String TAG = "MainActivity";

    //  不同分类的最底层Fragment
    public static final String TAG_FRAGMENT_MAIN = "MainFragment";

    @BindView(R.id.tdrawerlyt)
    TDrawerLayout mTDrawerLyt;
    @BindView(R.id.ll_menu_container)
    LinearLayout mMenuContainerLLyt;
    @BindView(R.id.tv_gank)
    TextView mGankTv;
    @BindView(R.id.tv_mzitu)
    TextView mMZiTuTv;
    @BindView(R.id.fl_content_container)
    FrameLayout mContentContainerFLyt;

    private FragmentManager mFragmentManager;
    private GankMainFragment mGankFragment;
    private MZiTuFragment mMZiTuFragment;

    //  点击2次返回才退出
    private long firstTime = 0;

    private BaseFragment mBaseFragment;

    @Override
    public int bindLayout() {
        return R.layout.main_activity;
    }

    @Override
    protected void loadView() {
        mFragmentManager = getSupportFragmentManager();
        mGankFragment = GankMainFragment.newInstance();
        mMZiTuFragment = MZiTuFragment.newInstance();

        loadFragment(mGankFragment);

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.fl_content_container, fragment, TAG_FRAGMENT_MAIN);
        transaction.commit();
    }

    @OnClick({R.id.tv_gank, R.id.tv_mzitu})
    public void onViewClick(View view) {
        LogUtils.d(TAG, "view---" + view.toString());
        switch (view.getId()) {
            case R.id.tv_gank:
                if (mGankFragment == null) {
                    mGankFragment = GankMainFragment.newInstance();
                }
                if (!mGankFragment.isAdded()) {
                    loadFragment(mGankFragment);
                    mFragmentManager.beginTransaction().hide(mMZiTuFragment).commit();
                } else {
                    if (!mGankFragment.isVisible()) {
                        mFragmentManager.beginTransaction().hide(mMZiTuFragment).show(mGankFragment).commit();
                    }
                }

                break;
            case R.id.tv_mzitu:
                if (mMZiTuFragment == null) {
                    mMZiTuFragment = MZiTuFragment.newInstance();
                }
                if (!mMZiTuFragment.isAdded()) {
                    loadFragment(mMZiTuFragment);
                    mFragmentManager.beginTransaction().hide(mGankFragment).commit();
                } else {
                    if (!mMZiTuFragment.isVisible()) {
                        mFragmentManager.beginTransaction().hide(mGankFragment).show(mMZiTuFragment).commit();
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mBaseFragment == null || !mBaseFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                //  如果当前菜单展开，先隐藏菜单
                if (mTDrawerLyt.isMenuOpen()) {
                    mTDrawerLyt.toggleMenu();
                    return;
                }
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime < 2000) {
                    System.exit(0);
                } else {
                    ToastUtils.showShort(R.string.sys_exit);
                    firstTime = System.currentTimeMillis();
                }
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }

    }

    public void isDispatchEvent2DrawerLayout(boolean isDispatch) {
        mTDrawerLyt.setConsume(isDispatch);
    }

    public void toggleDrawerLyt() {
        mTDrawerLyt.toggleMenu();
    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        mBaseFragment = selectedFragment;
    }

    public void startFragment(Fragment fragment) {
//        mTDrawerLyt.setConsume(false);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_translate_in, R.anim.fragment_translate_out, R.anim.fragment_translate_in, R.anim.fragment_translate_out);
        transaction.add(R.id.fl_content_container, fragment).addToBackStack(null);
        transaction.commit();
    }


}
