package com.shixing.studycode.animation;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.shixing.study.R;

public class CamerDemoFragment extends Fragment implements
        OnSeekBarChangeListener {
    private SeekBar mSeekBar1, mSeekBar2, mSeekBar3, mSeekBar4, mSeekBar5,
            mSeekBar6;
    private TextView mTextView1, mTextView2, mTextView3, mTextView4,
            mTextView5, mTextView6;
    private ImageView mImageView;

    private Camera mCamera;

    // integer params
    private int rotateX, rotateY, rotateZ;
    private float skewX, skewY;
    private int translateZ;

    public CamerDemoFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.camera_demo, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mCamera = new Camera();

        mSeekBar1 = (SeekBar) rootView.findViewById(R.id.seekbar_1);
        mSeekBar1.setOnSeekBarChangeListener(this);
        mSeekBar2 = (SeekBar) rootView.findViewById(R.id.seekbar_2);
        mSeekBar2.setOnSeekBarChangeListener(this);
        mSeekBar3 = (SeekBar) rootView.findViewById(R.id.seekbar_3);
        mSeekBar3.setOnSeekBarChangeListener(this);
        mSeekBar4 = (SeekBar) rootView.findViewById(R.id.seekbar_4);
        mSeekBar4.setOnSeekBarChangeListener(this);
        mSeekBar5 = (SeekBar) rootView.findViewById(R.id.seekbar_5);
        mSeekBar5.setOnSeekBarChangeListener(this);
        mSeekBar6 = (SeekBar) rootView.findViewById(R.id.seekbar_6);
        mSeekBar6.setOnSeekBarChangeListener(this);

        mTextView1 = (TextView) rootView.findViewById(R.id.text_1);
        mTextView2 = (TextView) rootView.findViewById(R.id.text_2);
        mTextView3 = (TextView) rootView.findViewById(R.id.text_3);
        mTextView4 = (TextView) rootView.findViewById(R.id.text_4);
        mTextView5 = (TextView) rootView.findViewById(R.id.text_5);
        mTextView6 = (TextView) rootView.findViewById(R.id.text_6);

        mImageView = (ImageView) rootView.findViewById(R.id.image_view);
    }

    private void refreshImage() {
        //获取待处理的图像
        BitmapDrawable tmpBitDra = (BitmapDrawable) this.getActivity()
                .getResources().getDrawable(R.drawable.shishi);
        Bitmap tmpBit = tmpBitDra.getBitmap();
        //1.获取处理矩阵,记录一下初始状态.save()和restore()可以将图像过渡得柔和一些.
        mCamera.save();
        Matrix matrix = new Matrix();
        //rotate
        mCamera.rotateX(rotateX);
        mCamera.rotateY(rotateY);
        mCamera.rotateZ(rotateZ);
        //translate
        mCamera.translate(0, 0, translateZ);
        mCamera.getMatrix(matrix);
        mCamera.restore();
        //设置图像处理的中心点
        matrix.preTranslate(tmpBit.getWidth() / 2, tmpBit.getHeight() / 2);
        matrix.preSkew(skewX, skewY);
        //2.通过矩阵生成新图像(或直接作用于Canvas)
        Bitmap newBit = null;
        
        try {
         // 经过矩阵转换后的图像宽高有可能不大于0，此时会抛出IllegalArgumentException
            newBit = Bitmap.createBitmap(tmpBit, 0, 0,tmpBit.getWidth(),tmpBit.getHeight(),matrix,true);
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        }
        if (newBit != null) {
            mImageView.setImageBitmap(newBit);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) {
        if (seekBar == mSeekBar1) {
            mTextView1.setText(progress + "゜");
            rotateX = progress;
        } else if (seekBar == mSeekBar2) {
            mTextView2.setText(progress + "゜");
            rotateY = progress;
        } else if (seekBar == mSeekBar3) {
            mTextView3.setText(progress + "゜");
            rotateZ = progress;
        } else if (seekBar == mSeekBar4) {
            skewX = (progress - 100) * 1.0f / 100;
            mTextView4.setText(String.valueOf(skewX));
        } else if (seekBar == mSeekBar5) {
            skewY = (progress - 100) * 1.0f / 100;
            mTextView5.setText(String.valueOf(skewY));
        } else if (seekBar == mSeekBar6) {
            translateZ = progress - 100;
            mTextView6.setText(String.valueOf(translateZ));
        }

        this.refreshImage();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

}
