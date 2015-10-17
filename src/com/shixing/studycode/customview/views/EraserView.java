
package com.shixing.studycode.customview.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.shixing.study.R;
import com.shixing.studycode.utils.MeasureUtil;

/**
 * ��Ƥ��View �����������ö࡭��
 * 
 * @author Aige
 * @since 2014/11/17
 */
public class EraserView extends View {
    private static final int MIN_MOVE_DIS = 5;// ��С���ƶ����룺���������ָ����Ļ�ϵ��ƶ�����С�ڴ�ֵ�򲻻����

    private Bitmap fgBitmap, bgBitmap;// ǰ����Ƥ����Bitmap�ͱ������ǵ�ͼ��Bitmap
    private Canvas mCanvas;// ������Ƥ��·���Ļ���
    private Paint mPaint;// ��Ƥ��·������
    private Path mPath;// ��Ƥ������·��

    private int screenW, screenH;// ��Ļ���
    private float preX, preY;// ��¼��һ�������¼���λ�����

    public EraserView(Context context, AttributeSet set) {
        super(context, set);

        // �������
        this.cal(context);

        // ��ʼ������
        this.init(context);
    }

    /**
     * �������
     * 
     * @param context �����Ļ�������
     */
    private void cal(Context context) {
        // ��ȡ��Ļ�ߴ�����
        int[] screenSize = MeasureUtil.getScreenSize((Activity) context);

        // ��ȡ��Ļ���
        this.screenW = screenSize[0];
        this.screenH = screenSize[1];
    }

    /**
     * ��ʼ������
     */
    private void init(Context context) {
        // ʵ��·������
        this.mPath = new Path();

        // ʵ��ʲ������俹��ݺͿ�����
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        // ���û���͸����Ϊ0�ǹؼ�����Ҫ�û��Ƶ�·����͸���ģ�Ȼ���ø�·����ǰ���ĵ�ɫ��ϡ��١�������·��
        this.mPaint.setARGB(0, 255, 0, 0);

        // ���û��ģʽΪDST_IN
        this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        // ���û��ʷ��Ϊ���
        this.mPaint.setStyle(Paint.Style.STROKE);

        // ����·����ϴ���ʽ
        this.mPaint.setStrokeJoin(Paint.Join.ROUND);

        // ���ñʴ�����
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);

        // ������߿��
        this.mPaint.setStrokeWidth(50);

        // ���ǰ��ͼBitmap
        this.fgBitmap = Bitmap.createBitmap(this.screenW, this.screenH, Config.ARGB_8888);

        // ����ע�뻭��
        this.mCanvas = new Canvas(this.fgBitmap);

        // ���ƻ�������Ϊ���Ի�
        this.mCanvas.drawColor(0xFF808080);

        // ��ȡ������ͼBitmap
        this.bgBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.a4);

        // ���ű�����ͼBitmap����Ļ��С
        this.bgBitmap = Bitmap.createScaledBitmap(this.bgBitmap, this.screenW, this.screenH, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // ���Ʊ���
        canvas.drawBitmap(this.bgBitmap, 0, 0, null);

        // ����ǰ��
        canvas.drawBitmap(this.fgBitmap, 0, 0, null);

        /*
         * ����Ҫע��canvas��mCanvas��������ͬ�Ļ�������
         * ����������Ļ���ƶ���ָ����·��ʱ���·��ͨ��mCanvas���Ƶ�fgBitmap��
         * ÿ��������ָ�
         * ƶ�һ�ξ�Ὣ·��mPath��ΪĿ��ͼ����Ƶ�mCanvas�ϣ�����������������mCanvas
         * �ϻ��������Ի�ɫ
         * ���߻���ΪDST_INģʽ�ļ���ֻ��ʾ���Իң�������ΪmPath��͸����������
         * ɵĻ��ͼ��Ҳ����͸���� �������ǻ�õ�����Ƥ������Ч��
         */
        this.mCanvas.drawPath(this.mPath, this.mPaint);
    }

    /**
     * View���¼�������7/12���
     */
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
