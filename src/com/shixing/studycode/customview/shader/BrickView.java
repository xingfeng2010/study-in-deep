package com.shixing.studycode.customview.shader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.shixing.study.R;
import com.shixing.studycode.utils.MeasureUtil;

public class BrickView extends View {
    private static final int CIRCLE_RADIUS = 150;
    private Paint mFillPaint, mStrokePaint;// 填充和描边的画笔
    private BitmapShader mBitmapShader;// Bitmap着色器
    PorterDuffXfermode src = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    PorterDuffXfermode dst = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

    private float posX, poxY;// 触摸点的XY坐标

    Bitmap mBitmap;
    Bitmap mBallBitmap;

    public BrickView(Context context, AttributeSet attr) {
        super(context, attr);

        // 初始化画笔
        initPosition(context);
        initPaint();
    }

    private void initPosition(Context context) {
        int[] screenSize = MeasureUtil.getScreenSize((Activity) context);

        this.posX = screenSize[0] / 2;
        this.poxY = screenSize[1] / 2;
    }

    private void initPaint() {
        /**
         * 实例化描边画笔并设置参数
         */
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mStrokePaint.setColor(0xFF000000);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(5);

        // 实例化填充画笔
        mFillPaint = new Paint();

        // 生成BitmapShader
        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.shishi);
        mBallBitmap = getRoundedBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 设置画笔颜色

        if (mBallBitmap != null) {
            canvas.drawBitmap(mBallBitmap, posX, poxY, mFillPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            posX = event.getX();
            poxY = event.getY();

            this.invalidate();
        }
        return true;
    }

    // 图片圆角处理
    public Bitmap getRoundedBitmap() {
        Bitmap mBitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.shishi);
        // create new bitmap
        Bitmap bgBitmap = Bitmap.createBitmap(mBitmap.getWidth(),
                mBitmap.getHeight(), Config.ARGB_8888);
        // 把创建的位图作为画板
        Canvas mCanvas = new Canvas(bgBitmap);

        Paint mPaint = new Paint();
        Rect mRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());

        mCanvas.drawCircle(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2,
                CIRCLE_RADIUS, mPaint);

        // 设置图像的叠加模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 绘制图像
        mCanvas.drawBitmap(mBitmap, mRect, mRect, mPaint);

        mCanvas.drawCircle(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2,
                CIRCLE_RADIUS, mStrokePaint);
        return bgBitmap;
    }

}
