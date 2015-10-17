package com.shixing.studycode.customview.font;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("DrawAllocation")
public class PathEffectView extends View {

    private float mPhase;// 偏移值
    private final Paint mPaint;// 画笔对象
    private final Path mPath;// 路径对象
    private final PathEffect[] mEffects;// 路径效果数组

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*
        * 实例化各类特效
        */
        this.mEffects[0] = null;
        this.mEffects[1] = new CornerPathEffect(10);
        this.mEffects[2] = new DiscretePathEffect(3.0F, 5.0F);
        this.mEffects[3] = new DashPathEffect(new float[] { 20, 10, 5, 10 },
                this.mPhase);
        Path path = new Path();
        path.addRect(0, 0, 8, 8, Path.Direction.CCW);
        this.mEffects[4] = new PathDashPathEffect(path, 12, this.mPhase,
                PathDashPathEffect.Style.ROTATE);
        this.mEffects[5] = new ComposePathEffect(this.mEffects[2],
                this.mEffects[4]);
        this.mEffects[6] = new SumPathEffect(this.mEffects[4], this.mEffects[3]);

        /*
        * 绘制路径
        */
        for (int i = 0; i < this.mEffects.length; i++) {
            this.mPaint.setPathEffect(this.mEffects[i]);
            canvas.drawPath(this.mPath, this.mPaint);

            // 每绘制一条将画布向下平移250个像素
            canvas.translate(0, 250);
        }

        // 刷新偏移值并重绘视图实现动画效果
        this.mPhase += 1;
        this.invalidate();

    }

    public PathEffectView(Context context) {
        this(context, null);
    }

    public PathEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);

        /* 
         * 实例化画笔并设置属性 
         */
        this.mPaint = new Paint();
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(5);
        this.mPaint.setColor(Color.DKGRAY);

        // 实例化路径
        this.mPath = new Path();

        // 定义路径的起点
        this.mPath.moveTo(0, 0);

        // 定义路径的各个点
        for (int i = 0; i <= 30; i++) {
            this.mPath.lineTo(i * 35, (float) (Math.random() * 100));
        }

        // 创建路径效果数组
        this.mEffects = new PathEffect[7];
    }
}
