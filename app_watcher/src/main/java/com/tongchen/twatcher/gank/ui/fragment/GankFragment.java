package com.tongchen.twatcher.gank.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tongchen.twatcher.R;
import com.tongchen.twatcher.base.ui.fragment.BaseFragment;
import com.tongchen.twatcher.gank.FragmentAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GankFragment extends BaseFragment {

    @BindView(R.id.tabLyt)
    TabLayout tabLyt;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.fab)
    FloatingActionButton mFABtn;

    private List<String> mTitleList;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentAdapter mAdapter;


    private String[] mTitleArray = {"全部", "Android", "iOS", "前端", "拓展资源", "休息视频", "瞎推荐", "App", "福利"};

    public GankFragment() {

    }

    public static GankFragment newInstance() {
        return new GankFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDatas();
    }

    private void initDatas() {
        mTitleList = new ArrayList<>(Arrays.asList(mTitleArray));

        for (int i = 0; i < mTitleList.size(); i++) {
            ContentFragment fragment = ContentFragment.newInstance(mTitleList.get(i));
            mFragmentList.add(fragment);
        }

        mAdapter = new FragmentAdapter(getFragmentManager(), mFragmentList, mTitleList);
        viewpager.setAdapter(mAdapter);
        tabLyt.setupWithViewPager(viewpager);
    }

    @Override
    public int bindLayout() {
        return R.layout.gank_fragment;
    }

    @OnClick(R.id.fab)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                int index = viewpager.getCurrentItem();
                ((ContentFragment) mFragmentList.get(index)).back2Top();
                break;
        }
    }

}
