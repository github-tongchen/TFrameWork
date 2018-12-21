package com.tongchen.twatcher.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.tongchen.twatcher.R;
import com.tongchen.twatcher.util.ScreenUtils;

/**
 * Created by TongChen at 14:05 on 2018/12/21.
 * <p>
 * Description: 圆形的ImageView
 */
public class CircleImageView extends AppCompatImageView {

    private int mBorderColor;
    private int mBorderWidth;
    private int mBackgroundColor;
    private int mRadius;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);

        mBorderColor = typedArray.getColor(R.styleable.CircleImageView_border_color, Color.WHITE);
        mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.CircleImageView_border_width, ScreenUtils.dip2px(context.getApplicationContext(), 5));
        mBackgroundColor=typedArray.getColor(R.styleable.CircleImageView_circle_background_color,Color.LTGRAY);

        typedArray.recycle();
    }
}
