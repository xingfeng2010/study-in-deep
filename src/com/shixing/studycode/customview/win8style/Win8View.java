package com.shixing.studycode.customview.win8style;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class Win8View extends ImageView {

    private int mViewWidth;
    private int mViewHeight;
    private Camera mCamera;
    private float mScale = 0.95f;
    private float mDegree = 10;

    private static final int SCALE_BEGIN = 1;
    private static final int SCALE_CONTINUE = 2;
    private static final int SCALE_RESTORE = 3;

    private static final int TRANSLATE_BEGIN = 4;
    private static final int TRANSLATE_CONTINUE = 5;
    private static final int TRANSLATE_RESTORE = 6;

    private boolean isScale;
    private boolean isXMoreThanY = false;

    private float mRotateX;
    private float mRotateY;

    public Win8View(Context context) {
        super(context);

        mCamera = new Camera();
    }

    public Win8View(Context context, AttributeSet attrs) {
        super(context, attrs);

        mCamera = new Camera();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:

            float pointX = event.getX();
            float pointY = event.getY();

            mRotateX = mViewWidth / 2 - pointX;
            mRotateY = mViewHeight / 2 - pointY;

            isXMoreThanY = Math.abs(mRotateX) > Math.abs(mRotateY) ? true
                    : false;

            int sideWidth = mViewWidth / 3;
            int sideHeight = mViewHeight / 3;

            if (pointX > sideWidth && pointX < sideWidth * 2
                    && pointY > sideHeight && pointY < sideHeight * 2) {
                isScale = true;
            } else {
                isScale = false;
            }

            if (isScale) {
                mScaleHandler.sendEmptyMessage(SCALE_BEGIN);
            } else {
                mTranslateHandler.sendEmptyMessage(TRANSLATE_BEGIN);
            }
            break;
        case MotionEvent.ACTION_MOVE:
            break;
        case MotionEvent.ACTION_UP:
            android.util.Log.i("shixing", "onTouchEvent aciton up");
            if (isScale) {
                mScaleHandler.sendEmptyMessage(SCALE_RESTORE);
            } else {
                mTranslateHandler.sendEmptyMessage(TRANSLATE_RESTORE);
            }

            break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewWidth = this.getWidth() - this.getPaddingLeft()
                - this.getPaddingRight();
        mViewHeight = this.getHeight() - this.getPaddingBottom()
                - this.getPaddingTop();

        Drawable drawable = getDrawable();
        BitmapDrawable bd = (BitmapDrawable) drawable;
        bd.setAntiAlias(true);

        android.util.Log.i("shixing", "Win8View onSizeChanged");
    }

    private Handler mScaleHandler = new Handler() {
        private Matrix matrix = new Matrix();
        private float s;
        private int count;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            matrix.set(Win8View.this.getImageMatrix());
            switch (msg.what) {
            case 1:
                s = (float) Math.sqrt(Math.sqrt(mScale));
                beginScale(matrix, s);
                count = 0;
                mScaleHandler.sendEmptyMessage(SCALE_CONTINUE);
                break;
            case SCALE_CONTINUE:
                beginScale(matrix, s);
                if (count < 4) {
                    mScaleHandler.sendEmptyMessage(SCALE_CONTINUE);
                }
                count++;
                break;
            case 3:
                s = (float) Math.sqrt(Math.sqrt(1.0f / mScale));
                beginScale(matrix, s);
                count = 0;
                mScaleHandler.sendEmptyMessage(SCALE_CONTINUE);
                break;
            }

        }

    };

    private Handler mTranslateHandler = new Handler() {
        private Matrix matrix = new Matrix();
        private float degree = 0;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            matrix.set(Win8View.this.getImageMatrix());
            switch (msg.what) {
            case TRANSLATE_BEGIN:
                degree = 0;
                beginTranslate(matrix, isXMoreThanY ? degree : 0,
                        isXMoreThanY ? 0 : degree);
                mTranslateHandler.sendEmptyMessage(TRANSLATE_CONTINUE);
                break;
            case TRANSLATE_CONTINUE:
                beginTranslate(matrix, isXMoreThanY ? degree : 0,
                        isXMoreThanY ? 0 : degree);
                if (degree < mDegree) {
                    mTranslateHandler.sendEmptyMessage(TRANSLATE_CONTINUE);
                }

                degree++;
                degree++;
                break;
            case TRANSLATE_RESTORE:
                beginTranslate(matrix, isXMoreThanY ? degree : 0,
                        isXMoreThanY ? 0 : degree);
                if (degree > 0) {
                    mTranslateHandler.sendEmptyMessage(TRANSLATE_RESTORE);
                }

                degree--;
                degree--;
                break;
            }
        }

    };

    protected void beginScale(Matrix matrix, float scaleSize) {
        matrix.postScale(scaleSize, scaleSize, mViewWidth / 2, mViewHeight / 2);
        Win8View.this.setImageMatrix(matrix);
    }

    protected void beginTranslate(Matrix matrix, float rolateX, float rolateY) {
        int scaleX = mViewWidth / 2;
        int scaleY = mViewHeight / 2;

        mCamera.save();
        mCamera.rotateX(mRotateY > 0 ? rolateY : -rolateY);
        mCamera.rotateY(mRotateX > 0 ? rolateX : -rolateX);
        mCamera.getMatrix(matrix);
        mCamera.restore();

        //控制中心点
        if (mRotateX > 0 && rolateX != 0) {
            matrix.preTranslate(-mViewWidth, -scaleY);
            matrix.postTranslate(mViewWidth, scaleY);
        } else if (mRotateY > 0 && rolateY != 0) {
            matrix.preTranslate(-scaleX, -mViewHeight);
            matrix.postTranslate(scaleX, mViewHeight);
        } else if (mRotateX < 0 && rolateX != 0) {
            matrix.preTranslate(-0, -scaleY);
            matrix.postTranslate(0, scaleY);
        } else if (mRotateY < 0 && rolateY != 0) {
            matrix.preTranslate(-scaleX, -0);
            matrix.postTranslate(scaleX, 0);
        }

        setImageMatrix(matrix);
    }

}
