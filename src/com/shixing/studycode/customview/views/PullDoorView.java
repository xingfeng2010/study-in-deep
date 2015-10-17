
package com.shixing.studycode.customview.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.shixing.study.R;

/**
 * zaker自定义效果页面
 * 
 * @author Administrator
 */
public class PullDoorView extends RelativeLayout {

    private final Context mContext;

    private Scroller mScroller;

    private int mScreenWidth = 0;

    private int mScreenHeigh = 0;

    private int mLastDownY = 0;

    private int mCurryY;

    private int mDelY;

    private boolean mCloseFlag = false;

    private ImageView mImgView;

    public PullDoorView(Context context) {
        super(context);
        this.mContext = context;
        this.setupView();
    }

    public PullDoorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.setupView();
    }

    @SuppressLint("NewApi")
    private void setupView() {

        // 这个Interpolator你可以设置别的 我这里选择的是有弹跳效果的Interpolator
        Interpolator polator = new BounceInterpolator();
        this.mScroller = new Scroller(this.mContext, polator);

        // 获取屏幕分辨率
        WindowManager wm = (WindowManager) (this.mContext
                .getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        this.mScreenHeigh = dm.heightPixels;
        this.mScreenWidth = dm.widthPixels;

        // 这里你一定要设置成透明背景,不然会影响你看到底层布局
        this.setBackgroundColor(Color.argb(0, 0, 0, 0));
        this.mImgView = new ImageView(this.mContext);
        this.mImgView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        this.mImgView.setScaleType(ImageView.ScaleType.FIT_XY);// 填充整个屏幕
        this.mImgView.setImageResource(R.drawable.home); // 默认背景
        this.addView(this.mImgView);
    }

    // 设置推动门背景
    public void setBgImage(int id) {
        this.mImgView.setImageResource(id);
    }

    // 设置推动门背景
    public void setBgImage(Drawable drawable) {
        this.mImgView.setImageDrawable(drawable);
    }

    // 推动门的动画
    public void startBounceAnim(int startY, int dy, int duration) {
        android.util.Log.i("shixing", "startBounceAnim startY=" + startY + "::dy=" + dy);
        this.mScroller.startScroll(0, startY, 0, dy, duration);
        this.invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                this.mLastDownY = (int) event.getY();
                System.err.println("ACTION_DOWN=" + this.mLastDownY);
                return true;
            case MotionEvent.ACTION_MOVE:
                this.mCurryY = (int) event.getY();
                System.err.println("ACTION_MOVE=" + this.mCurryY);
                this.mDelY = this.mCurryY - this.mLastDownY;
                // 只准上滑有效
                if (this.mDelY < 0) {
                    this.scrollTo(0, -this.mDelY);
                }
                System.err.println("-------------  " + this.mDelY);

                break;
            case MotionEvent.ACTION_UP:
                this.mCurryY = (int) event.getY();
                this.mDelY = this.mCurryY - this.mLastDownY;
                if (this.mDelY < 0) {

                    if (Math.abs(this.mDelY) > this.mScreenHeigh / 2) {
                        // 向上滑动超过半个屏幕高的时候 开启向上消失动画
                        this.startBounceAnim(this.getScrollY(), this.mScreenHeigh, 450);
                        this.mCloseFlag = true;
                    } else {
                        // 向上滑动未超过半个屏幕高的时候 开启向下弹动动画
                        this.startBounceAnim(this.getScrollY(), -this.getScrollY(), 1000);

                    }
                }

                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {

        if (this.mScroller.computeScrollOffset()) {
            this.scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            Log.i("scroller", "getCurrX()= " + this.mScroller.getCurrX()
                    + "     getCurrY()=" + this.mScroller.getCurrY()
                    + "  getFinalY() =  " + this.mScroller.getFinalY());
            // 不要忘记更新界面
            this.postInvalidate();
        } else {
            if (this.mCloseFlag) {
                this.setVisibility(View.GONE);
            }
        }
    }

}
