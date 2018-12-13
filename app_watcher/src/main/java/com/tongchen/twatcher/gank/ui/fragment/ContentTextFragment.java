package com.tongchen.twatcher.gank.ui.fragment;

import android.os.Bundle;

import com.tongchen.twatcher.MainActivity;
import com.tongchen.twatcher.R;
import com.tongchen.twatcher.base.ui.fragment.BaseFragment;
import com.tongchen.twatcher.gank.model.entity.GankResult;

public class ContentTextFragment extends BaseFragment {

    private static final String ARG_GANK_RESULT = "gank_result";

    private GankResult mGankResult;

    public ContentTextFragment() {
    }

    public static ContentTextFragment newInstance(GankResult result) {
        ContentTextFragment fragment = new ContentTextFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_GANK_RESULT, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGankResult = getArguments().getParcelable(ARG_GANK_RESULT);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.gank_fragment_content_text;
    }

    public void updateData(GankResult result) {

    }
}
