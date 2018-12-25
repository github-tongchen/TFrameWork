package com.tongchen.twatcher.gank.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tongchen.twatcher.MainActivity;
import com.tongchen.twatcher.R;
import com.tongchen.twatcher.base.ui.fragment.BaseFragment;
import com.tongchen.twatcher.gank.model.entity.GankResult;
import com.tongchen.twatcher.gank.ui.AppBarStateChangeListener;
import com.tongchen.twatcher.widget.MarqueTextView;

import java.util.List;

import butterknife.BindView;

public class ContentTextFragment extends BaseFragment {

    public static final String TAG = "ContentTextFragment";

    private static final String ARG_GANK_RESULT = "gank_result";

    private GankResult mGankResult;

    @BindView(R.id.appbarLyt)
    AppBarLayout mAppBarLyt;
    @BindView(R.id.iv_head_bg)
    ImageView mHeadBgIv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsingToolbarLyt)
    CollapsingToolbarLayout mCollapsingToolbarLyt;
    @BindView(R.id.mtv_title)
    MarqueTextView mTitleMtv;
    @BindView(R.id.progressbar_4_web)
    ProgressBar mWebProgressBar;
    @BindView(R.id.web_content)
    WebView mWebView;
    @BindView(R.id.tv_publish_date)
    TextView mPublishDateTv;


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

    @Override
    public boolean onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.stopLoading();
            mWebView.goBack();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<String> imgList = mGankResult.getImages();
        if (imgList != null && imgList.size() > 0) {
            Glide.with(mActivity).load(imgList.get(0)).into(mHeadBgIv);
        }

        mAppBarLyt.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                switch (state) {
                    case EXPANDED:
                    case IDLE:
                        mTitleMtv.setText("");
                        mCollapsingToolbarLyt.setTitle(mGankResult.getDesc());
                        mToolbar.setNavigationIcon(null);
                        break;
                    case COLLAPSED:
                        mTitleMtv.setText(mGankResult.getDesc());
                        mCollapsingToolbarLyt.setTitle("");
                        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
                        break;
                }
            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivity instanceof MainActivity) {
                    ((MainActivity) mActivity).onBackPressed();
                }
            }
        });

        mPublishDateTv.setText(String.format("发布日期: %s", mGankResult.getPublishedAt().split("T")[0]));
        //  先隐藏加载完成后再显示
        mPublishDateTv.setVisibility(View.GONE);

        mWebView.setWebViewClient(new WebViewClient() {

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                mWebView.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mWebProgressBar == null) {
                    return;
                }
                //  进度条处理
                if (mWebProgressBar.getVisibility() == View.GONE) {
                    mWebProgressBar.setVisibility(View.VISIBLE);
                }
                mWebProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    mWebProgressBar.setVisibility(View.GONE);
                    mPublishDateTv.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mWebView.loadUrl(mGankResult.getUrl());
    }

    @Override
    public void onStop() {
        super.onStop();
        mWebView.stopLoading();
    }

    public void updateData(GankResult result) {

    }
}
