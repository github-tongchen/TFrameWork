package com.tongchen.twatcher.gank.ui.fragment

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.AppBarLayout
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bumptech.glide.Glide
import com.tongchen.twatcher.MainActivity
import com.tongchen.twatcher.R
import com.tongchen.twatcher.base.ui.fragment.BaseFragment
import com.tongchen.twatcher.gank.model.entity.GankResult
import com.tongchen.twatcher.gank.ui.AppBarStateChangeListener
import kotlinx.android.synthetic.main.gank_fragment_content_text.*

/**
 * @author TongChen
 * @date 2019/11/28  9:58
 * <p>
 * Desc:
 */
class ContentTextFragment : BaseFragment() {

    private val TAG = "ContentTextFragment"

    private var mGankResult: GankResult? = null
    private lateinit var mWebView: WebView

    companion object {
        const val ARG_GANK_RESULT = "gank_result"

        @JvmStatic
        fun newInstance(result: GankResult): ContentTextFragment {
            val fragment = ContentTextFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_GANK_RESULT, result)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mGankResult = arguments!!.getParcelable(ARG_GANK_RESULT)
        }
    }

    override fun bindLayout() = R.layout.gank_fragment_content_text

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mWebView = web_content

        val imgList = mGankResult?.images


        if (imgList != null) {
            if (imgList.size > 0) {
                Glide.with(mActivity).load(imgList[0]).into(iv_head_bg)
            }
            appbarLyt.setExpanded(true)

        } else {
            appbarLyt.setExpanded(false)
        }

        appbarLyt.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                when (state) {
                    State.EXPANDED, State.IDLE -> {
                        mtv_title.text = ""
                        collapsingToolbarLyt.title = mGankResult?.desc
                        toolbar.navigationIcon = null
                    }

                    State.COLLAPSED -> {
                        mtv_title.text = mGankResult?.desc
                        collapsingToolbarLyt.title = ""
                        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
                    }
                }
            }
        })

        toolbar.setNavigationOnClickListener {
            if (mActivity is MainActivity) {
                (mActivity as MainActivity).onBackPressed()
            }
        }

        tv_publish_date.text = String.format(getString(R.string.publish_date), mGankResult?.publishedAt!!.split("T")[0])
        //  先隐藏加载完成后再显示
        tv_publish_date.visibility = View.GONE

        web_content.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                mWebView.loadUrl(url)
                return true
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                mWebView.loadUrl(request?.url.toString())
                return true
            }
        }

        web_content.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (progressbar_4_web == null) {
                    return
                }
                //  进度条处理
                if (progressbar_4_web.visibility == View.GONE) {
                    progressbar_4_web.visibility = View.VISIBLE
                }
                progressbar_4_web.progress = newProgress
                if (newProgress == 100) {
                    progressbar_4_web.visibility = View.GONE
                    tv_publish_date.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mWebView.loadUrl(mGankResult?.url)
    }

    override fun onStop() {
        super.onStop()
        mWebView.stopLoading()
    }

    override fun onBackPressed(): Boolean {
        if (mWebView.canGoBack()) {
            mWebView.stopLoading()
            mWebView.goBack()
            return true
        }
        return super.onBackPressed()
    }
}