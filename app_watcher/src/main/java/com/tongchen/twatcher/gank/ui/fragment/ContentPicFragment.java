package com.tongchen.twatcher.gank.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.tongchen.twatcher.R;
import com.tongchen.twatcher.base.ui.fragment.BaseFragment;
import com.tongchen.twatcher.gank.model.entity.GankResult;
import com.tongchen.twatcher.util.FileUtils;
import com.tongchen.twatcher.util.ToastUtils;

import java.io.File;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContentPicFragment extends BaseFragment {

    public static final String TAG = "ContentTextFragment";

    private static final String ARG_GANK_RESULT = "gank_result";

    private GankResult mGankResult;

    @BindView(R.id.iv_pic)
    ImageView mPicIv;

    private ConfirmDialogFragment mConfirmDialogFragment;

    public ContentPicFragment() {
    }

    public static ContentPicFragment newInstance(GankResult result) {
        ContentPicFragment fragment = new ContentPicFragment();
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
        return R.layout.gank_fragment_content_pic;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Glide.with(mActivity).load(mGankResult.getUrl()).into(mPicIv);

        mConfirmDialogFragment = ConfirmDialogFragment.newInstance();
        mConfirmDialogFragment.setOnDialogClickListener(new ConfirmDialogFragment.OnDialogClickListener() {
            @Override
            public void onPositive() {
                savePic2Local();
                mConfirmDialogFragment.dismiss();
            }

            @Override
            public void onNegative() {
                mConfirmDialogFragment.dismiss();
            }
        });

        mPicIv.setOnLongClickListener(v -> {
            if (getFragmentManager() != null) {
                mConfirmDialogFragment.show(getFragmentManager(), "ConfirmDialogFragment");
            }
            return true;
        });
    }

    private void savePic2Local() {
        Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> emitter) throws Exception {
                emitter.onNext(Glide.with(mActivity)
                        .downloadOnly()
                        .load(mGankResult.getUrl())
                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get());
                emitter.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(File file) {
                        File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                        File appDir = new File(pictureFolder, "/TWatcher/gank/");
                        if (!appDir.exists()) {
                            boolean mkSuccess = appDir.mkdirs();
                            if (!mkSuccess) {
                                ToastUtils.showShort(R.string.save_pic_failed);
                                return;
                            }
                        }
                        String fileName = System.currentTimeMillis() + ".jpg";
                        File destFile = new File(appDir, fileName);
                        //  把Glide下载得到图片复制到定义好的目录中去
                        FileUtils.copyFile2Target(file, destFile);
                        updateAlbum(destFile);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort(R.string.save_pic_failed);
                    }

                    @Override
                    public void onComplete() {
                        ToastUtils.showShort(R.string.save_pic_succeed);
                    }
                });

    }

    // 通知图库更新
    private void updateAlbum(File destFile) {
        Uri uri = Uri.fromFile(destFile);
        mActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
    }

}
