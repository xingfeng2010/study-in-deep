package com.shixing.studycode.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.shixing.study.R;

public class BitMapActivity extends Activity {
    private static final String TAG = "BitMapActivity";

    private static final int DELAY_TIME = 3000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.bitmap_process);

        ImageView mImageView1 = (ImageView) this.findViewById(R.id.imageview1);
        ImageView mImageView2 = (ImageView) this.findViewById(R.id.imageview2);
        ImageView mImageView3 = (ImageView) this.findViewById(R.id.imageview3);
        // mImageView1.setImageBitmap(getDrawableBitmap());
        // mImageView1.setImageBitmap(getResourceBitmap());
        // mImageView1.setImageBitmap(getAssetsBitmap());
        // mImageView1.setImageBitmap(drawGraphics());

        // Bitmap mSaveBitmap = drawGraphics();
        // mImageView1.setImageBitmap(mSaveBitmap);
        // ע�������Ӧ��Ȩ�ޣ���manifest.xml��
        // saveBitmap(mSaveBitmap, "/sdcard/savebitmap123.PNG");

        // mImageView1.setImageResource(R.drawable.android);
        // mImageView2.setImageResource(R.drawable.pet);
        // this.getDrawingCache(mImageView1, mImageView2);

        // mImageView1.setImageBitmap(this.getRoundedBitmap());
        // mImageView2.setImageResource(R.drawable.shishi);

        // mImageView1.setImageResource(R.drawable.shishi);
        // mImageView2.setImageBitmap(this.getGrayBitmap());

        // mImageView1.setImageResource(R.drawable.enemy_infantry_ninja);
        // mImageView2.setImageBitmap(this.getAlphaBitmap());
        // mImageView3.setImageBitmap(this.getStrokeBitmap());

        // mImageView1.setImageResource(R.drawable.pet);
        // mImageView2.setImageBitmap(this.getScaleBitmap());
        // mImageView2.setImageBitmap(this.getRotatedBitmap());
        // mImageView2.setImageBitmap(this.getScrewBitmap());
        // mImageView2.setImageBitmap(this.getInverseBitmap());
        // mImageView2.setImageBitmap(this.getReflectedBitmap());

        // mImageView1.setImageBitmap(this.getCompoundedBitmap());

        mImageView1.setImageBitmap(this.getArrowBitmap());

    }

    // ��ȡresĿ¼�µ�drawable��Դ
    public Bitmap getDrawableBitmap() {
        // ��ȡӦ����Դ������ʵ��
        Resources mResources = this.getResources();
        // ��ȡdrawable��Դframe��ת��Ϊ BitmapDrawable����
        BitmapDrawable mBitmapDrawable = (BitmapDrawable) mResources
                .getDrawable(R.drawable.android);
        // ��ȡbitmap
        Bitmap mBitmap = mBitmapDrawable.getBitmap();

        return mBitmap;
    }

    // ��ȡresĿ¼�µ�drawable��Դ
    public Bitmap getResourceBitmap() {
        Resources mResources = this.getResources();
        Bitmap mBitmap = BitmapFactory.decodeResource(mResources,
                R.drawable.android);

        return mBitmap;
    }

    // ��ȡassetsĿ¼�µ�ͼƬ��Դ
    public Bitmap getAssetsBitmap() {
        // ����Bitmap
        Bitmap mBitmap = null;

        // ��ȡassets��Դ����ʵ��
        AssetManager mAssetManager = this.getAssets();

        try {
            // ��frame.png�ļ���
            InputStream mInputStream = mAssetManager.open("android.png");
            // ͨ��decodeStream���������ļ���
            mBitmap = BitmapFactory.decodeStream(mInputStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return mBitmap;
    }

    // ����Bitmap��Դ
    public Bitmap drawGraphics() {
        // ������СΪ320 x 480��ARGB_8888����λͼ
        Bitmap mBitmap = Bitmap.createBitmap(320, 480, Config.ARGB_8888);
        // ���½���λͼ��Ϊ����
        Canvas mCanvas = new Canvas(mBitmap);

        // �Ȼ�һ������
        mCanvas.drawColor(Color.BLACK);

        // ��������,����������
        Paint mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Style.FILL);

        Rect mRect = new Rect(10, 10, 300, 80);
        RectF mRectF = new RectF(mRect);
        // ����Բ�ǰ뾶
        float roundPx = 15;

        mPaint.setAntiAlias(true);
        mCanvas.drawRoundRect(mRectF, roundPx, roundPx, mPaint);
        mPaint.setColor(Color.GREEN);
        mCanvas.drawCircle(80, 180, 80, mPaint);

        DashPathEffect mDashPathEffect = new DashPathEffect(new float[] { 20,
                20, 10, 10, 5, 5, }, 0);
        mPaint.setPathEffect(mDashPathEffect);
        Path mPath = new Path();
        mRectF.offsetTo(10, 300);
        mPath.addRect(mRectF, Direction.CW);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setStrokeJoin(Join.ROUND);
        mPaint.setStyle(Style.STROKE);
        mCanvas.drawPath(mPath, mPaint);

        mCanvas.drawBitmap(this.getDrawableBitmap(), 160, 90, mPaint);

        return mBitmap;
    }

    // ����λͼ��Դ
    // ��֪�����ʹ��
    public static void saveBitmap(Bitmap bitmap, String path) {
        FileOutputStream mFileOutputStream = null;

        try {
            File mFile = new File(path);
            // �����ļ�
            mFile.createNewFile();
            // �����ļ������
            mFileOutputStream = new FileOutputStream(mFile);
            // ����Bitmap��PNG�ļ�
            // ͼƬѹ������Ϊ75������PNG��˵�������ᱻ����
            bitmap.compress(CompressFormat.PNG, 75, mFileOutputStream);
            // Flushes this stream.
            // Implementations of this method should ensure that any buffered
            // data is written out.
            // This implementation does nothing.
            mFileOutputStream.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                mFileOutputStream.close();
            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

    }

    // convert view to bitmap
    public void getDrawingCache(final ImageView sourceImageView,
            final ImageView destImageView) {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                // open bitmap cache
                sourceImageView.setDrawingCacheEnabled(true);
                // get bitmap cache
                Bitmap mBitmap = sourceImageView.getDrawingCache();
                // show bitmap
                destImageView.setImageBitmap(mBitmap);

                // Bitmap mBitmap = sourceImageView.getDrawingCache();
                // Drawable drawable = (Drawable) new BitmapDrawable(mBitmap);
                // destImageView.setImageDrawable(drawable);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 不再显示bitmap缓存
                        // destImageView.setImageBitmap(null);
                        destImageView.setImageResource(R.drawable.pet);

                        // destImageView.setBackgroundDrawable(null);

                        // close bitmap cache
                        sourceImageView.setDrawingCacheEnabled(false);
                        // release bitmap resouce
                        sourceImageView.destroyDrawingCache();
                    }
                }, DELAY_TIME);
            }
        }, DELAY_TIME);
    }

    // 图片圆角处理
    public Bitmap getRoundedBitmap() {
        Bitmap mBitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.shishi);
        // create new bitmap
        Bitmap bgBitmap = Bitmap.createBitmap(mBitmap.getWidth(),
                mBitmap.getHeight(), Config.ARGB_8888);
        // 把创建的位图作为画板
        Canvas mCanvas = new Canvas(bgBitmap);

        Paint mPaint = new Paint();
        Rect mRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        RectF mRectF = new RectF(mRect);
        // 设置圆角半径为20
        float roundPx = 50;
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        // 先绘制圆角矩形
        mCanvas.drawRoundRect(mRectF, roundPx, roundPx, mPaint);

        // 设置图像的叠加模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 绘制图像
        mCanvas.drawBitmap(mBitmap, mRect, mRect, mPaint);

        return bgBitmap;
    }

    // 图片灰化处理
    public Bitmap getGrayBitmap() {
        Bitmap mBitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.shishi);
        Bitmap mGrayBitmap = Bitmap.createBitmap(mBitmap.getWidth(),
                mBitmap.getHeight(), Config.ARGB_8888);
        Canvas mCanvas = new Canvas(mGrayBitmap);
        Paint mPaint = new Paint();

        // //创建颜色变换矩阵
        ColorMatrix mColorMatrix = new ColorMatrix();
        // 设置灰度影响范围
        mColorMatrix.setSaturation(0);
        // 创建颜色过滤矩阵
        ColorMatrixColorFilter mColorFilter = new ColorMatrixColorFilter(
                mColorMatrix);
        // 设置画笔的颜色过滤矩阵
        mPaint.setColorFilter(mColorFilter);
        // 使用处理后的画笔绘制图像
        mCanvas.drawBitmap(mBitmap, 0, 0, mPaint);

        return mGrayBitmap;
    }

    // 提取图像Alpha位图
    public Bitmap getAlphaBitmap() {
        BitmapDrawable mBitmapDrawable = (BitmapDrawable) this.getResources()
                .getDrawable(R.drawable.enemy_infantry_ninja);
        Bitmap mBitmap = mBitmapDrawable.getBitmap();
        Log.i(TAG, "getAlphaBitmap config=" + mBitmap.getConfig());

        // BitmapDrawable的getIntrinsicWidth（）方法，Bitmap的getWidth（）方法
        // 注意这两个方法的区别
        // Bitmap mAlphaBitmap =
        // Bitmap.createBitmap(mBitmapDrawable.getIntrinsicWidth(),
        // mBitmapDrawable.getIntrinsicHeight(), Config.ARGB_8888);
        Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmap.getWidth(),
                mBitmap.getHeight(), Config.ARGB_8888);

        Canvas mCanvas = new Canvas(mAlphaBitmap);
        Paint mPaint = new Paint();

        mPaint.setColor(Color.GREEN);
        // 从原位图中提取只包含alpha的位图
        Bitmap alphaBitmap = mBitmap.extractAlpha();
        // 在画布上（mAlphaBitmap）绘制alpha位图
        mCanvas.drawBitmap(alphaBitmap, 0, 0, mPaint);

        return mAlphaBitmap;
    }

    // getStrokeBitmap 把原图片四个边距缩小两个dp，然后与Alpha位图一起绘制的结果
    public Bitmap getStrokeBitmap() {
        BitmapDrawable mBitmapDrawable = (BitmapDrawable) this.getResources()
                .getDrawable(R.drawable.enemy_infantry_ninja);
        Bitmap mBitmap = mBitmapDrawable.getBitmap();
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        Bitmap mAlphaBitmap = Bitmap.createBitmap(width, height,
                Config.ARGB_8888);
        Canvas mCanvas = new Canvas(mAlphaBitmap);
        Paint mPaint = new Paint();

        mPaint.setColor(Color.BLUE);
        Bitmap alphaBitmap = mBitmap.extractAlpha();
        mCanvas.drawBitmap(alphaBitmap, 0, 0, mPaint);

        Rect srcRect = new Rect(0, 0, width, height);
        Rect innerRect = new Rect(srcRect);
        innerRect.inset(2, 2);
        mCanvas.drawBitmap(mBitmap, srcRect, innerRect, mPaint);

        return mAlphaBitmap;
    }

    // getScaleBitmap
    public Bitmap getScaleBitmap() {
        BitmapDrawable mBitmapDrawable = (BitmapDrawable) this.getResources()
                .getDrawable(R.drawable.pet);
        Bitmap mBitmap = mBitmapDrawable.getBitmap();
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(0.75f, 0.75f);
        Bitmap mScaleBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height,
                matrix, true);

        return mScaleBitmap;
    }

    // getRotatedBitmap
    public Bitmap getRotatedBitmap() {
        BitmapDrawable mBitmapDrawable = (BitmapDrawable) this.getResources()
                .getDrawable(R.drawable.pet);
        Bitmap mBitmap = mBitmapDrawable.getBitmap();
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preRotate(45);
        Bitmap mRotateBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width,
                height, matrix, true);

        return mRotateBitmap;
    }

    // getScrewBitmap
    public Bitmap getScrewBitmap() {
        BitmapDrawable mBitmapDrawable = (BitmapDrawable) this.getResources()
                .getDrawable(R.drawable.pet);
        Bitmap mBitmap = mBitmapDrawable.getBitmap();
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preSkew(1.0f, 0.15f);
        Bitmap mScrewBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height,
                matrix, true);

        return mScrewBitmap;
    }

    // getInverseBitmap
    public Bitmap getInverseBitmap() {
        BitmapDrawable mBitmapDrawable = (BitmapDrawable) this.getResources()
                .getDrawable(R.drawable.pet);
        Bitmap mBitmap = mBitmapDrawable.getBitmap();
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        Matrix matrix = new Matrix();
        // 图像倒立效果
        matrix.preScale(1, -1);
        Bitmap mInverseBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width,
                height, matrix, false);

        return mInverseBitmap;
    }

    // 图像倒影
    private Bitmap getReflectedBitmap() {
        BitmapDrawable mBitmapDrawable = (BitmapDrawable) this.getResources()
                .getDrawable(R.drawable.pet);
        Bitmap mBitmap = mBitmapDrawable.getBitmap();
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        Matrix matrix = new Matrix();
        // 图像缩放,x轴变为原来的1倍,y轴变为-1倍,实现图片的反转
        matrix.preScale(1, -1);

        // 创建反转后的图片Bitmap对象,图片高是原图片的一半.
        // Bitmap mInverseBitmap = Bitmap.createBitmap(mBitmap, 0, height/2,
        // width, height/2, matrix, false);
        // 创建标准的Bitmap对象，宽和原图一致，高是原图的1.5倍。
        // 注意两种createBitmap的不同
        // Bitmap mReflectedBitmap = Bitmap.createBitmap(width, height*3/2,
        // Config.ARGB_8888);

        Bitmap mInverseBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width,
                height, matrix, false);
        Bitmap mReflectedBitmap = Bitmap.createBitmap(width, height * 2,
                Config.ARGB_8888);

        // 把新建的位图作为画板
        Canvas mCanvas = new Canvas(mReflectedBitmap);
        // 绘制图片
        mCanvas.drawBitmap(mBitmap, 0, 0, null);
        mCanvas.drawBitmap(mInverseBitmap, 0, height, null);

        // // 添加倒影的渐变效果
        Paint mPaint = new Paint();
        Shader mShader = new LinearGradient(0, height, 0,
                mReflectedBitmap.getHeight(), 0x70ffffff, 0x00ffffff,
                TileMode.MIRROR);
        mPaint.setShader(mShader);
        // 设置叠加模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // 绘制遮罩效果
        mCanvas.drawRect(0, height, width, mReflectedBitmap.getHeight(), mPaint);

        return mReflectedBitmap;
    }

    // getCompoundedBitmap
    public Bitmap getCompoundedBitmap() {

        BitmapDrawable bdHair = (BitmapDrawable) this.getResources()
                .getDrawable(R.drawable.res_hair);
        Bitmap bitmapHair = bdHair.getBitmap();
        BitmapDrawable bdFace = (BitmapDrawable) this.getResources()
                .getDrawable(R.drawable.res_face);
        Bitmap bitmapFace = bdFace.getBitmap();
        BitmapDrawable bdClothes = (BitmapDrawable) this.getResources()
                .getDrawable(R.drawable.res_clothes);
        Bitmap bitmapClothes = bdClothes.getBitmap();

        int w = bdClothes.getIntrinsicWidth();
        int h = bdClothes.getIntrinsicHeight();
        Bitmap mBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);

        Canvas canvas = new Canvas(mBitmap);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);

        canvas.drawBitmap(bitmapClothes, 0, 0, mPaint);
        canvas.drawBitmap(bitmapFace, 0, 0, mPaint);
        canvas.drawBitmap(bitmapHair, 0, 0, mPaint);

        return mBitmap;
    }

    // getClipBitmap
    public Bitmap getClipBitmap() {
        BitmapDrawable bd = (BitmapDrawable) this.getResources().getDrawable(
                R.drawable.beauty);
        Bitmap bitmap = bd.getBitmap();

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap bm = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.STROKE);

        canvas.drawBitmap(bitmap, 0, 0, mPaint);

        int deltX = 76;
        int deltY = 98;

        DashPathEffect dashStyle = new DashPathEffect(
                new float[] { 10, 5, 5, 5 }, 2);

        RectF faceRect = new RectF(0, 0, 88, 106);
        float[] faceCornerii = new float[] { 30, 30, 30, 30, 75, 75, 75, 75 };
        mPaint.setColor(0xFF6F8DD5);
        mPaint.setStrokeWidth(6);
        mPaint.setPathEffect(dashStyle);
        Path clip = new Path();
        clip.reset();
        // ע��addRoundRect�Ĺ��췽���ĸ�������
        clip.addRoundRect(faceRect, faceCornerii, Direction.CW);

        canvas.save();
        canvas.translate(deltX, deltY);
        // ע��Region.Op�и��ֵ��ӷ�ʽ��ʹ��
        canvas.clipPath(clip, Region.Op.DIFFERENCE);
        canvas.drawColor(0xDF222222);
        canvas.drawPath(clip, mPaint);
        canvas.restore();

        Rect srcRect = new Rect(0, 0, 88, 106);
        srcRect.offset(deltX, deltY);
        // Ϊcanvas���DrawFilter
        PaintFlagsDrawFilter dfd = new PaintFlagsDrawFilter(
                Paint.ANTI_ALIAS_FLAG, Paint.FILTER_BITMAP_FLAG);
        canvas.setDrawFilter(dfd);
        canvas.clipPath(clip);
        canvas.drawBitmap(bitmap, srcRect, faceRect, mPaint);

        return bm;
    }

    // getArrowBitmap
    public Bitmap getArrowBitmap() {
        BitmapDrawable bd = (BitmapDrawable) this.getResources().getDrawable(
                R.drawable.beauty);
        Bitmap bitmap = bd.getBitmap();
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap bm = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);

        canvas.save();
        canvas.rotate(90, w / 2, h / 2);
        canvas.drawLine(w / 2, 0, 0, h / 2, mPaint);
        canvas.drawLine(w / 2, 0, w, h / 2, mPaint);
        canvas.drawLine(w / 2, 0, w / 2, h, mPaint);
        canvas.restore();

        // Draw circle
        mPaint.setColor(Color.YELLOW);
        canvas.drawLine(0, 0, w, h, mPaint);

        return bm;
    }
}
