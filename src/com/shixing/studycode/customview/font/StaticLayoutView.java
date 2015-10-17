package com.shixing.studycode.customview.font;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class StaticLayoutView extends View {
    private static final String TEXT = "Test font size";
    private TextPaint mTextPaint;// 画笔
    private StaticLayout mStaticLayout;// 文本布局

    public StaticLayoutView(Context context) {
        this(context, null);
    }

    public StaticLayoutView(Context context, AttributeSet attrs) {
        super(context);

        this.initPaint(context);
    }

    private void initPaint(Context context) {
        this.mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        this.mTextPaint.setTextSize(20);
        this.mTextPaint.setColor(Color.BLACK);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                "fangzhengzhunyuan.ttf");
        this.mTextPaint.setTypeface(typeface);
        this.mTextPaint.setTextScaleX(2f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mStaticLayout = new StaticLayout(TEXT, this.mTextPaint,
                canvas.getWidth(), Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
        this.mStaticLayout.draw(canvas);
        canvas.restore();
    }

}
