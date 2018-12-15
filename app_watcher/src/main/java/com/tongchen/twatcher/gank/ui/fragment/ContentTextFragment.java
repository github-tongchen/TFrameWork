package com.tongchen.twatcher.gank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tongchen.twatcher.R;
import com.tongchen.twatcher.base.ui.fragment.BaseFragment;
import com.tongchen.twatcher.gank.model.entity.GankResult;
import com.tongchen.twatcher.util.LogUtils;

import butterknife.BindView;

public class ContentTextFragment extends BaseFragment {

    public static final String TAG = "ContentTextFragment";

    private static final String ARG_GANK_RESULT = "gank_result";

    private GankResult mGankResult;

    @BindView(R.id.iv_head_bg)
    ImageView mHeadBgIv;
    @BindView(R.id.web_content)
    WebView mWebView;

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
        LogUtils.d(TAG, mGankResult.toString());
    }

    @Override
    public int bindLayout() {
        return R.layout.gank_fragment_content_text;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Glide.with(mActivity).load(mGankResult.getImages().get(0)).into(mHeadBgIv);
        mWebView.loadUrl(mGankResult.getUrl());
    }

    public void updateData(GankResult result) {

    }
}
