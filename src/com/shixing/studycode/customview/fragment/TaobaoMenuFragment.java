package com.shixing.studycode.customview.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shixing.study.R;

public class TaobaoMenuFragment extends Fragment {

    private boolean areButtonsShowing;
    private RelativeLayout composerButtonsWrapper;
    private ImageView composerButtonsShowHideButtonIcon;
    private RelativeLayout composerButtonsShowHideButton;

    private int xOffset = 15;
    private int yOffset = -13;

    public TaobaoMenuFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        composerButtonsShowHideButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!areButtonsShowing) {
                    startAnimationsIn(composerButtonsWrapper, 300);
                    composerButtonsShowHideButtonIcon
                            .startAnimation(getRotateAnimation(0, -270, 300));
                } else {
                    startAnimationsOut(composerButtonsWrapper, 300);
                    composerButtonsShowHideButtonIcon
                            .startAnimation(getRotateAnimation(-270, 0, 300));
                }
                areButtonsShowing = !areButtonsShowing;
            }
        });
        for (int i = 0; i < composerButtonsWrapper.getChildCount(); i++) {
            composerButtonsWrapper.getChildAt(i).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                        }
                    });
        }

        composerButtonsShowHideButton.startAnimation(getRotateAnimation(0, 360,
                200));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.taobao_menu, container, false);

        composerButtonsWrapper = (RelativeLayout) rootView
                .findViewById(R.id.composer_buttons_wrapper);
        composerButtonsShowHideButton = (RelativeLayout) rootView
                .findViewById(R.id.composer_buttons_show_hide_button);
        composerButtonsShowHideButtonIcon = (ImageView) rootView
                .findViewById(R.id.composer_buttons_show_hide_button_icon);

        initOffset(this.getActivity());
        
        return rootView;
    }

    public void initOffset(Context context) {// 由布局文件
        xOffset = (int) (10.667 * context.getResources().getDisplayMetrics().density);
        yOffset = -(int) (8.667 * context.getResources().getDisplayMetrics().density);
    }

    public Animation getRotateAnimation(float fromDegrees, float toDegrees,
            int durationMillis) {
        RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(durationMillis);
        rotate.setFillAfter(true);
        return rotate;
    }

    public void startAnimationsIn(ViewGroup viewgroup, int durationMillis) {
        for (int i = 0; i < viewgroup.getChildCount(); i++) {
            ImageButton inoutimagebutton = (ImageButton) viewgroup
                    .getChildAt(i);
            inoutimagebutton.setVisibility(0);
            MarginLayoutParams mlp = (MarginLayoutParams) inoutimagebutton
                    .getLayoutParams();
            Animation animation = new TranslateAnimation(mlp.rightMargin
                    - xOffset, 0F, yOffset + mlp.bottomMargin, 0F);

            animation.setFillAfter(true);
            animation.setDuration(durationMillis);
            animation.setStartOffset((i * 100)
                    / (-1 + viewgroup.getChildCount()));
            animation.setInterpolator(new OvershootInterpolator(2F));
            inoutimagebutton.startAnimation(animation);

        }
    }

    public void startAnimationsOut(ViewGroup viewgroup, int durationMillis) {
        for (int i = 0; i < viewgroup.getChildCount(); i++) {
            final ImageButton inoutimagebutton = (ImageButton) viewgroup
                    .getChildAt(i);
            MarginLayoutParams mlp = (MarginLayoutParams) inoutimagebutton
                    .getLayoutParams();
            Animation animation = new TranslateAnimation(0F, mlp.rightMargin
                    - xOffset, 0F, yOffset + mlp.bottomMargin);

            animation.setFillAfter(true);
            animation.setDuration(durationMillis);
            animation.setStartOffset(((viewgroup.getChildCount() - i) * 100)
                    / (-1 + viewgroup.getChildCount()));// 顺序倒一下比较舒服
            animation.setInterpolator(new AnticipateInterpolator(2F));
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation arg0) {
                }

                @Override
                public void onAnimationRepeat(Animation arg0) {
                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    // TODO Auto-generated method stub
                    inoutimagebutton.setVisibility(8);
                }
            });
            inoutimagebutton.startAnimation(animation);
        }

    }

}
