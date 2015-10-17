package com.shixing.studycode.customview.shader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.shixing.study.R;
import com.shixing.studycode.utils.MeasureUtil;

public class ShaderView extends View {
    private static final int RECT_SIZE = 400;// 矩形尺寸的一半

    private final Paint mPaint;// 画笔

    private final int left, top, right, bottom;// 矩形坐上右下坐标

    public ShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取屏幕尺寸数据
        int[] screenSize = MeasureUtil.getScreenSize((Activity) context);

        // 获取屏幕中点坐标
        int screenX = screenSize[0] / 2;
        int screenY = screenSize[1] / 2;

        // 计算矩形左上右下坐标值
        left = screenX - RECT_SIZE;
        top = screenY - RECT_SIZE;
        right = screenX + RECT_SIZE;
        bottom = screenY + RECT_SIZE;

        // 实例化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        // 获取位图
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.shishi);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

         Matrix matrix = new Matrix();
         matrix.setTranslate(left, top);
         shader.setLocalMatrix(matrix);

      // 设置着色器
         mPaint.setShader(shader);
        // mPaint.setShader(new SweepGradient(screenX, screenY, Color.RED,
        // Color.YELLOW));

        // mPaint.setShader(new SweepGradient(screenX, screenY, new int[] {
        // Color.GREEN, Color.WHITE, Color.GREEN }, null));
        // RadialGradient (float centerX, float centerY, float radius, int
        // centerColor, int edgeColor, Shader.TileMode tileMode)
//        mPaint.setShader(new RadialGradient(screenX, screenY, 100, Color.GREEN,
//                Color.RED, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制矩形
        canvas.drawRect(left, top, right, bottom, mPaint);
    }
}
