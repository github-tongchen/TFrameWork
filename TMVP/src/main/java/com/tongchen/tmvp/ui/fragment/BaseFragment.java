package com.tongchen.tmvp.ui.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tongchen.tmvp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class BaseFragment extends Fragment {

    public BaseFragment() {

    }

    public static BaseFragment newInstance() {
        BaseFragment fragment = new BaseFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tmvp_fragment_base, container, false);
    }
}
