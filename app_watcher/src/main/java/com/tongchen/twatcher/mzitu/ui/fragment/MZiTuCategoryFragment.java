package com.tongchen.twatcher.mzitu.ui.fragment;

import android.os.Bundle;

import com.tongchen.twatcher.R;
import com.tongchen.twatcher.base.ui.fragment.MVPFragment;
import com.tongchen.twatcher.mzitu.model.entity.MZiTu;
import com.tongchen.twatcher.mzitu.model.entity.MZiTuCategory;
import com.tongchen.twatcher.mzitu.presenter.IMZiTuCategoryPresenter;
import com.tongchen.twatcher.mzitu.view.IMZiTuCategoryView;

import java.util.List;

public class MZiTuCategoryFragment extends MVPFragment<List<MZiTu>,IMZiTuCategoryView,IMZiTuCategoryPresenter> implements IMZiTuCategoryView {

    private static final String ARG_CATEGORY = "category";


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

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject2Fragment(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void refreshSucceed(List<MZiTu> result) {

    }

    @Override
    public void refreshFailed(String errorMsg) {

    }

    @Override
    public void loadMoreSucceed(List<MZiTu> result) {

    }

    @Override
    public void loadMoreFailed(String errorMsg) {

    }

    @Override
    public void noMoreData() {

    }
}
