package com.tongchen.twatcher.gank.ui.fragment

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tongchen.twatcher.R
import kotlinx.android.synthetic.main.gank_fragment_confirm_dialog.*

/**
 * @author TongChen
 * @date 2019/11/20  14:24
 * <p>
 * Desc:
 */
class ConfirmDialogFragment : DialogFragment(), View.OnClickListener {

    private val TAG = "ConfirmDialogFragment"

    private var mDialogClickListener: OnDialogClickListener? = null

    companion object {
        @JvmStatic
        fun newInstance(): ConfirmDialogFragment {
            return ConfirmDialogFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.gank_fragment_confirm_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv_positive.setOnClickListener(this)
        tv_negative.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            tv_positive -> mDialogClickListener?.onPositive()
            tv_negative -> mDialogClickListener?.onNegative()
        }
    }

    fun setOnDialogClickListener(listener: OnDialogClickListener) {
        mDialogClickListener = listener
    }

    interface OnDialogClickListener {
        fun onPositive()

        fun onNegative()
    }
}