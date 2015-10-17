package com.shixing.studycode.customview.measure;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CustomLayout extends ViewGroup {

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (this.getChildCount() > 0) {
            this.measureChildren(widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        if (this.getChildCount() > 0) {
            int addHeight = 0;
            for (int i = 0; i < this.getChildCount(); i++) {
                View view = this.getChildAt(i);

                view.layout(0, addHeight, view.getMeasuredWidth(),
                        view.getMeasuredHeight()+addHeight);

                addHeight += view.getMeasuredHeight();
            }
        }
    }

}
