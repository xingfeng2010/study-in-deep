package com.shixing.studycode.customview.font;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ECGView extends View {
    private final Paint mPaint;// 画笔
    private final Path mPath;// 路径对象

    private int screenW, screenH;// 屏幕宽高
    private float x, y;// 路径初始坐标
    private float initScreenW;// 屏幕初始宽度
    private float initX;// 初始X轴坐标
    private float transX, moveX;// 画布移动的距离

    private boolean isCanvasMove;// 画布是否需要平移

    public ECGView(Context context, AttributeSet set) {
        super(context, set);

        /*
         * 实例化画笔并设置属性
         */
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        this.mPaint.setColor(Color.GREEN);
        this.mPaint.setStrokeWidth(5);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setShadowLayer(7, 0, 0, Color.GREEN);

        this.mPath = new Path();
        this.transX = 0;
        this.isCanvasMove = false;
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i("ECGView", "onSizeChanged");
        /*
        * 获取屏幕宽高
        */
        this.screenW = w;
        this.screenH = h;

        /*
        * 设置起点坐标
        */
        this.x = 0;
        this.y = (this.screenH / 2) + (this.screenH / 4) + (this.screenH / 10);

        // 屏幕初始宽度
        this.initScreenW = this.screenW;

        // 初始X轴坐标
        this.initX = ((this.screenW / 2) + (this.screenW / 4));

        this.moveX = (this.screenW / 24);

        this.mPath.moveTo(this.x, this.y);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Log.i("ECGView", "onDraw");
        canvas.drawColor(Color.BLACK);

        this.mPath.lineTo(this.x, this.y);

        // 向左平移画布
        canvas.translate(-this.transX, 0);

        // 计算坐标
        this.calCoors();

        // 绘制路径
        canvas.drawPath(this.mPath, this.mPaint);
        this.invalidate();
    }

    /**
     * 计算坐标
     */
    private void calCoors() {
        if (this.isCanvasMove == true) {
            this.transX += 4;
        }

        if (this.x < this.initX) {
            this.x += 8;
        } else {
            if (this.x < this.initX + this.moveX) {
                this.x += 2;
                this.y -= 8;
            } else {
                if (this.x < this.initX + (this.moveX * 2)) {
                    this.x += 2;
                    this.y += 14;
                } else {
                    if (this.x < this.initX + (this.moveX * 3)) {
                        this.x += 2;
                        this.y -= 12;
                    } else {
                        if (this.x < this.initX + (this.moveX * 4)) {
                            this.x += 2;
                            this.y += 6;
                        } else {
                            if (this.x < this.initScreenW) {
                                this.x += 8;
                            } else {
                                this.isCanvasMove = true;
                                this.initX = this.initX + this.initScreenW;
                            }
                        }
                    }
                }
            }

        }
    }
}
