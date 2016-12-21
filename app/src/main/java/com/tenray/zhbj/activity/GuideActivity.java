package com.tenray.zhbj.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tenray.zhbj.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends Activity {
    private final String tag= getClass().getSimpleName();
    @BindView(R.id.vp_guide)
    ViewPager mViewPager;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;

    //引导页图片ID数组

    private  int[] mImageIds = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3
    };

    List<ImageView> mImageViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        initData();

        mViewPager.setAdapter(new GuideAdapter());
    }

    private void initData() {
        mImageViewList=new ArrayList<>();
        for (int i=0;i<mImageIds.length;i++){
            ImageView view =new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);//通过设置背景,可以让宽高填充布局
            mImageViewList.add(view);
        }
    }

    class GuideAdapter extends PagerAdapter{

        @Override
        // item的个数
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            Log.i(tag,"isViewFromObject:"+(view == object));
            return view == object;
        }
        // 初始化item布局
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
           ImageView view =mImageViewList.get(position);
            container.addView(view);
            return view;
        }
        // 销毁item
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
           // super.destroyItem(container, position, object);
        }
    }
}
