package com.shixing.studycode.activities;

import com.shixing.study.R;
import com.shixing.studycode.customview.camera.CamerDemoFragment;
import com.shixing.studycode.customview.fragment.SlideMenuFragment;
import com.shixing.studycode.customview.fragment.TaobaoMenuFragment;
import com.shixing.studycode.customview.fragment.TongXinFragment;
import com.shixing.studycode.customview.fragment.VerticalScrollFragment;
import com.shixing.studycode.customview.fragment.ZoomScaleFragment;
import com.shixing.studycode.customview.measure.CustomLayoutFragment;
import com.shixing.studycode.customview.measure.IconFragment;
import com.shixing.studycode.customview.measure.ImageFragment;
import com.shixing.studycode.customview.pageturn.FoldFragment;
import com.shixing.studycode.customview.pageturn.ListanimFragment;
import com.shixing.studycode.customview.pageturn.PageturnFragment;
import com.shixing.studycode.customview.switchbutton.SwitchButtonFragment;
import com.shixing.studycode.customview.win8style.Win8StyleFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class CustomViewActivity extends Activity {
    private static final String TAG = "CustomViewActivity shixing";

    private String[] mTextList = new String[] { "AnimList", "TurnPage", "Fold",
            "Image", "Icon", "ViewGroup", "switchButton", "Win8Style",
            "CameraDemo", "verticallScroll", "tongxin", "taobaomenu",
            "Slidemenu","ZoomScale"};

    private ListView mListView;
    private ListanimFragment mListAnimFragment;
    private PageturnFragment mPageTurnFragment;
    private FoldFragment mFoldFragment;
    private ImageFragment mImageFragment;
    private IconFragment mIconFragment;
    private CustomLayoutFragment mCustomLayoutFragment;
    private SwitchButtonFragment mSwitchButtonFragment;
    private Win8StyleFragment mWin8StyleFragment;
    private CamerDemoFragment mCamerDemoFragment;
    private VerticalScrollFragment mVerticalScrollFragment;
    private TongXinFragment mTongXinFragment;
    private TaobaoMenuFragment mTaobaoMenuFragment;
    private SlideMenuFragment mSlideMenuFragment;
    private ZoomScaleFragment mZoomScaleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.custom_view);
        initFragment();
        initView();
    }

    private void initFragment() {
        mListAnimFragment = new ListanimFragment();
        mPageTurnFragment = new PageturnFragment();
        mFoldFragment = new FoldFragment();
        mImageFragment = new ImageFragment();
        mIconFragment = new IconFragment();
        mCustomLayoutFragment = new CustomLayoutFragment();
        mSwitchButtonFragment = new SwitchButtonFragment();
        mWin8StyleFragment = new Win8StyleFragment();
        mCamerDemoFragment = new CamerDemoFragment();
        mVerticalScrollFragment = new VerticalScrollFragment();
        mTongXinFragment = new TongXinFragment();
        mTaobaoMenuFragment = new TaobaoMenuFragment();
        mSlideMenuFragment = new SlideMenuFragment();
        mZoomScaleFragment = new ZoomScaleFragment();
    }

    private void initView() {
        mListView = (ListView) this.findViewById(R.id.list);
        mListView.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return mTextList.length;
            }

            @Override
            public Object getItem(int arg0) {
                return null;
            }

            @Override
            public long getItemId(int arg0) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup arg2) {
                convertView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.custom_list_item, null);
                Button button = (Button) convertView.findViewById(R.id.button);
                button.setText(mTextList[position]);

                return convertView;
            }

        });

        mListView.requestFocus();
        mListView.setSelected(true);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Log.i(TAG, "onItemClick position=" + position);
                switch (position) {
                case 0:
                    replaceFragment(mListAnimFragment);
                    break;
                case 1:
                    replaceFragment(mPageTurnFragment);
                    break;
                case 2:
                    replaceFragment(mFoldFragment);
                    break;
                case 3:
                    replaceFragment(mImageFragment);
                    break;
                case 4:
                    replaceFragment(mIconFragment);
                case 5:
                    replaceFragment(mCustomLayoutFragment);
                    break;
                case 6:
                    replaceFragment(mSwitchButtonFragment);
                    break;
                case 7:
                    replaceFragment(mWin8StyleFragment);
                    break;
                case 8:
                    replaceFragment(mCamerDemoFragment);
                    break;
                case 9:
                    replaceFragment(mVerticalScrollFragment);
                    break;
                case 10:
                    replaceFragment(mTongXinFragment);
                    break;
                case 11:
                    replaceFragment(mTaobaoMenuFragment);
                    break;
                case 12:
                    replaceFragment(mSlideMenuFragment);
                    break;
                case 13:
                    replaceFragment(mZoomScaleFragment);
                    break;
                }

            }

        });

    }

    protected void replaceFragment(Fragment fragment) {
        mListView.setVisibility(View.GONE);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (!mListView.isShown()) {
            mListView.setVisibility(View.VISIBLE);
            return;
        }

        super.onBackPressed();
    }
}
