package com.shixing.studycode.customview.shader;

import com.shixing.studycode.utils.MeasureUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class LayerView extends View {

    private static final int LAYER_FLAGS = Canvas.MATRIX_SAVE_FLAG
            | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
            | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
            | Canvas.CLIP_TO_LAYER_SAVE_FLAG;

    private final Paint mPaint;
    int screenX;
    int screenY;

    public LayerView(Context context, AttributeSet attrs) {
        super(context);
        setFocusable(true);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        initPosition(context);
    }

    private void initPosition(Context context) {
        int[] screenSize = MeasureUtil.getScreenSize((Activity) context);

        // 获取屏幕中点坐标
        screenX = screenSize[0] / 2;
        screenY = screenSize[1] / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.YELLOW);
        canvas.translate(10, 10);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(screenX, screenY, 100, mPaint);
        canvas.saveLayerAlpha(screenX - 100, screenY - 100, screenX + 100,
                screenY + 100, 0x88, LAYER_FLAGS);
        canvas.drawColor(Color.GREEN);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(screenX, screenY, 100, mPaint);
        canvas.restore();

        mPaint.setColor(Color.DKGRAY);
        canvas.drawCircle(screenX - 50, screenY - 50, 100, mPaint);
    }
}
