package com.tongchen.twatcher.base.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tongchen.twatcher.MainActivity;
import com.tongchen.twatcher.base.ui.BackHandleInterface;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment implements ViewPager.OnPageChangeListener {

    protected Activity mActivity;

    private Unbinder mUnbinder;

    protected BackHandleInterface mBackHandleInterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();

        if (!(getActivity() instanceof BackHandleInterface)) {
            throw new ClassCastException("Hosting Activity must implement BackHandleInterface");
        } else {
            this.mBackHandleInterface = (BackHandleInterface) getActivity();
        }
        //告诉Activity，当前Fragment在栈顶
        mBackHandleInterface.setSelectedFragment(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (!(getActivity() instanceof BackHandleInterface)) {
            throw new ClassCastException("Hosting Activity must implement BackHandleInterface");
        } else {
            this.mBackHandleInterface = (BackHandleInterface) getActivity();
        }
        //告诉Activity，当前Fragment已移除
        mBackHandleInterface.setSelectedFragment(null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(bindLayout(), container, false);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @LayoutRes
    public abstract int bindLayout();

    /**
     * 继承BaseFragment的子类需要自己处理返回键点击事件的时候重写此方法，默认不处理
     * MainActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
     * 如果没有Fragment消息时MainActivity自己才会消费该事件
     */
    public boolean onBackPressed() {
        return false;
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i == 0) {
            if (mActivity instanceof MainActivity) {
                ((MainActivity) mActivity).isDispatchEvent2DrawerLayout(true);
            }
        } else {
            if (mActivity instanceof MainActivity) {
                ((MainActivity) mActivity).isDispatchEvent2DrawerLayout(false);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
