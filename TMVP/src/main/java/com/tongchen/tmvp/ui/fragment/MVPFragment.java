package com.tongchen.tmvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tongchen.tmvp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MVPFragment extends BaseFragment {

    public MVPFragment() {

        Class a=MVPFragment.class;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tmvp_fragment_base, container, false);
    }
}
