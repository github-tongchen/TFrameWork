package com.tongchen.twatcher.mzitu.ui.fragment;

import android.os.Bundle;

import com.tongchen.twatcher.R;
import com.tongchen.twatcher.base.ui.fragment.BaseFragment;
import com.tongchen.twatcher.mzitu.model.entity.MZiTuCategory;

public class MZiTuCategoryFragment extends BaseFragment {

    private static final String ARG_CATEGORY = "category";


    public MZiTuCategoryFragment() {
        // Required empty public constructor
    }

    public static MZiTuCategoryFragment newInstance(MZiTuCategory category) {
        MZiTuCategoryFragment fragment = new MZiTuCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_CATEGORY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.mzitu_fragment_category;
    }

}
