package com.shixing.studycode.imageloader;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.shixing.study.R;
import com.shixing.studycode.imageloader.MyImageLoader.Type;

public class LoadImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mImgs;
    private LayoutInflater mInflater;
    private String mDirPath;
    private MyImageLoader mImageLoader;

    public LoadImageAdapter(Context applicationContext, List<String> imgs,
            String absolutePath) {
        mContext = applicationContext;
        mImgs = imgs;
        mInflater = LayoutInflater.from(applicationContext);
        mDirPath = absolutePath;
        mImageLoader = MyImageLoader.getInstance(3 , Type.LIFO);
    }

    @Override
    public int getCount() {
        return mImgs.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mImgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View converView, final ViewGroup parent) {
        ViewHolder holder = null;

        if (converView == null) {
            holder = new ViewHolder();
            converView = mInflater.inflate(R.layout.image_loader_grid_item,
                    parent, false);
            holder.mImageView = (ImageView) converView
                    .findViewById(R.id.grid_image);
            
            converView.setTag(holder);
        } else {
            holder = (ViewHolder) converView.getTag();
        }
        holder.mImageView.setImageResource(R.drawable.default_no);

        mImageLoader.loadImage(mDirPath + "/" + mImgs.get(position),
                holder.mImageView);

        return converView;
    }

    private static class ViewHolder {
        public ImageView mImageView;
    }
}
