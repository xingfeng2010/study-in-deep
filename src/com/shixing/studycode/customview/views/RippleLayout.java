package com.shixing.studycode.customview.views;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class RippleLayout extends LinearLayout {

    private int mCenterX;
    private int mCenterY;

    private Paint mPaint = new Paint();
    
    private View mTargetView;
    private int mMaxCircus;

    private int mCircus;
    private boolean mShouldRipple;

    public RippleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.RED);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
        case MotionEvent.ACTION_UP:
            mCenterX = (int) ev.getRawX();
            mCenterY = (int) ev.getRawY();
            View targetView = getTargetView(mCenterX, mCenterY);

            mMaxCircus = Math.max(targetView.getMeasuredWidth(),
                    targetView.getMeasuredHeight());

            mCircus = Math.min(targetView.getMeasuredWidth(),
                    targetView.getMeasuredHeight()) / 4;
            mShouldRipple = true;

            if (targetView != null) {
                mTargetView = targetView;
                this.postInvalidate();
            }

            break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private View getTargetView(float x, float y) {
        List<View> viewList = this.getTouchables();

        View temp = null;

        for (View view : viewList) {
            if (positionInView(view, x, y)) {
                temp = view;
                break;
            }
        }

        return temp;
    }

    private boolean positionInView(View view, float x, float y) {
        int[] location = new int[2];

        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();

        if (view.isClickable() && x >= left && x <= right && y >= top
                & y <= bottom) {
            return true;
        }

        return false;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (!mShouldRipple || mTargetView == null) {
            return;
        }

        int[] location = new int[2];

        mTargetView.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + mTargetView.getMeasuredWidth();
        int bottom = top + mTargetView.getMeasuredHeight();
        Rect rect = new Rect(left, top, right, bottom);

        canvas.save();
        canvas.clipRect(rect);
        canvas.drawCircle(mCenterX, mCenterY, mCircus, mPaint);
        canvas.restore();

        if (mCircus <= mMaxCircus) {
            mCircus += 2;
            this.postInvalidateDelayed(10, left, top, right, bottom);
        } else {
            mShouldRipple = false;
            this.postInvalidateDelayed(10, left, top, right, bottom);
        }

    }

}
