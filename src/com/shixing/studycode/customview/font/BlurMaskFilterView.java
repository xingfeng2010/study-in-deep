package com.shixing.studycode.customview.font;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.shixing.study.R;
import com.shixing.studycode.utils.MeasureUtil;

public class BlurMaskFilterView extends View {

    private final Context mContext;
    private Paint shadowPaint;
    private Bitmap srcBitmap;
    private Bitmap shadowBitmap;
    private int x;
    private int y;

    public BlurMaskFilterView(Context context) {
        this(context, null);
    }

    public BlurMaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        // 记得设置模式为SOFTWARE
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);

        // 初始化画笔
        this.initPaint();

        // 初始化资源
        this.initRes(context);
    }

    private void initRes(Context context) {
        // 获取位图
        this.srcBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.shishi);

        // 获取位图的Alpha通道图
        this.shadowBitmap = this.srcBitmap.extractAlpha();

        /* 
         * 计算位图绘制时左上角的坐标使其位于屏幕中心 
         */
        this.x = MeasureUtil.getScreenSize((Activity) this.mContext)[0] / 2
                - this.srcBitmap.getWidth() / 2;
        this.y = MeasureUtil.getScreenSize((Activity) this.mContext)[1] / 2
                - this.srcBitmap.getHeight() / 2;

    }

    private void initPaint() {
        // 实例化画笔
        this.shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        this.shadowPaint.setColor(Color.DKGRAY);
        this.shadowPaint.setMaskFilter(new BlurMaskFilter(50,
                BlurMaskFilter.Blur.NORMAL));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        canvas.drawBitmap(this.shadowBitmap, this.x, this.y, this.shadowPaint);
        canvas.drawBitmap(this.srcBitmap, this.x, this.y, null);
    }

}
