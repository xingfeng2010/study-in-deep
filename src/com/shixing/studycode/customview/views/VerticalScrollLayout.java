package com.shixing.studycode.customview.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class VerticalScrollLayout extends ViewGroup {
    private final Context mContext;
    private Scroller mScroller;
    private int mScreenWidth = 0;
    private int mScreenHeigh = 0;

    private int mLastDownY = 0;
    private int mCurryY;
    private int mDelY;

    private int mScrollStart;
    private int mScrollEnd;

    // 总页数
    private int pageCount = 0;
    // 当前页
    private int pageNum = 1;

    public VerticalScrollLayout(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public VerticalScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int count = this.getChildCount();
            pageCount = count;
            for (int i = 0; i < count; i++) {
                View childView = this.getChildAt(i);
                childView.layout(0, i * mScreenHeigh, mScreenWidth, (i + 1)
                        * mScreenHeigh);
            }
        }

    }

    private void init() {
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
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int count = this.getChildCount();
        if (count > 0) {
            this.measureChildren(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:
            mScrollStart = this.getScrollY();
            mLastDownY = (int) event.getY();
            break;
        case MotionEvent.ACTION_MOVE:
            mCurryY = (int) event.getY();
            mDelY = mCurryY - mLastDownY;
            mLastDownY = mCurryY;

            this.scrollBy(0, -mDelY);

            break;
        case MotionEvent.ACTION_UP:
            mCurryY = (int) event.getY();
            mDelY = mCurryY - mLastDownY;

            mScrollEnd = this.getScrollY();
            int destScroll = mScrollEnd - mScrollStart;

            if (destScroll > 0) {// 往下翻
                if (pageNum < pageCount && destScroll > mScreenHeigh / 2) {
                    this.startBounceAnim(this.getScrollY(), this.mScreenHeigh
                            - destScroll, 450);
                    pageNum++;
                } else {
                    // 向上滑动未超过半个屏幕高的时候 开启向下弹动动画
                    this.startBounceAnim(this.getScrollY(), -destScroll, 1000);
                }
            } else if (destScroll < 0) {// 往上翻
                if (pageNum > 1 && -destScroll > mScreenHeigh / 2) {
                    this.startBounceAnim(this.getScrollY(), -mScreenHeigh
                            - destScroll, 1000);
                    pageNum--;
                } else {
                    this.startBounceAnim(this.getScrollY(), -destScroll, 450);
                }
            }

            break;
        }
        return true;
    }

    // 推动门的动画
    public void startBounceAnim(int startY, int dy, int duration) {
        android.util.Log.i("shixing", "startBounceAnim startY=" + startY
                + "::dy=" + dy);
        this.mScroller.startScroll(0, startY, 0, dy, duration);
        this.invalidate();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            this.postInvalidate();
        } else {

        }
    }
}
