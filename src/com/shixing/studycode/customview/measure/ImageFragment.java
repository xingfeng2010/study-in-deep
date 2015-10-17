package com.shixing.studycode.customview.measure;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shixing.study.R;

public class ImageFragment extends Fragment {
    ImageView mImageView;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.shishi);
        mImageView.setBitmap(bitmap);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.image_view, container,false);
        mImageView = (ImageView)rootView.findViewById(R.id.image_view);
        return rootView;
    }

    public ImageFragment() {
    }

}
