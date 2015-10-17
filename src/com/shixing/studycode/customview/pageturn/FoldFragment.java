package com.shixing.studycode.customview.pageturn;

import java.util.ArrayList;
import java.util.List;

import com.shixing.study.R;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FoldFragment extends Fragment {
    private FoldView mFoldView;
    public FoldFragment() {
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showTurnPageView();
    }

    private void showTurnPageView() {
        List<Bitmap> map = new ArrayList<Bitmap>();
        // 获取位图
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),
                R.drawable.turn1);
        map.add(bitmap1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),
                R.drawable.turn2);
        map.add(bitmap2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(),
                R.drawable.turn3);
        map.add(bitmap3);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(),
                R.drawable.turn4);
        map.add(bitmap4);
        Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(),
                R.drawable.turn5);
        map.add(bitmap5);

        mFoldView.setBitmaps(map);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fold_view, container,
                false);
        mFoldView = (FoldView) rootView.findViewById(R.id.fold_view);
        return rootView;
    }
}
