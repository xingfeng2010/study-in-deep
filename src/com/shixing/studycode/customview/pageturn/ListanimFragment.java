package com.shixing.studycode.customview.pageturn;

import com.shixing.study.R;
import com.shixing.studycode.customview.shader.AnimListView;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListanimFragment extends Fragment {
    private AnimListView mListview;
    public ListanimFragment() {
        
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        showAnimListView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.list_anim, container,
                false);
        mListview = (AnimListView) rootView.findViewById(R.id.anim_list);
        return rootView;
    }
    
    /**
     * 自定义listView
     */
    private void showAnimListView() {
        // TODO Auto-generated method stub

        mListview.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return 100;
            }

            @Override
            public Object getItem(int arg0) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public long getItemId(int arg0) {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public View getView(int arg0, View convertView, ViewGroup arg2) {
                convertView = LayoutInflater.from(mListview.getContext()).inflate(R.layout.anim_list_item, null);
                return convertView;
            }
            
        });
    }

}
