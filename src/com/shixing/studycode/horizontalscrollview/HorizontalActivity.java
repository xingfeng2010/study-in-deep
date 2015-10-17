package com.shixing.studycode.horizontalscrollview;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shixing.study.R;

public class HorizontalActivity extends Activity {

    private int[] mImageIds = new int[] {R.drawable.turn1,R.drawable.turn2,R.drawable.turn3,R.drawable.turn4,R.drawable.turn5
    };

    private LayoutInflater mInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.horizontal_scroll);

        mInflater = LayoutInflater.from(this);

        initView();
    }

    private void initView() {
        LinearLayout id_gallery = (LinearLayout)this.findViewById(R.id.id_gallery);

        for(int i=0;i<mImageIds.length;i++) {
            View view = mInflater.inflate(R.layout.gallery_item, id_gallery,false);
            
            ImageView img = (ImageView) view.findViewById(R.id.gallery_item_image);
            img.setImageResource(mImageIds[i]);

            TextView tv = (TextView) view.findViewById(R.id.gallery_item_tv);
            tv.setText("this is liu!");

            id_gallery.addView(view);
        }
    }

}
