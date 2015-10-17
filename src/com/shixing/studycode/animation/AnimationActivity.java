package com.shixing.studycode.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shixing.study.R;
import com.shixing.studycode.utils.MeasureUtil;

public class AnimationActivity extends Activity implements
        OnCheckedChangeListener {
    private static final String TAG = "AnimationActivity";

    private ImageView mAnimImage;
    private ImageView mAnimBall;
    private HorizontalScrollView mScrollView;
    private LinearLayout mContainer;

    private int mVal;

    private int mScreenWidth;
    private int mScreenHeight;

    private CheckBox mAppear;

    private CheckBox mChangeAppear;

    private CheckBox mDisAppear;

    private CheckBox mChangeDisAppear;

    private GridLayout mGridLayout;

    private LayoutTransition mTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.animation_main);

        mAnimImage = (ImageView) this.findViewById(R.id.anim_image);
        mAnimBall = (ImageView) this.findViewById(R.id.anim_ball);
        mScrollView = (HorizontalScrollView) this
                .findViewById(R.id.anim_scroll);
        mContainer = (LinearLayout) this.findViewById(R.id.id_container);

        int[] size = MeasureUtil.getScreenSize(this);
        mScreenWidth = size[0];
        mScreenHeight = size[1];

        initBujuAnim();
    }

    private void initBujuAnim() {
        mAppear = (CheckBox) findViewById(R.id.id_appear);
        mChangeAppear = (CheckBox) findViewById(R.id.id_change_appear);
        mDisAppear = (CheckBox) findViewById(R.id.id_disappear);
        mChangeDisAppear = (CheckBox) findViewById(R.id.id_change_disappear);

        mAppear.setOnCheckedChangeListener(this);
        mChangeAppear.setOnCheckedChangeListener(this);
        mDisAppear.setOnCheckedChangeListener(this);
        mChangeDisAppear.setOnCheckedChangeListener(this);

        // 创建一个GridLayout
        mGridLayout = new GridLayout(this);
        // 设置每列5个按钮
        mGridLayout.setColumnCount(5);
        // 添加到布局中
        mContainer.addView(mGridLayout);
        // 默认动画全部开启
        mTransition = new LayoutTransition();
        mGridLayout.setLayoutTransition(mTransition);
    }

    public void rotateyAnimRun(View view) {
        ObjectAnimator.ofFloat(mAnimImage, "rotationX", 0.0f, 360.0f)
                .setDuration(500).start();
    }

    public void smallAnimRun(View view) {
        // the first way
        ObjectAnimator anim = ObjectAnimator.ofFloat(mAnimImage, "lsx", 1.0f,
                0.0f, 1.0f).setDuration(500);
        anim.start();

        anim.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                mAnimImage.setAlpha(cVal);
                mAnimImage.setScaleX(cVal);
                mAnimImage.setScaleY(cVal);
            }

        });

        // another method
        // PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha",
        // 1f,0f,1f);
        // PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX",
        // 1f,0f,1f);
        // PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY",
        // 1f,0f,1f);
        // ObjectAnimator.ofPropertyValuesHolder(mAnimImage,
        // pvhX,pvhY,pvhZ).setDuration(1000).start();
    }

    public void resetAnimRun(View view) {
        mAnimImage.setVisibility(View.VISIBLE);
        mAnimImage.setAlpha(1.0f);
        mAnimImage.setScaleX(1.0f);
        mAnimImage.setScaleY(1.0f);

        mScrollView.setVisibility(View.VISIBLE);
        mAnimBall.setX(0);
        mAnimBall.setY(0);

        mAnimBall.setVisibility(View.VISIBLE);
    }

    public void verticalAnimRun(View view) {
        mAnimImage.setVisibility(View.GONE);

        ValueAnimator animator = ValueAnimator.ofFloat(0, mScreenHeight
                - mAnimBall.getHeight());
        animator.setTarget(mAnimBall);
        animator.setDuration(1000);
        animator.start();
        animator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimBall.setTranslationY((Float) animation.getAnimatedValue());
            }

        });

    }

    public void paowuAnimRun(View view) throws InterruptedException {
        mAnimImage.setVisibility(View.GONE);

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {

            @Override
            public PointF evaluate(float fraction, PointF startValue,
                    PointF endValue) {
                // x方向200px/s ，则y方向0.5 * 10 * t
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }

        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                mAnimBall.setX(point.x);
                mAnimBall.setY(point.y);
            }

        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e(TAG, "onAnimationEnd");
                ViewGroup parent = (ViewGroup) mAnimBall.getParent();
                if (parent != null) {
                    // parent.removeView(mAnimBall);
                }
            }
        });

    }

    public void togetherAnimRun(View view) {
        mAnimImage.setVisibility(View.GONE);

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(mAnimBall,
                "translationX", mAnimBall.getX(), mScreenWidth / 2);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(mAnimBall,
                "translationY", mAnimBall.getY(), mScreenHeight / 2);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(mAnimBall, "scaleX",
                1.0f, 2f);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(mAnimBall, "scaleY",
                1.0f, 2f);

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(500);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.play(anim1).with(anim2);
        animSet.play(anim3).with(anim4);
        animSet.play(anim3).after(anim1);

        animSet.start();
    }

    public void layoutAnimRun(View view) {
        mAnimImage.setVisibility(View.GONE);
        mScrollView.setVisibility(View.GONE);
        mAnimBall.setVisibility(View.GONE);

        mContainer.setVisibility(View.VISIBLE);
    }

    public void sequealAnimRun(View view) {

    }

    public void addBtn(View view) {
        final Button button = new Button(this);
        button.setLayoutParams(new LayoutParams(70, 50));
        button.setText(++mVal + "");
        mGridLayout.addView(button, Math.min(1, mGridLayout.getChildCount()));
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mGridLayout.removeView(button);
            }

        });
    }

    @Override
    public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
        mTransition = new LayoutTransition();
        mTransition.setAnimator(
                LayoutTransition.APPEARING,// 当一个View在ViewGroup中出现时，对此View设置的动画
                // (mAppear.isChecked() ? mTransition
                // .getAnimator(LayoutTransition.APPEARING) : null));
                (mAppear.isChecked() ? ObjectAnimator.ofFloat(this, "scaleX",
                        0, 1) : null));

        mTransition
                .setAnimator(
                        LayoutTransition.CHANGE_APPEARING,// 当一个View在ViewGroup中出现时，对此View对其他View位置造成影响，对其他View设置的动画
                        (mChangeAppear.isChecked() ? mTransition
                                .getAnimator(LayoutTransition.CHANGE_APPEARING)
                                : null));

        mTransition.setAnimator(
                LayoutTransition.DISAPPEARING,// 当一个View在ViewGroup中消失时，对此View设置的动画
                (mChangeAppear.isChecked() ? mTransition
                        .getAnimator(LayoutTransition.DISAPPEARING) : null));

        mTransition.setAnimator(
                LayoutTransition.CHANGE_DISAPPEARING,// 当一个View在ViewGroup中消失时，对此View对其他View位置造成影响，对其他View设置的动画
                (mChangeAppear.isChecked() ? mTransition
                        .getAnimator(LayoutTransition.CHANGE_DISAPPEARING)
                        : null));

        mGridLayout.setLayoutTransition(mTransition);
    }
}
