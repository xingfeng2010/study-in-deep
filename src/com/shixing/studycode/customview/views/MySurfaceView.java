package com.shixing.studycode.customview.views;

import com.shixing.study.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {

    private SurfaceHolder mHolder;
    private Canvas mCanvas;

    Thread t;

    private boolean isRunning;
    /**
     * �齱������
     */
    private String[] mStrs = new String[] { "�������", "IPAD", "��ϲ����", "IPHONE",
            "����һֻ", "��ϲ����" };

    /**
     * ÿ���̿����ɫ
     */
    private int[] mColors = new int[] { 0xFFFFC300, 0xFFF17E01, 0xFFFFC300,
            0xFFF17E01, 0xFFFFC300, 0xFFF17E01 };

    /**
     * �����ֶ�Ӧ��ͼƬ
     */
    private int[] mImgs = new int[] { R.drawable.danfan, R.drawable.ipad,
            R.drawable.f040, R.drawable.iphone, R.drawable.meizi,
            R.drawable.f040 };

    // �����ֶ�ӦͼƬ��bitmap����
    private Bitmap[] mImgsBitmap;

    // �̿�ĸ���
    private int mItemCount = 6;

    // �����̿�ķ�Χ
    private RectF mRange = new RectF();

    // Բ��ֱ��
    private int mRadius;

    private Paint mTextPaint;

    // ����ٶ�
    private double mSpeed;
    private volatile float mStartAngle = 0;

    private boolean isShouldEnd;

    // �ؼ�����
    private int mCenter;

    private int mPadding;

    // ����ͼƬbitmap
    private Bitmap mBgBitmap = BitmapFactory.decodeResource(
            this.getResources(), R.drawable.bg2);

    // ���ֵĴ�С
    private float mTextSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 21, this.getResources()
                    .getDisplayMetrics());
    private Paint mArcPaint;

    public MySurfaceView(Context context) {
        this(context, null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mHolder = this.getHolder();
        mHolder.addCallback(this);

        this.setFocusable(true);
        this.setFocusableInTouchMode(true);

        // ���ó���
        this.setKeepScreenOn(true);

    }

    @Override
    public void run() {
        while (isRunning) {
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void draw() {
        try {
            // ���canvas
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                // ���Ʊ���ͼ
                drawBg();
                
                /**
                 * ����ÿ���飬ÿ�����ϵ��ı���ÿ�����ϵ�ͼƬ
                 */
                float tmpAngle = mStartAngle;
                float sweepAngle = (float) (360 / mItemCount);
                for (int i = 0; i < mItemCount; i++) {
                    //���ƿ��
                    mArcPaint.setColor(mColors[i]);
                    mCanvas.drawArc(mRange, tmpAngle, sweepAngle, true, mArcPaint);
                    //�����ı�
                    drawText(tmpAngle,sweepAngle,mStrs[i]);
                    //����ICON
                    drawIcon(tmpAngle,i);
                    tmpAngle += sweepAngle;
                }
                
                //���mSpeed������0,���൱���ڹ�
                mStartAngle += mSpeed;
                
                //���ֹͣʱ������mSpeedΪ�ݼ�Ϊ0ֵת��ֹͣ
                if (isShouldEnd) {
                    mSpeed -= 1;
                }
                
                if (mSpeed <= 0) {
                    mSpeed = 0;
                    isShouldEnd = false;
                }
                // ��ݵ�ǰ��ת��mStartAngle���㵱ǰ���������
                calInExactArea(mStartAngle);
            }
        } catch (Exception e) {
                e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private void calInExactArea(float mStartAngle2) {
        // TODO Auto-generated method stub
        
    }

    //����ͼƬ
    private void drawIcon(float startAngle, int i) {
         //����ͼƬ�Ŀ��Ϊֱ����1/8
        int imgWidth = mRadius / 8;
        
        float angle = (float) ((30 + startAngle) * (Math.PI / 180));
        
        int x = (int) (mCenter + mRadius / 2 / 2 * Math.cos(angle));
        int y = (int) (mCenter + mRadius / 2 / 2 * Math.sin(angle));
        
        //ȷ������ͼƬ��λ��
        Rect rect = new Rect(x - imgWidth / 2,y - imgWidth / 2,x + imgWidth / 2, y + imgWidth /2);
        mCanvas.drawBitmap(mImgsBitmap[i], null,rect, null);
    }

    //�����ı�
    private void drawText(float startAngle, float sweepAngle, String string) {
        Path path = new Path();
        path.addArc(mRange, startAngle, sweepAngle);
        float textWidth = mTextPaint.measureText(string);
        //����ˮƽƫ�������־���
        float hOffset = (float)(mRadius * Math.PI / mItemCount / 2 - textWidth / 2);//ˮƽƫ��
        float vOffset = mRadius / 2 / 6;//��ֱƫ��
        mCanvas.drawTextOnPath(string,path,hOffset,vOffset,mTextPaint);
    }

    /**
     * ��ݵ�ǰ��ת��mStartAngle���㵱ǰ�����������Ʊ���������Ҫ����ȫΪ���9�
     */
    private void drawBg() {
        mCanvas.drawColor(0xFFFFFFFF);
        mCanvas.drawBitmap(
                mBgBitmap,
                null,
                new Rect(mPadding / 2, mPadding / 2, this.getMeasuredWidth()
                        - mPadding / 2, this.getMeasuredWidth() - mPadding / 2),
                null);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        // ��ʼ������Բ���Ļ���
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);
        // ��ʼ���������ֵĻ���
        mTextPaint = new Paint();
        mTextPaint.setColor(0xFFffffff);
        mTextPaint.setTextSize(mTextSize);
        // Բ���Ļ��Ʒ�Χ
        mRange = new RectF(this.getPaddingLeft(), this.getPaddingLeft(),
                mRadius + this.getPaddingLeft(), mRadius
                        + this.getPaddingLeft());
        // ��ʼ��ͼƬ
        mImgsBitmap = new Bitmap[mItemCount];
        for (int i = 0; i < mItemCount; i++) {
            mImgsBitmap[i] = BitmapFactory.decodeResource(this.getResources(),
                    mImgs[i]);
        }
        // �����߳�
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // ֪ͨ�ر��߳�
        isRunning = false;
    }

    /**
     * ���ÿؼ�Ϊ����
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = Math.min(this.getMeasuredWidth(), this.getMeasuredHeight());
        mRadius = width - this.getPaddingLeft() - this.getPaddingRight();
        mPadding = this.getPaddingLeft();
        mCenter = width / 2;
        this.setMeasuredDimension(width, width);
    }

    
    /**
     * ���ʼ��ת
     * 
     * @param luckyIndex
     */
    public void luckyStart(int luckyIndex)
    {
        // ÿ��Ƕȴ�С
        float angle = (float) (360 / mItemCount);
        // �н��Ƕȷ�Χ����Ϊָ�����ϣ�����ˮƽ��һ����ת��ָ��ָ����Ҫ��ת210-270����
        float from = 270 - (luckyIndex + 1) * angle;
        float to = from + angle;
        // ͣ��4ʱ��ת�ľ���
        float targetFrom = 4 * 360 + from;
        /**
         * <pre>
         *  (v1 + 0) * (v1+1) / 2 = target ;
         *  v1*v1 + v1 - 2target = 0 ;
         *  v1=-1+(1*1 + 8 *1 * target)/2;
         * </pre>
         */
        float v1 = (float) (Math.sqrt(1 * 1 + 8 * 1 * targetFrom) - 1) / 2;
        float targetTo = 4 * 360 + to;
        float v2 = (float) (Math.sqrt(1 * 1 + 8 * 1 * targetTo) - 1) / 2;

        mSpeed = (float) (v1 + Math.random() * (v2 - v1));
        isShouldEnd = false;
    }

    public void luckyEnd()
    {
        mStartAngle = 0;
        isShouldEnd = true;
    }

    public boolean isStart()
    {
        return mSpeed != 0;
    }

    public boolean isShouldEnd()
    {
        return isShouldEnd;
    }
    
    
}
