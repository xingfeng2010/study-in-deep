package com.shixing.studycode.customview.pageturn;

import java.util.ArrayList;
import java.util.List;

import com.shixing.studycode.utils.MeasureUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class PageTurnView extends View {
    private static final float TEXT_SIZE_NORMAL = 1 / 40F, TEXT_SIZE_LARGER = 1 / 20F;// 标准文字尺寸和大号文字尺寸的占比
    private TextPaint mTextPaint;// 文本画笔 
    private Context mContext;// 上下文环境引用  
    
    List<Bitmap> mBitmaps;
    int mViewWidth, mViewHeight;
    
    private float mTextSizeNormal, mTextSizeLarger;// 标准文字尺寸和大号文字尺寸 
    
    /**
     * mClipX为0意味着图片已经被裁剪完了
     */
    private float mClipX;
    private float autoAreaLeft, autoAreaRight;
    private boolean isLastPage;
    private int pageIndex;
    private boolean isNextPage;
    private float mCurPointX;//指尖触碰屏幕时点x的坐标值
    private float mMoveValid;//移动事件的有效距离

    public PageTurnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG); 
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        // 获取屏幕尺寸数据
        int[] screenSize = MeasureUtil.getScreenSize((Activity) context);

        // 获取屏幕中点坐标
        mViewWidth = screenSize[0];
        mViewHeight = screenSize[1];

    }

    @Override
    public void draw(Canvas canvas) {
        // TODO Auto-generated method stub
        if (mBitmaps.isEmpty()) {
            defaultDisplay(canvas);
        } else {
            drawBtimaps(canvas);
        }
    }

    /**
     * 设置位图数据
     * @param mBitmaps
     *            位图数据列表
     */
    public synchronized void setBitmaps(List<Bitmap> bitmaps) {
        /*
         * 如果数据为空则抛出异常
         */
        if (null == bitmaps || bitmaps.size() == 0)
            throw new IllegalArgumentException("no bitmap to display");

        /*
         * 如果数据长度小于2则GG思密达
         */
        if (bitmaps.size() < 2)
            throw new IllegalArgumentException(
                    "fuck you and fuck to use imageview");
        this.mBitmaps = bitmaps;
        initBitmaps();

        invalidate();
    }

    /**
     * 绘制位图
     * @param canvas
     *            Canvas对象
     */
    @SuppressLint("ShowToast")
    private void drawBtimaps(Canvas canvas) {
        // 绘制位图前重置isLastPage为false
        isLastPage = false;

        pageIndex = pageIndex < 0 ? 0 : pageIndex;
        pageIndex = pageIndex > mBitmaps.size() ? mBitmaps.size() : pageIndex;

        // 计算数据起始位置
        int start = mBitmaps.size() - 2 - pageIndex;
        int end = mBitmaps.size() - pageIndex;
        /**
         * 如果数据起点位置小于0则表示当前已经到了最后一张图片
         */
        if (start < 0) {
            isLastPage = true;
            Toast.makeText(this.getContext(), "亲爱的,到最后一页了!", Toast.LENGTH_SHORT)
                    .show();
            start = 0;
            end = 1;
        }

        for (int i = start; i < end; i++) {
            canvas.save();

            /**
             * 仅剪裁位于最顶层的画面区域
             * 到了末页则不再执行裁剪
             */
            if (!isLastPage && i == end - 1) {
                canvas.clipRect(0, 0, mClipX, mViewHeight);
            }

            canvas.drawBitmap(mBitmaps.get(i), 0, 0, null);

            canvas.restore();
        }
    }

    /**
     * 初始化位图数据
     * 缩放位图尺寸与屏幕匹配
     */
    private void initBitmaps() {
        List<Bitmap> temp = new ArrayList<Bitmap>();
        for (int i = mBitmaps.size() - 1; i >= 0; i--) {
            Bitmap bitmap = Bitmap.createScaledBitmap(mBitmaps.get(i),
                    mViewWidth, mViewHeight, true);
            temp.add(bitmap);
        }
        mBitmaps = temp;
    }

    /**
     * 默认显示
     * @param canvas
     *            Canvas对象
     */
    private void defaultDisplay(Canvas canvas) {
        // 绘制底色
        canvas.drawColor(Color.WHITE);

        // 绘制标题文本
        mTextPaint.setTextSize(50);
        mTextPaint.setColor(Color.RED);
        canvas.drawText("FBI WARNING", mViewWidth / 2, mViewHeight / 4,
                mTextPaint);

        // 绘制提示文本
        mTextPaint.setTextSize(30);
        mTextPaint.setColor(Color.BLACK);
        canvas.drawText("Please set data use setBitmaps method",
                mViewWidth / 2, mViewHeight / 3, mTextPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mClipX = mViewWidth;

        initBitmaps();
        
     // 计算文字尺寸  
        mTextSizeNormal = TEXT_SIZE_NORMAL * mViewHeight;  
        mTextSizeLarger = TEXT_SIZE_LARGER * mViewHeight;  
        
        // 计算控件左侧和右侧自动吸附的区域
        autoAreaLeft = mViewWidth * 1 / 5F;
        autoAreaRight = mViewWidth * 4 / 5F;
        
        mViewWidth = w;
        mViewHeight = h;
        
        // 计算一度的有效距离  
        mMoveValid = mViewWidth * 1 / 100F;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 每次触发TouchEvent重置isNextPage为true
        isNextPage = true;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:
            // 获取触摸点的x坐标
            mCurPointX = event.getX();

            /**
             * 如果事件点位于回滚区域
             */
            if (mCurPointX < autoAreaLeft) {
                //那就不翻下一页了而是上一页
                isNextPage = false;
                pageIndex--;
                mClipX = mCurPointX;
                invalidate();
            }
            break;
        case MotionEvent.ACTION_MOVE://滑动时
            float SlideDis = mCurPointX - event.getX();
            if (Math.abs(SlideDis) > mMoveValid ) {
                mClipX= event.getX();

                this.invalidate();
            }
            break;
        case MotionEvent.ACTION_UP:// 触点抬起时
            // 判断是否需要自动滑动
            judgeSlideAuto();
            
            /*
             * 如果当前页不是最后一页,如果是需要翻下一页
             * 并且上一页已被clip掉
             */
            if (!isLastPage && isNextPage && mClipX <= 0) {
                pageIndex++;
                mClipX = mViewWidth;
                this.invalidate();
            }
            break;
        }

        return true;
    }

    /**
     * 判断是否需要自动滑动
     */
    private void judgeSlideAuto() {
        if (mClipX < autoAreaLeft) {
            while (mClipX > 0) {
                mClipX--;
                this.invalidate();
            }
        }

        if (mClipX > autoAreaRight) {
            while (mClipX < mViewWidth) {
                mClipX++;
                this.invalidate();
            }
        }

    }
}
