package com.shixing.studycode.customview.font;

import android.app.Activity;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.shixing.studycode.utils.MeasureUtil;

public class MaskFilterView extends View {
    private static final int RECT_SIZE = 800;
    private final Context mContext;
    private int left, top, right, bottom;

    private Paint mPaint;

    public MaskFilterView(Context context) {
        this(context, null);
    }

    public MaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        // 初始化画笔
        this.initPaint();

        // 初始化资源
        this.initRes(context);
    }

    private void initRes(Context context) {
        /* 
         * 计算位图绘制时左上角的坐标使其位于屏幕中心 
         */
        this.left = MeasureUtil.getScreenSize((Activity) this.mContext)[0] / 2
                - RECT_SIZE / 2;
        this.top = MeasureUtil.getScreenSize((Activity) this.mContext)[1] / 2
                - RECT_SIZE / 2;
        this.right = MeasureUtil.getScreenSize((Activity) this.mContext)[0] / 2
                + RECT_SIZE / 2;
        this.bottom = MeasureUtil.getScreenSize((Activity) this.mContext)[1]
                / 2 + RECT_SIZE / 2;

    }

    private void initPaint() {
        // 实例化画笔
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(0xFF603811);

        // 设置画笔遮罩滤镜
        this.mPaint.setMaskFilter(new BlurMaskFilter(20,
                BlurMaskFilter.Blur.SOLID));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);

        // 画一个矩形
        canvas.drawRect(this.left, this.top, this.right, this.bottom,
                this.mPaint);
    }

}
