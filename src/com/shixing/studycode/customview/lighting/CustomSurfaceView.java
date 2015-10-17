
package com.shixing.studycode.customview.lighting;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

import com.shixing.study.R;

@SuppressLint("UseSparseArrays")
public class CustomSurfaceView extends SurfaceView implements Runnable,
        Callback {
    public static CustomSurfaceView mSurfaceView;
    private final SurfaceHolder mHolder;
    private Canvas mCanvas;
    private final Paint mPaint;
    private final Context mContext;
    private final SoundPool mSoundPool;
    private final Map<Integer, Integer> mSoundMap;
    private int mIndex;
    private final CustomPaint mCustomPaint;

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        super(context);
        this.mContext = context;
        mSurfaceView = this;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(Color.BLUE);
        this.mPaint.setAlpha(127);
        this.mPaint.setStrokeWidth(2);
        this.mHolder = this.getHolder();
        this.mHolder.setFormat(PixelFormat.TRANSPARENT);// é–«å¿”æ§?
        this.mHolder.addCallback(this);
        this.setKeepScreenOn(true);
        this.setFocusable(true);
        this.setLongClickable(true);
        this.mSoundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        this.mSoundMap = new HashMap<Integer, Integer>();
        this.mSoundMap.put(1, this.mSoundPool.load(context, R.raw.zapp, 1));
        this.mCustomPaint = new CustomPaint();
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent arg1) {
        // TODO Auto-generated method stub
        switch (arg1.getPointerCount()) {
            case 1:
                switch (arg1.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        this.drawLine1(arg1.getX(), arg1.getY());
                        this.viBrator(this.getContext());
                        this.playSound();
                        break;
                    case MotionEvent.ACTION_UP:
                        this.drawClear();
                        this.stopSound();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        this.drawLine1(arg1.getX(), arg1.getY());
                        this.viBrator(this.getContext());
                        // playSound();
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                this.drawAction2(arg1);
                break;
            default:
                this.drawAction2(arg1);
                break;
        }

        return super.onTouchEvent(arg1);
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        // mThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {

    }

    @Override
    public void run() {
        // while (mFlag) {
        // drawLine(mX, mY);
        // viBrator(mContext);
        // playSound();
        // try {
        // Thread.sleep(50);
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        //
        // }
    }

    // ç”»å‘¨å›´å››æ¡çº¿
    public void drawLine1(float x, float y) {
        this.mCanvas = this.mHolder.lockCanvas();
        if (this.mCanvas != null) {
            int width = this.mContext.getResources().getDisplayMetrics().widthPixels;
            int heigth = this.mContext.getResources().getDisplayMetrics().heightPixels - 40;
            this.mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            this.mCustomPaint.drawLightning(0, 0, x, y, new Random().nextInt(40),
                    this.mCanvas);
            this.mCustomPaint.drawLightning(0, 0, x, y, new Random().nextInt(40),
                    this.mCanvas);
            this.mCustomPaint.drawLightning(width, 0, x, y,
                    new Random().nextInt(40), this.mCanvas);
            this.mCustomPaint.drawLightning(width, 0, x, y,
                    new Random().nextInt(40), this.mCanvas);
            this.mCustomPaint.drawLightning(0, heigth, x, y,
                    new Random().nextInt(40), this.mCanvas);
            this.mCustomPaint.drawLightning(0, heigth, x, y,
                    new Random().nextInt(40), this.mCanvas);
            this.mCustomPaint.drawLightning(width, heigth, x, y,
                    new Random().nextInt(40), this.mCanvas);
            this.mCustomPaint.drawLightning(width, heigth, x, y,
                    new Random().nextInt(40), this.mCanvas);
            this.mCustomPaint.drawLightningBold(x, y, x, y,
                    new Random().nextInt(40), this.mCanvas);
            this.mCustomPaint.drawLightningBold(x, y, x, y,
                    new Random().nextInt(40), this.mCanvas);

            this.mHolder.unlockCanvasAndPost(this.mCanvas);
        }
    }

    // ç”»ä¸­é—´æˆ–è€…ä¸¤ä¸ªæ‰‹æŒ‡ä¹‹é—´çš„ç²—çº¿
    public void drawLine2(float x1, float y1, float x2, float y2) {
        this.mCanvas = this.mHolder.lockCanvas();
        if (this.mCanvas != null) {
            int width = this.mContext.getResources().getDisplayMetrics().widthPixels;
            int heigth = this.mContext.getResources().getDisplayMetrics().heightPixels - 40;
            this.mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            if (y1 < y2) {
                this.mCustomPaint.drawLightning(0, 0, x1, y1,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(0, 0, x1, y1,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(width, 0, x1, y1,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(width, 0, x1, y1,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(0, heigth, x2, y2,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(0, heigth, x2, y2,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(width, heigth, x2, y2,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(width, heigth, x2, y2,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(x1, y1, x2, y2,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(x1, y1, x2, y2,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightningBold(x1, y1, x2, y2,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightningBold(x1, y1, x2, y2,
                        new Random().nextInt(40), this.mCanvas);
            } else {
                this.mCustomPaint.drawLightning(0, 0, x2, y2,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(0, 0, x2, y2,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(width, 0, x2, y2,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(width, 0, x2, y2,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(0, heigth, x1, y1,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(0, heigth, x1, y1,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(width, heigth, x1, y1,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(width, heigth, x1, y1,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(x1, y1, x2, y2,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightning(x1, y1, x2, y2,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightningBold(x1, y1, x2, y2,
                        new Random().nextInt(40), this.mCanvas);
                this.mCustomPaint.drawLightningBold(x1, y1, x2, y2,
                        new Random().nextInt(40), this.mCanvas);
            }

            this.mHolder.unlockCanvasAndPost(this.mCanvas);
        }
    }

    // ç¡¬ä»¶è°ƒç”¨â€”â?â€”éœ‡åŠ?
    public void viBrator(Context context) {
        Vibrator vibrator = (Vibrator) context
                .getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(100);
    }

    // è¢«é‡å¤è°ƒç”¨çš„ç”»ç²—çº¿çš„æ–¹æ³•
    public void drawAction2(MotionEvent arg1) {
        this.drawLine2(arg1.getX(0), arg1.getY(0), arg1.getX(1), arg1.getY(1));
        this.viBrator(this.getContext());
        // playSound();
    }

    // æ’­æ”¾å£°éŸ³
    public void playSound() {
        this.mIndex = this.mSoundPool.play(this.mSoundMap.get(1), 1, 1, 0, -1, 1);
    }

    public void stopSound() {
        // Toast.makeText(getContext(), "zzzzz", 0).show();
        this.mSoundPool.stop(this.mIndex);
    }

    // ç”»å¸ƒæ¸…é™¤
    public void drawClear() {
        this.mCanvas = this.mHolder.lockCanvas();
        if (this.mCanvas != null) {
            this.mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            // mCanvas.drawColor(Color.alpha(0));
            this.mHolder.unlockCanvasAndPost(this.mCanvas);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        Toast.makeText(this.getContext(), "keydown", Toast.LENGTH_SHORT).show();
        return super.onKeyDown(keyCode, event);
    }

}
