package com.shixing.studycode.customview.views;

import com.shixing.studycode.utils.MeasureUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class TongXinView extends View {

    private Paint mPaint;
    private int mStrokeWidth = 30;

    private int mCenterX;
    private int mCenterY;
    private int mRadius = 80;
    private RectF mRect;
    private int mSwipe = 0;

    public TongXinView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Cap.SQUARE);

        int[] size = MeasureUtil.getScreenSize((Activity) context);
        mCenterX = size[0] / 2;
        mCenterY = size[1] / 2;

        mRect = new RectF(mCenterX - mRadius, mCenterY - mRadius, mCenterX
                + mRadius, mCenterY + mRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawArc(mRect, 0f, mSwipe, false, mPaint);

        if (mSwipe <= 360) {
            mSwipe += 1;
        } else {
            mSwipe = 0;
        }

        mHandler.sendEmptyMessage(1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
        }

    };

}
