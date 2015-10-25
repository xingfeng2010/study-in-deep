/**
 * 
 */
package com.shixing.studycode.customview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author lishixing
 * 
 */
public class MultiTouchView extends SurfaceView implements
		SurfaceHolder.Callback, GestureDetector.OnGestureListener {

	private static final String TAG = "MultiTouchView";
	private static final int MAX_TOUCH_POINTS = 10;
	private static final int CIRCLE_RADIUS = 10;
	private static final String NOTE_TEXT = "请从下面开始作画";
	private Paint[] mPaints = new Paint[MAX_TOUCH_POINTS];
	private int[] mColors = new int[MAX_TOUCH_POINTS];
	private Canvas mCanvas;
	private Paint textPaint;

	private GestureDetector mGestureDetector;

	public MultiTouchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.getHolder().addCallback(this);
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
		textPaint = new Paint();
		mGestureDetector = new GestureDetector(context,this);
		initPaint();
	}

	private void initPaint() {
		mColors[0] = Color.BLUE;
		mColors[1] = Color.RED;
		mColors[2] = Color.GREEN;
		mColors[3] = Color.YELLOW;
		mColors[4] = Color.CYAN;
		mColors[5] = Color.MAGENTA;
		mColors[6] = Color.DKGRAY;
		mColors[7] = Color.WHITE;
		mColors[8] = Color.LTGRAY;
		mColors[9] = Color.GRAY;
		for (int i = 0; i < MAX_TOUCH_POINTS; i++) {
			mPaints[i] = new Paint();
			mPaints[i].setColor(mColors[i]);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 获得屏幕触点数量
		mGestureDetector.onTouchEvent(event);
		int pointerCount = event.getPointerCount();
		if (pointerCount > MAX_TOUCH_POINTS) {
			pointerCount = MAX_TOUCH_POINTS;
		}

		Canvas canvas = this.getHolder().lockCanvas();
		if (canvas == null) {
			return false;
		}

		Log.i(TAG, "onTouchEvent pointerCount:" + pointerCount);
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_DOWN:
			for (int i = 0; i < pointerCount; i++) {
				canvas.drawCircle(event.getX(i), event.getY(i), CIRCLE_RADIUS,
						mPaints[event.getPointerId(i)]);
			}

		}

		this.getHolder().unlockCanvasAndPost(canvas);
		return super.onTouchEvent(event);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		Log.i(TAG, "surfaceChanged");
		textPaint.setTextSize(30);
		textPaint.setColor(Color.RED);
		Canvas canvas = getHolder().lockCanvas();
		canvas.drawColor(Color.BLUE);
		canvas.drawText(NOTE_TEXT, 0, 10, textPaint);
		getHolder().unlockCanvasAndPost(canvas);
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		Log.i(TAG, "surfaceCreated");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

	}
	
    //Touch down时触发 
	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	//Touch了滑动一点距离后，up时触发。
	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	//Touch了不移动一直Touch down时触发
	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	//Touch了滑动时触发。
	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	//Touch了还没有滑动时触发
	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	//上面这两个函数都是在touch down后又没有滑动（onScroll），又没有长按（onLongPress），然后Touchup时触发。
	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
