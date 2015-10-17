package com.shixing.studycode.customview.pageturn;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.shixing.study.R;

public class PageturnFragment extends Fragment {
private PageTurnView mPageTurnView;

    public PageturnFragment() {

    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        showTurnPageView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.turn_page, container,
                false);
        mPageTurnView = (PageTurnView) rootView.findViewById(R.id.page_turn);
        return rootView;
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

        mPageTurnView.setBitmaps(map);
    }

}
