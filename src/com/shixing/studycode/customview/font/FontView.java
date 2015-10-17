package com.shixing.studycode.customview.font;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class FontView extends View {

    private static final String TEXT = "ap爱哥ξτβбпшㄎㄊ";

    private Paint textPaint, linePaint;// 文本的画笔和中心的画笔

    private int baseX, baseY;// Baseline绘制的XY坐标

    public FontView(Context context) {
        this(context, null);
    }

    public FontView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化画笔
        this.inintPaint();
    }

    private void inintPaint() {
        // instance paint
        this.textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.textPaint.setTextSize(70);
        this.textPaint.setColor(Color.BLACK);

        this.linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.linePaint.setStyle(Paint.Style.STROKE);
        this.linePaint.setStrokeWidth(1);
        this.linePaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 计算Baseline绘制的起点X轴坐标
        this.baseX = (int) (canvas.getWidth() / 2 - this.textPaint
                .measureText(TEXT) / 2);

        // 计算Baseline绘制的Y坐标
        this.baseY = (int) ((canvas.getHeight() / 2) - ((this.textPaint
                .descent() + this.textPaint.ascent()) / 2));

        canvas.drawText(TEXT, this.baseX, this.baseY, this.textPaint);

        // 为了便于理解我们在画布中心处绘制一条中线
        canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(),
                canvas.getHeight() / 2, this.linePaint);
    }

}
