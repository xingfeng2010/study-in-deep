package com.shixing.studycode.customview.measure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class ImageView extends View {

    private Bitmap mBitmap;

    public ImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制位图
        canvas.drawBitmap(mBitmap, this.getPaddingLeft(), this.getPaddingTop(), null);
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    /* (non-Javadoc)
     * @see android.view.View#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resultWidth,resultHeight;
        
       int widthMode = MeasureSpec.getMode(widthMeasureSpec);
       int width = MeasureSpec.getSize(widthMeasureSpec);
       android.util.Log.i("shixing","onMeasure width="+width);
       if (widthMode == MeasureSpec.EXACTLY) {
           resultWidth = width;
       } else {
           resultWidth = Math.min(mBitmap.getWidth()+this.getPaddingLeft()+this.getPaddingRight(), width);
       }
       
       int heightMode = MeasureSpec.getMode(heightMeasureSpec);
       int height = MeasureSpec.getSize(heightMeasureSpec);
       android.util.Log.i("shixing","onMeasure height="+height);
       if (heightMode == MeasureSpec.EXACTLY) {
           resultHeight = height;
       } else {
           resultHeight = Math.min(mBitmap.getHeight()+this.getPaddingBottom()+this.getPaddingTop(), height);
       }

       //设置测量尺寸
      this.setMeasuredDimension(resultWidth, resultHeight);
    }
}
