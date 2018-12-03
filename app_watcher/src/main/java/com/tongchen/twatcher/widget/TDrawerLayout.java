package com.tongchen.twatcher.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.tongchen.twatcher.R;
import com.tongchen.twatcher.util.ScreenUtils;

/**
 * Created by TongChen on 2017/7/25.
 * <p>
 * Description:自定义仿 QQ6.0/KuGou8 侧滑菜单，通过在xml布局文件中配置属性“navigation_style”决定侧滑风格
 */

public class TDrawerLayout extends ViewGroup {

    private final int QQ_STYLE = 0;
    private final int KuGOU_STYLE = 1;

    private int mScreenWidth, mScreenHeight, mMenuRightPadding, mMenuWidth, mContentWidth;
    //  mMenu是侧滑出的部分，mContent是策划前的主界面
    private ViewGroup mMenu, mContent;
    private int mLastX, mLastY;

    private int mNavigationStyle;
    private Scroller mScroller;
    private boolean mIsOpen;
    private int mLastXIntercept, mLastYIntercept;

    private float scale;

    public TDrawerLayout(Context context) {
        this(context, null, 0);
    }

    public TDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TDrawerLayout);
        mNavigationStyle = ta.getInt(R.styleable.TDrawerLayout_navigation_style, 0);
        ta.recycle();

        mScroller = new Scroller(context);

        //设置侧滑菜单距离屏幕右侧的距离
        mMenuRightPadding = ScreenUtils.dip2px(context.getApplicationContext(), 100);

        mScreenWidth = (int) ScreenUtils.getScreenWidth(context.getApplicationContext());
        mScreenHeight = (int) ScreenUtils.getScreenHeight(context.getApplicationContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //  拿到Menu，Menu是第0个孩子
        mMenu = (ViewGroup) getChildAt(0);
        //  拿到Content，Content是第1个孩子
        mContent = (ViewGroup) getChildAt(1);
        //  设置菜单宽度为屏幕的宽度减去Menu距离屏幕右侧的距离
        mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
        //  设置content的宽为屏幕的宽度
        mContentWidth = mContent.getLayoutParams().width = mScreenWidth;
        //  测量菜单
        measureChild(mMenu, widthMeasureSpec, heightMeasureSpec);
        //  测量content
        measureChild(mContent, widthMeasureSpec, heightMeasureSpec);
        //  测量自己，自己的宽度为Menu宽度加上content宽度，高度为屏幕高度
        setMeasuredDimension(mMenuWidth + mContentWidth, mScreenHeight);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        //  摆放Menu的位置，根据上面图可以确定上下左右的坐标
        mMenu.layout(-mMenuWidth, 0, 0, mScreenHeight);
        //  摆放content的位置
        mContent.layout(0, 0, mScreenWidth, mScreenHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) ev.getX() - mLastXIntercept;
                int deltaY = (int) (ev.getY() - mLastYIntercept);
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercept = true;
                } else {//   纵向滑动
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (getScrollX() < -mMenuWidth / 2) {
                    //调用startScroll方法，第一个参数是起始X坐标，第二个参数是起始Y坐标，第三个参数是X方向偏移量，第四个参数是Y方向偏移量
                    mScroller.startScroll(getScrollX(), 0, -mMenuWidth - getScrollX(), 0, 300);
                    //设置一个已经打开的标识，当实现点击开关自动打开关闭功能时会用到
                    mIsOpen = true;
                    //一定不要忘了调用这个方法重绘，否则没有动画效果
                    invalidate();
                } else {//关闭Menu
                    //同上
                    mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, 300);
                    mIsOpen = false;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                switch (mNavigationStyle) {
                    case QQ_STYLE:
                        mMenu.setTranslationX(2 * (mMenuWidth + getScrollX()) / 3);
                        break;
                    case KuGOU_STYLE:
                        scale = Math.abs((float) getScrollX()) / (float) mMenuWidth;
                        mMenu.setTranslationX(mMenuWidth + getScrollX() - (mMenuWidth / 2) * (1.0f - scale));

                        mMenu.setScaleX(0.7f + 0.3f * scale);
                        mMenu.setScaleY(0.7f + 0.3f * scale);
                        mMenu.setAlpha(scale);

                        mContent.setScaleX(1 - 0.3f * scale);
                        mContent.setPivotY(mScreenHeight / 2);
                        mContent.setScaleY(1.0f - 0.3f * scale);
                        break;
                }

                int currentX = (int) event.getX();
                int currentY = (int) event.getY();
                //  获取x方向上的偏移
                int dx = currentX - mLastX;
                //  向左滑动
                if (dx < 0) {
                    //边界控制，如果Content已经完全显示，再滑动的话,content右侧就会出现白边了，进行边界控制
                    if (getScrollX() + Math.abs(dx) >= 0) {
                        //直接移动到（0，0）位置，不会出现白边
                        scrollTo(0, 0);
                    } else {
                        scrollBy(-dx, 0);
                    }
                } else {
                    //  边界控制，如果Menu已经完全显示，再滑动的话Menu左侧就会出现白边了,进行边界控制
                    if (getScrollX() - dx <= -mMenuWidth) {
                        scrollTo(-mMenuWidth, 0);
                    } else {
                        scrollBy(-dx, 0);
                    }
                }
                mLastX = currentX;
                mLastY = currentY;
                break;
        }

        return true;
    }

    @Override
    public void computeScroll() {
        switch (mNavigationStyle) {
            case QQ_STYLE:
                mMenu.setTranslationX(2 * (mMenuWidth + getScrollX()) / 3);
                break;
            case KuGOU_STYLE:
                scale = Math.abs((float) getScrollX()) / (float) mMenuWidth;
                mMenu.setTranslationX(mMenuWidth + getScrollX() - (mMenuWidth / 2) * (1.0f - scale));

                mMenu.setScaleX(0.7f + 0.3f * scale);
                mMenu.setScaleY(0.7f + 0.3f * scale);
                mMenu.setAlpha(scale);

                mContent.setScaleX(1 - 0.3f * scale);
                mContent.setPivotY(mScreenHeight / 2);
                mContent.setScaleY(1.0f - 0.3f * scale);
        }


        //  判断是否滚动是否结束，true说明滚动尚未完成，false说明滚动已经完成
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 点击开关，开闭Menu，如果当前menu已经打开，则关闭，如果当前menu已经关闭，则打开
     */
    public void toggleMenu() {
        if (mIsOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    /**
     * 打开menu
     */
    private void openMenu() {
        mScroller.startScroll(getScrollX(), 0, -mMenuWidth - getScrollX(), 0, 500);
        invalidate();
        mIsOpen = true;
    }

    /**
     * 关闭menu
     */
    private void closeMenu() {
        //也是使用startScroll方法，dx和dy的计算方法一样
        mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, 500);
        invalidate();
        mIsOpen = false;
    }
}
