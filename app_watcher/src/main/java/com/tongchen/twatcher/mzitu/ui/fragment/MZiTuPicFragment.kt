package com.tongchen.twatcher.mzitu.ui.fragment

import android.os.Bundle
import com.bumptech.glide.Glide

import com.tongchen.twatcher.R
import com.tongchen.twatcher.base.ui.fragment.BaseFragment
import com.tongchen.twatcher.mzitu.model.entity.MZiTuPic
import kotlinx.android.synthetic.main.mzitu_fragment_pic.*

private const val ARG_MZITU_RESULT = "mzitu_result"

class MZiTuPicFragment : BaseFragment() {

    private var mMZiTuResult: MZiTuPic? = null

    companion object {
        @JvmStatic
        fun newInstance(result: MZiTuPic) =
                MZiTuPicFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_MZITU_RESULT, result)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mMZiTuResult = it.getParcelable<MZiTuPic>(ARG_MZITU_RESULT)
        }
    }


    override fun bindLayout(): Int {
        return R.layout.mzitu_fragment_pic
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = mActivity
        if (activity != null) {
            Glide.with(activity).load("").into(iv_pic)
        }
    }
}
