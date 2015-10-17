package com.shixing.studycode.activities;

import com.letv.sdk.Tv;
import com.letv.sdk.Listner.LoadDataListner;
import com.shixing.studycode.video.VideoView;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;

import com.shixing.study.R;

public class VideoPlayerActivity extends Activity implements LoadDataListner{
    private VideoView vvPlay;
    private String url ;
    Tv tv;
    

    private void initView() {
        vvPlay = (VideoView) findViewById(R.id.vvPlay);
    }

    public static native void start_tvm2p(String lib,String ip);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        tv = new Tv(this);
        tv.setLoadDataListner(this);
        tv.LoadChannel("CWX");
        initView();
    }



    @Override
    public void onFaild(String arg0) {
        // TODO Auto-generated method stub
        
    }



    @Override
    public void onLoadChannel(String arg0) {
        initPlayer();
    }



    @Override
    public void onLoadEpg(String arg0) {
        // TODO Auto-generated method stub
        
    }
    
    private void initPlayer() {
        String str = "http://stream.suntv.tvmining.com/approve/live?channel=CCTV1HD&type=iptv&etx=m3u8";
        String really = tv.getLiveUrl(str, Tv.IPSD);

        vvPlay.setVideoURI(Uri.parse(really));
        vvPlay.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                vvPlay.start();
            }
        });
        vvPlay.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // mp.setDataSource();
            }
        });

    }

    @Override
    public void onCurrentChannelProgram(String arg0) {
        // TODO Auto-generated method stub
        
    }
}
