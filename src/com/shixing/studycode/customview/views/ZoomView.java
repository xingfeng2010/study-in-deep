package com.shixing.studycode.customview.views;

import com.shixing.study.R;
import com.shixing.studycode.utils.MeasureUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class ZoomView extends ImageView implements OnScaleGestureListener,
        OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {

    private Bitmap mBitmap;
    private Matrix mMatrix;

    private final float[] matrixValues = new float[9];
    private static final float MAX_SCALE = 4f;
    private static final String TAG = "ZoomView";
    private float initScale = 0.25f;

    private int mScreenWidth;
    private int mScreenHeight;

    private int mCenterX;
    private int mCenterY;

    private float mLastX;
    private float mLastY;

    /**
     * 缩放的手势检测
     */
    private ScaleGestureDetector mScaleGestureDetector = null;
    private boolean once = true;
    private int lastPointerCount;
    private boolean isCanDrag = true;
    private boolean isCheckTopAndBottom;
    private boolean isCheckLeftAndRight;
    private double mTouchSlop = 10;
    protected float SCALE_MID = 2.0f;
    protected float SCALE_MAX = 4.0f;
    protected boolean isAutoScale;
    private GestureDetector mGestureDetector;

    public ZoomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.fairy);
        mMatrix = new Matrix();

        mScreenWidth = MeasureUtil.getScreenSize((Activity) context)[0];
        mScreenHeight = MeasureUtil.getScreenSize((Activity) context)[1];

        mCenterX = mScreenWidth / 2;
        mCenterY = mScreenHeight / 2;

        /**
         * 因为GestureDetector设置监听器的话，方法一大串，而我们只需要onDoubleTap这个回调，
         * 所以我们准备使用它的一个内部类SimpleOnGestureListener，对接口的其他方法实现了空实现。
         */
        mGestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        if (isAutoScale == true)
                            return true;

                        float x = e.getX();
                        float y = e.getY();
                        Log.e("DoubleTap", getScale() + " , " + initScale);
                        if (getScale() < SCALE_MID) {
                            ZoomView.this.postDelayed(new AutoScaleRunnable(
                                    SCALE_MID, x, y), 16);
                            isAutoScale = true;
                        } else if (getScale() >= SCALE_MID
                                && getScale() < SCALE_MAX) {
                            ZoomView.this.postDelayed(new AutoScaleRunnable(
                                    SCALE_MAX, x, y), 16);
                            isAutoScale = true;
                        } else {
                            ZoomView.this.postDelayed(new AutoScaleRunnable(
                                    initScale, x, y), 16);
                            isAutoScale = true;
                        }

                        return true;
                    }
                });
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        super.setScaleType(ScaleType.MATRIX);

        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        this.setOnTouchListener(this);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float factor = detector.getScaleFactor();

        android.util.Log.i("shixing", "ZoomView scale=" + scale + "::factor="
                + factor);

        if ((factor > 1.0f && scale < MAX_SCALE)
                || (factor < 1.0f && scale > initScale)) {
            if (factor * scale > MAX_SCALE) {
                factor = MAX_SCALE / scale;
            }

            if (factor * scale < initScale) {
                factor = initScale / scale;
            }

            android.util.Log.i("shixing", "ZoomView scale come in");
            mMatrix.postScale(factor, factor, detector.getFocusX(),
                    detector.getFocusY());
            setImageMatrix(mMatrix);
        }

        checkBorderAndCenterWhenScale();

        return true;
    }

    /**
     * 在缩放时，进行图片显示范围的控制
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = mScreenWidth;
        int height = mScreenHeight;

        // 如果宽或高大于屏幕,则控制范围
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }

        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }

        // 如果宽或高小于屏幕，则让其居中
        if (rect.width() < width) {
            deltaX = -(rect.left + rect.right) / 2.0f + mCenterX;
        }

        if (rect.height() < height) {
            deltaY = -(rect.top + rect.bottom) / 2.0f + mCenterX;
        }
        mMatrix.postTranslate(deltaX, deltaY);
        this.setImageMatrix(mMatrix);
    }

    private RectF getMatrixRectF() {
        Matrix matrix = mMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }

    private float getScale() {
        mMatrix.getValues(matrixValues);
        float temp = matrixValues[Matrix.MSCALE_X];
        return temp;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        // 一定要返回true才会进入onScale()这个函数
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        // 返回给ScaleGestureDetector来处理
        mScaleGestureDetector.onTouchEvent(event);
        mGestureDetector.onTouchEvent(event);

        float x = 0, y = 0;
        // 拿到触摸点的个数
        final int pointerCount = event.getPointerCount();
        // 得到多个触摸点的x与y均值
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x = x / pointerCount;
        y = y / pointerCount;

        /**
         * 每当触摸点发生变化时，重置mLasX , mLastY
         */
        if (pointerCount != lastPointerCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }

        lastPointerCount = pointerCount;
        RectF rectF = getMatrixRectF();

        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:

            if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
            Log.e(TAG, "ACTION_UP");
            lastPointerCount = 0;
            break;
        case MotionEvent.ACTION_MOVE:
            Log.e(TAG, "ACTION_MOVE");
            float dx = x - mLastX;
            float dy = y - mLastY;

            /**
             * 宽或高大于屏幕宽或高时，因为此时可以移动，我们不想被拦截
             */
            if (rectF.width() > mScreenWidth || rectF.height() > mScreenHeight) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }

            // if (!isCanDrag) {
            // isCanDrag = isCanDrag(dx, dy);
            // }
            // if (isCanDrag) {
            // RectF rectF = getMatrixRectF();
            if (getDrawable() != null) {
                if (getMatrixRectF().left == 0 && dx > 0) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                if (getMatrixRectF().right == getWidth() && dx < 0) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                isCheckLeftAndRight = isCheckTopAndBottom = true;
                // 如果宽度小于屏幕宽度，则禁止左右移动
                if (rectF.width() < mScreenWidth) {
                    dx = 0;
                    isCheckLeftAndRight = false;
                }
                // 如果高度小雨屏幕高度，则禁止上下移动
                if (rectF.height() < mScreenHeight) {
                    dy = 0;
                    isCheckTopAndBottom = false;
                }
                mMatrix.postTranslate(dx, dy);
                checkMatrixBounds();
                setImageMatrix(mMatrix);
            }
            // }
            mLastX = x;
            mLastY = y;
            break;
        }

        return true;
    }

    @Override
    public void onGlobalLayout() {
        if (once) {
            Drawable d = getDrawable();
            if (d == null)
                return;
            Log.e("ZoomView",
                    d.getIntrinsicWidth() + " , " + d.getIntrinsicHeight());
            // int width = getWidth();
            // int height = getHeight();
            int width = mScreenWidth;
            int height = mScreenHeight;
            // 拿到图片的宽和高
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();
            float scale = 1.0f;
            // 如果图片的宽或者高大于屏幕，则缩放至屏幕的宽或者高
            if (dw > width && dh <= height) {
                scale = width * 1.0f / dw;
            }
            if (dh > height && dw <= width) {
                scale = height * 1.0f / dh;
            }
            // 如果宽和高都大于屏幕，则让其按按比例适应屏幕大小
            if (dw > width && dh > height) {
                scale = Math.min(dw * 1.0f / width, dh * 1.0f / height);
            }
            // initScale = scale;
            // 图片移动至屏幕中心
            mMatrix.postTranslate((width - dw) / 2.0f, (height - dh) / 2.0f);
            mMatrix.postScale(scale, scale, width / 2.0f, height / 2.0f);
            setImageMatrix(mMatrix);
            once = false;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    private void moveImage(float moveX, float moveY) {
        mMatrix.postTranslate(moveX, moveY);
        setImageMatrix(mMatrix);
    }

    /**
     * 移动时，进行边界判断，主要判断宽或高大于屏幕的
     */
    private void checkMatrixBounds() {
        RectF rect = getMatrixRectF();

        float deltaX = 0, deltaY = 0;
        final float viewWidth = mScreenWidth;
        final float viewHeight = mScreenHeight;
        // 判断移动或缩放后，图片显示是否超出屏幕边界
        if (rect.top > 0 && isCheckTopAndBottom) {
            deltaY = -rect.top;
        }
        if (rect.bottom < viewHeight && isCheckTopAndBottom) {
            deltaY = viewHeight - rect.bottom;
        }
        if (rect.left > 0 && isCheckLeftAndRight) {
            deltaX = -rect.left;
        }
        if (rect.right < viewWidth && isCheckLeftAndRight) {
            deltaX = viewWidth - rect.right;
        }
        mMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 是否是推动行为
     * @param dx
     * @param dy
     * @return
     */
    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
    }

    /**
     * 自动缩放的任务
     * @author lsx
     */
    private class AutoScaleRunnable implements Runnable {
        static final float BIGGER = 1.07f;
        static final float SMALLER = 0.93f;
        private float mTargetScale;
        private float tmpScale;

        /**
         * 缩放的中心
         */
        private float x;
        private float y;

        /**
         * 传入目标缩放值，根据目标值与当前值，判断应该放大还是缩小
         * @param targetScale
         */
        public AutoScaleRunnable(float targetScale, float x, float y) {
            this.mTargetScale = targetScale;
            this.x = x;
            this.y = y;
            if (getScale() < mTargetScale) {
                tmpScale = BIGGER;
            } else {
                tmpScale = SMALLER;
            }

        }

        @Override
        public void run() {
            // 进行缩放
            mMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mMatrix);

            final float currentScale = getScale();
            // 如果值在合法范围内，继续缩放
            if (((tmpScale > 1f) && (currentScale < mTargetScale))
                    || ((tmpScale < 1f) && (mTargetScale < currentScale))) {
                ZoomView.this.postDelayed(this, 16);
            } else// 设置为目标的缩放比例
            {
                final float deltaScale = mTargetScale / currentScale;
                mMatrix.postScale(deltaScale, deltaScale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mMatrix);
                isAutoScale = false;
            }

        }
    }
}
