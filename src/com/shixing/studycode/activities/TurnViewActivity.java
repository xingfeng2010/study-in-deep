package com.shixing.studycode.activities;

import com.shixing.study.R;
import com.shixing.studycode.customview.views.MySurfaceView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class TurnViewActivity extends Activity {

    private ImageView mStartBtn;
    private MySurfaceView mLuckyPanView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.turnview);
        
        mLuckyPanView = (MySurfaceView) findViewById(R.id.id_luchypan);  
        mStartBtn = (ImageView) findViewById(R.id.start_button);  
        mStartBtn.setOnClickListener(new OnClickListener()  {  
            @Override  
            public void onClick(View v)  {  
                if (!mLuckyPanView.isStart())  {
                    mStartBtn.setImageResource(R.drawable.stop);
                    mLuckyPanView.luckyStart(2);  
                } else {
                    if (!mLuckyPanView.isShouldEnd()) {  
                        mStartBtn.setImageResource(R.drawable.start);
                        mLuckyPanView.luckyEnd();  
                    }  
                }  
            }  
        });  
    }  

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
