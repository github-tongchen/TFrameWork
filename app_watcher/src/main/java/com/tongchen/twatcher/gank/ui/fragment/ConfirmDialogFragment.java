package com.tongchen.twatcher.gank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tongchen.twatcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by TongChen at 14:33 on 2018/12/26.
 * <p>
 * Description: 描述该文件实现的功能
 */
public class ConfirmDialogFragment extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.tv_tip)
    TextView mTipTv;
    @BindView(R.id.tv_positive)
    TextView mPositiveTv;
    @BindView(R.id.tv_negative)
    TextView mNegativeTv;

    private Unbinder mUnbinder;
    private OnDialogClickListener mDialogClickListener;

    public static ConfirmDialogFragment newInstance() {
        return new ConfirmDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gank_fragment_confirm_dialog, container, false);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPositiveTv.setOnClickListener(this);
        mNegativeTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_positive:
                mDialogClickListener.onPositive();
                break;
            case R.id.tv_negative:
                mDialogClickListener.onNegative();
                break;
        }
    }

    public void setOnDialogClickListener(OnDialogClickListener listener) {
        mDialogClickListener = listener;
    }

    public interface OnDialogClickListener {

        void onPositive();

        void onNegative();
    }

}
