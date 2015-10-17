
package com.shixing.studycode.customview.lighting;

import java.util.Random;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CustomPaint {

    public Paint mLighnitngColorPaint = new Paint();// ç»†çº¿
    public Paint mLighnitngColorPaintBold = new Paint();// çª„é˜´å½?
    public Paint mLighnitngGlowPaint = new Paint();// ç²—çº¿
    public Paint mLighnitngGlowPaintBold = new Paint();// å®½é˜´å½?

    public CustomPaint() {
        this.mLighnitngColorPaint.setAntiAlias(true);
        this.mLighnitngColorPaint.setDither(true);
        this.mLighnitngColorPaint.setColor(Color.argb(248, 255, 255, 255));
        this.mLighnitngColorPaint.setStrokeWidth(1.0F);
        this.mLighnitngColorPaint.setStyle(Paint.Style.STROKE);
        this.mLighnitngColorPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mLighnitngColorPaint.setStrokeCap(Paint.Cap.ROUND);

        this.mLighnitngGlowPaint.set(this.mLighnitngColorPaint);
        this.mLighnitngGlowPaint.setColor(Color.parseColor("#6669FD"));
        this.mLighnitngGlowPaint.setAlpha(235);
        this.mLighnitngGlowPaint.setStrokeWidth(7.0F);
        this.mLighnitngGlowPaint.setMaskFilter(new BlurMaskFilter(15.0F,
                BlurMaskFilter.Blur.NORMAL));

        this.mLighnitngColorPaintBold.setAntiAlias(true);
        this.mLighnitngColorPaintBold.setDither(true);
        this.mLighnitngColorPaintBold.setColor(Color.argb(248, 255, 255, 255));
        this.mLighnitngColorPaintBold.setStrokeWidth(3.0F);
        this.mLighnitngColorPaintBold.setStyle(Paint.Style.STROKE);
        this.mLighnitngColorPaintBold.setStrokeJoin(Paint.Join.ROUND);
        this.mLighnitngColorPaintBold.setStrokeCap(Paint.Cap.ROUND);

        this.mLighnitngGlowPaintBold.set(this.mLighnitngColorPaintBold);
        this.mLighnitngGlowPaintBold.setColor(Color.parseColor("#6669FD"));
        this.mLighnitngGlowPaintBold.setAlpha(235);
        this.mLighnitngGlowPaintBold.setStrokeWidth(10.0F);
        this.mLighnitngGlowPaintBold.setMaskFilter(new BlurMaskFilter(15.0F,
                BlurMaskFilter.Blur.NORMAL));

    }

    // ç”»ç»†çº¿ã?é˜´å½±ã€?
    public void drawLightning(float x1, float y1, float x2, float y2,
            int paramInt, Canvas paramCanvas) {
        Random localRandom = new Random();
        if (paramInt < localRandom.nextInt(7)) {
            paramCanvas.drawLine(x1, y1, x2, y2, this.mLighnitngColorPaint);
            paramCanvas.drawLine(x1, y1, x2, y2, this.mLighnitngColorPaint);
            paramCanvas.drawLine(x1, y1, x2, y2, this.mLighnitngGlowPaintBold);
            return;
        }
        float x3 = 0, y3 = 0;
        if (localRandom.nextBoolean()) {
            x3 = (float) ((x2 + x1) / 2.0F + ((localRandom.nextInt(8) - 0.5D) * paramInt));
        } else {
            x3 = (float) ((x2 + x1) / 2.0F - ((localRandom.nextInt(8) - 0.5D) * paramInt));
        }
        if (localRandom.nextBoolean()) {
            y3 = (float) ((y2 + y1) / 2.0F + ((localRandom.nextInt(5) - 0.5D) * paramInt));
        } else {
            y3 = (float) ((y2 + y1) / 2.0F - ((localRandom.nextInt(5) - 0.5D) * paramInt));
        }
        this.drawLightning(x1, y1, x3, y3, paramInt / 2, paramCanvas);
        this.drawLightning(x2, y2, x3, y3, paramInt / 2, paramCanvas);
        return;

    }

    // ç²—çº¿ã€é˜´å½?
    public void drawLightningBold(float x1, float y1, float x2, float y2,
            int paramInt, Canvas paramCanvas) {
        Random localRandom = new Random();
        if (paramInt < localRandom.nextInt(7)) {
            paramCanvas.drawLine(x1, y1, x2, y2, this.mLighnitngColorPaintBold);
            paramCanvas.drawLine(x1, y1, x2, y2, this.mLighnitngColorPaint);
            paramCanvas.drawLine(x1, y1, x2, y2, this.mLighnitngGlowPaintBold);
            return;
        }

        float x3 = 0, y3 = 0;
        boolean x3Z = localRandom.nextBoolean();
        if (x3Z) {
            x3 = (float) ((x2 + x1) / 2.0F + ((localRandom.nextInt(8) - 0.5D) * paramInt));
        } else {
            x3 = (float) ((x2 + x1) / 2.0F - ((localRandom.nextInt(8) - 0.5D) * paramInt));
        }

        if (localRandom.nextBoolean()) {
            y3 = (float) ((y2 + y1) / 2.0F + ((localRandom.nextInt(5) - 0.5D) * paramInt));
        } else {
            y3 = (float) ((y2 + y1) / 2.0F - ((localRandom.nextInt(5) - 0.5D) * paramInt));
        }
        this.drawLightningBold(x1, y1, x3, y3, paramInt / 2, paramCanvas);
        this.drawLightningBold(x2, y2, x3, y3, paramInt / 2, paramCanvas);
        return;

    }
}
