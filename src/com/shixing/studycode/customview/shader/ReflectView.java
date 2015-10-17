package com.shixing.studycode.customview.shader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.shixing.study.R;
import com.shixing.studycode.utils.MeasureUtil;

public class ReflectView extends View {

    private Bitmap mSrcBitmap, mRefBitmap;// 位图
    private Paint mPaint;// 画笔
    private PorterDuffXfermode mXfermode;// 混合模式

    private int x, y;// 位图起点坐标

    public ReflectView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initRes(context);
    }

    private void initRes(Context context) {
        // 获取源图
        mSrcBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.liying);

        // 实例化一个矩阵对象
        Matrix matrix = new Matrix();
        matrix.setScale(1F, -1F);

        // 生成倒影图
        mRefBitmap = Bitmap.createBitmap(mSrcBitmap, 0, 0,
                mSrcBitmap.getWidth(), mSrcBitmap.getHeight(), matrix, true);

        int screenW = MeasureUtil.getScreenSize((Activity) context)[0];
        int screenH = MeasureUtil.getScreenSize((Activity) context)[1];

        x = screenW / 2 - mSrcBitmap.getWidth() / 2;
        y = screenH / 2 - mSrcBitmap.getHeight() / 2;

        // ………………………………
        mPaint = new Paint();
        mPaint.setShader(new LinearGradient(x, y + mSrcBitmap.getHeight(), x, y
                + mSrcBitmap.getHeight() + mSrcBitmap.getHeight(), 0xAA000000,
                Color.TRANSPARENT, Shader.TileMode.CLAMP));

        // ………………………………
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mSrcBitmap, x, y, null);

        int sc = canvas.saveLayer(x, y + mSrcBitmap.getHeight(),
                x + mRefBitmap.getWidth(), y + mSrcBitmap.getHeight() * 2,
                null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mRefBitmap, x, y + mSrcBitmap.getHeight(), null);

        mPaint.setXfermode(mXfermode);

        canvas.drawRect(x, y + mSrcBitmap.getHeight(),
                x + mRefBitmap.getWidth(), y + mSrcBitmap.getHeight() * 2,
                mPaint);

        mPaint.setXfermode(null);

        canvas.restoreToCount(sc);
    }

}
