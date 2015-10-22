package com.shixing.studycode.customview.views;

import com.shixing.study.R;
import com.shixing.studycode.utils.MeasureUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GuaguakaView extends View {

	private Bitmap mSrcBitmap;
	private Bitmap mFGBitmap;
	private Paint mPaint;
	private Paint mLinePaint;
	private PorterDuffXfermode mPorterDuffXfermode;
	private Path mPath;
    private int mWidth,mHeight;
    private Rect mRect;
    private Canvas mCanvas;
    private static final int MIN_MOVE_DIS = 5;
    private float preX, preY;
	
	public GuaguakaView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public GuaguakaView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public GuaguakaView(Context context) {
		super(context);
	}
	
	private void init() {
		mWidth = MeasureUtil.getScreenSize((Activity) this.getContext())[0];
		mHeight = MeasureUtil.getScreenSize((Activity) this.getContext())[1];
		mRect = new Rect(10,10,mWidth,mHeight);
		
		mSrcBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.zhong_jiang);
		mFGBitmap = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
        this.mCanvas = new Canvas(this.mFGBitmap);
        //这步是关键，让合成的部分变成透明，后面的部分露出来
        this.mCanvas.drawColor(0xFF808080);
		
		mPaint = new Paint();
		mPaint.setColor(Color.BLUE);
		mLinePaint = new Paint();
        mLinePaint.setARGB(0, 255, 0, 0);
		mLinePaint.setStyle(Style.STROKE);
		mLinePaint.setStrokeWidth(30);
		mLinePaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		mPath = new Path();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mSrcBitmap, 0, 0, null);
		canvas.drawBitmap(mFGBitmap, 0,0,null);
		
		mCanvas.drawPath(mPath, mLinePaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
        /*
         * ��ȡ��ǰ�¼�λ�����
         */
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:// ��ָ�Ӵ���Ļ����·��
                this.mPath.reset();
                this.mPath.moveTo(x, y);
                this.preX = x;
                this.preY = y;
                break;
            case MotionEvent.ACTION_MOVE:// ��ָ�ƶ�ʱ����·��
                float dx = Math.abs(x - this.preX);
                float dy = Math.abs(y - this.preY);
                if (dx >= MIN_MOVE_DIS || dy >= MIN_MOVE_DIS) {
                    this.mPath.quadTo(this.preX, this.preY, (x + this.preX) / 2, (y + this.preY) / 2);
                    this.preX = x;
                    this.preY = y;
                }
                break;
        }

        // �ػ���ͼ
        this.invalidate();
        return true;
    }

}
