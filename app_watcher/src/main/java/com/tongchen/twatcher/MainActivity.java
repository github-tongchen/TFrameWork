package com.tongchen.twatcher;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.tongchen.twatcher.base.ui.activity.BaseActivity;
import com.tongchen.twatcher.base.ui.fragment.BaseFragment;
import com.tongchen.twatcher.gank.ui.BackHandledInterface;
import com.tongchen.twatcher.gank.ui.fragment.GankFragment;
import com.tongchen.twatcher.util.ToastUtils;
import com.tongchen.twatcher.widget.TDrawerLayout;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BackHandledInterface {

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

    private BaseFragment mBaseFragment;

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
        transaction.add(R.id.fl_main_container, fragment).addToBackStack(null);
        transaction.commit();
    }


}
