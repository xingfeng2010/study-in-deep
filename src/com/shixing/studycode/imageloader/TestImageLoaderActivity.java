package com.shixing.studycode.imageloader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.shixing.study.R;

public class TestImageLoaderActivity extends Activity {

    private GridView mGridView;
    private ProgressDialog mProgressDialog;
    private HashSet mDirPaths = new HashSet<String>();
    protected int mPicsSize;
    protected File mImageDir;
    protected List<String> mImgs;
    protected ListAdapter mAdapter;
    protected Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            mProgressDialog.dismiss();
            mImgs = Arrays.asList(mImageDir.list(new FilenameFilter() {

                @Override
                public boolean accept(File dir, String filename) {
                    if (filename.endsWith(".jpg")) {
                        return true;
                    }
                    return false;
                }

            }));

            mAdapter = new LoadImageAdapter(getApplicationContext(), mImgs,
                    mImageDir.getAbsolutePath());
            mGridView.setAdapter(mAdapter);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.image_loader_layout);

        mGridView = (GridView) this.findViewById(R.id.grid_view);
        getImages();
    }

    /**
     * 利用ContentProvider描述手机中的图片,此方法在运行在子线程中完成图片的扫描,最终获得jpg最多的那个文件夹
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "没有外部存储设备", Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressDialog = ProgressDialog.show(this, null, "正在加载......");

        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = TestImageLoaderActivity.this
                        .getContentResolver();

                // 只查询jpeg和jng的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[] { "image/jpeg", "image/png" },
                        MediaStore.Images.Media.DATE_MODIFIED);

                while (mCursor.moveToNext()) {
                    // 获取图片路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    // 获取该图片的父路径
                    File parentFile = new File(path).getParentFile();
                    String dirPath = parentFile.getAbsolutePath();

                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                    }

                    int picSize = parentFile.list(new FilenameFilter() {

                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg")) {
                                return true;
                            }
                            return false;
                        }

                    }).length;

                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImageDir = parentFile;
                    }
                }

                mCursor.close();
                // 释放内存
                mDirPaths = null;
                mHandler.sendEmptyMessage(0x110);
            }

        }).start();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
