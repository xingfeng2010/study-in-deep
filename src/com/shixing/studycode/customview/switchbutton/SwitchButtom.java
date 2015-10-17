package com.shixing.studycode.customview.switchbutton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SwitchButtom extends View {
    private Path mPath;
    private Paint mPaint;
    private Path mAnimPath;
    private boolean isAnim;
    private boolean isTranslate;

    private int mButtonWidth, mButtonHeight;
    private int mCenterX, mCenterY;
    private float mScaleSize;

    private float initScaleSize;

    private float mRadius;
    private float mPointX;
    private float mMoveX;
    private float mTranslateX = 0;
    private float mTranslateStep;

    private float mStrokeWidth;

    private enum Direction {
        RIGHT,
        LETF
    }

    private Direction direction;

    public SwitchButtom(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPath = new Path();
        mPaint = new Paint();
        mAnimPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (direction == Direction.RIGHT) {
            mPaint.setColor(Color.GREEN);
            if (mScaleSize > 0) {
                mScaleSize = (float) ((mScaleSize - 0.1f) * 0.98);
            } else {
                mScaleSize = 0;
                isAnim = false;
            }
        } else if (direction == Direction.LETF) {
            mPaint.setColor(0xffdddddd);
            if (mScaleSize <= initScaleSize) {
                mScaleSize = (float) ((mScaleSize + 0.1f) * 0.98);
            } else {
                mScaleSize = initScaleSize;
                isAnim = false;
            }
        }

        mTranslateX = mTranslateStep * (1 - mScaleSize);

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.FILL);
        canvas.drawPath(mPath, mPaint);
        mPaint.reset();

        // 画前面路径
        canvas.save();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(0xffffffff);
        canvas.scale(mScaleSize, mScaleSize, mCenterX, mCenterY);
        canvas.drawPath(mAnimPath, mPaint);
        canvas.restore();
        mPaint.reset();

        // 画按钮
        canvas.save();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(0xffffffff);
        canvas.translate(mTranslateX, 0);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        mPaint.setStyle(Style.STROKE);
        mPaint.setColor(0xffdddddd);
        mPaint.setStrokeWidth(mStrokeWidth);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint); // 按钮灰边
        canvas.restore();
        mPaint.reset();

        if (isAnim) {
            this.invalidate();
        }
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
        mButtonWidth = w;
        mButtonHeight = h;

        int bottom = mButtonHeight;
        mTranslateStep = mButtonWidth - bottom;
        mRadius = (float) (0.9 * (bottom / 2));
        mStrokeWidth = (float) (bottom * 0.1);

        mCenterX = (mButtonWidth - mButtonWidth / 2);
        mCenterY = bottom / 2;

        mScaleSize = initScaleSize = 1 - mStrokeWidth / mButtonHeight;

        RectF rect = new RectF(0, 0, bottom, bottom);
        mPath.arcTo(rect, 90, 180);
        rect.left = w - bottom;
        rect.right = w;
        mPath.arcTo(rect, 270, 180);
        mPath.close();

        RectF animRect = new RectF(0, 0, bottom, bottom);
        mAnimPath.arcTo(animRect, 90, 180);
        animRect.left = w - bottom;
        animRect.right = w;
        mAnimPath.arcTo(rect, 270, 180);
        mAnimPath.close();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:
            mPointX = event.getX();
            break;
        case MotionEvent.ACTION_MOVE:
            float tempX = event.getX();

            mMoveX = tempX - mPointX;

            if (mMoveX > 0) {
                direction = Direction.RIGHT;
            } else {
                direction = Direction.LETF;
            }

            if (Math.abs(mMoveX) > 20 && !isAnim) {
                isAnim = true;
                this.invalidate();
            }

            break;
        }

        return true;
    }

}
