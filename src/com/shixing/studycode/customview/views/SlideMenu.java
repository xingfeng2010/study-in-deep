package com.shixing.studycode.customview.views;

import com.shixing.study.R;
import com.shixing.studycode.utils.MeasureUtil;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlideMenu extends HorizontalScrollView {

    private float lastX;
    private LinearLayout mLinearLayout;
    private int mMenuWidth;
    private int mScreenWidth;
    private int mMenuRightPadding;
    private boolean once = false;
    private int mHalfWidth;

    public SlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mScreenWidth = MeasureUtil.getScreenSize((Activity) context)[0];
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SlidingMenu, defStyle, 0);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
            case R.styleable.SlidingMenu_rightPadding:
                // 默认50
                mMenuRightPadding = a.getDimensionPixelSize(attr,
                        (int) TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP, 50f,
                                getResources().getDisplayMetrics()));
                break;
            }
        }
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (!once) {
            mLinearLayout = (LinearLayout) this.getChildAt(0);

            ViewGroup menuView = (ViewGroup) mLinearLayout.getChildAt(0);
            ViewGroup contentView = (ViewGroup) mLinearLayout.getChildAt(1);

            mMenuWidth = mScreenWidth - mMenuRightPadding;
            mHalfWidth = mMenuWidth / 2;
//            menuView.getLayoutParams().width = mMenuWidth;
//            contentView.getLayoutParams().width = mScreenWidth;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenuWidth, 0);
            once = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        int action = ev.getAction();
//        switch (action) {
//        // Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
//        case MotionEvent.ACTION_UP:
//            int scrollX = getScrollX();
//            android.util.Log.i("shixing","count="+this.getChildCount());
//            if (scrollX > mHalfWidth) {
//                this.smoothScrollTo(mMenuWidth+mScreenWidth, 0);
//            } else {
//                this.smoothScrollTo(0, 0);
//            }
//            return true;
//        }
        return super.onTouchEvent(ev);
    }

}
