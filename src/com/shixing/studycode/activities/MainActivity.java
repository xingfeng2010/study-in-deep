package com.shixing.studycode.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.shixing.study.R;
import com.shixing.studycode.animation.AnimationActivity;
import com.shixing.studycode.customview.views.FontView;
import com.shixing.studycode.horizontalscrollview.HorizontalActivity;
import com.shixing.studycode.imageloader.TestImageLoaderActivity;
import com.shixing.studycode.indicatefragment.ScrollFragmentActivity;

/**
 * @author Shixing
 * @since 2015/06/107
 */
public class MainActivity extends Activity {
    private static final String TAG = "MainActivity shixing";

    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Log.i(TAG, "onCreate width=" + width + "\n" + "height=" + height);
    }

    public void initLight() {
        FontView view = new FontView(this, null);
        this.setContentView(view);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
    }

    /**
     * launch bitmap activity
     * @param view
     */
    public void startBitMapActivity(View view) {
        Intent intent = new Intent(this, BitMapActivity.class);
        this.startActivity(intent);
    }

    /**
     * launch private video activity
     * @param view
     */
    public void startPrivateVidoActivity(View view) {
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        this.startActivity(intent);
    }

    /**
     * launch custom view activity
     * @param view
     */
    public void startCustomViewActivity(View view) {
        Intent intent = new Intent(this, CustomViewActivity.class);
        this.startActivity(intent);
    }

    /**
     * launch custom view activity
     * @param view
     */
    public void startScrollFragmentActivity(View view) {
        Intent intent = new Intent(this, ScrollFragmentActivity.class);
        this.startActivity(intent);
    }

    public void startHorizontalActivity(View view) {
        Intent intent = new Intent(this, HorizontalActivity.class);
        this.startActivity(intent);
    }

    public void startAnimationActivity(View view) {
        Intent intent = new Intent(this, AnimationActivity.class);
        this.startActivity(intent);
    }

    public void startImageLoaderActivity(View view) {
        Intent intent = new Intent(this, TestImageLoaderActivity.class);
        this.startActivity(intent);
    }

}
