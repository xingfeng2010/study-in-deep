package com.shixing.studycode.customview.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.shixing.study.R;
import com.shixing.studycode.customview.views.ZoomView;

public class ZoomScaleFragment extends Fragment {

    private ViewPager mViewPager;

    private int[] mImgs = new int[] { R.drawable.turn1, R.drawable.turn2,
            R.drawable.turn3 };
    private ImageView[] mImageViews = new ImageView[mImgs.length];

    public ZoomScaleFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ZoomView imageView = new ZoomView(ZoomScaleFragment.this
                        .getActivity(), null);
                imageView.setImageResource(mImgs[position]);
                container.addView(imageView);
                mImageViews[position] = imageView;
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                    Object object) {
                container.removeView(mImageViews[position]);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return mImgs.length;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.zoom_scale, container, false);

        mViewPager = (ViewPager) rootView.findViewById(R.id.id_viewpager);
        return rootView;
    }

}
