package com.tenray.zhbj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tenray.zhbj.R;
import com.tenray.zhbj.util.Constants;
import com.tenray.zhbj.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends Activity {
    private final String tag = getClass().getSimpleName();
    @BindView(R.id.vp_guide)
    ViewPager mViewPager;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;
    @BindView(R.id.point_red)
    ImageView mRedPoint;

    //引导页图片ID数组

    private int[] mImageIds = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3
    };

    List<ImageView> mImageViewList;
    // 小红点移动距离
    private int mPointDis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 当页面滑动过程中的回调
                Log.i(tag, "当前位置:" + position + ";移动偏移百分比:"
                        + positionOffset);
                int leftMargin = (int) (mPointDis * positionOffset) + position
                        * mPointDis;// 计算小红点当前的左边距

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mRedPoint
                        .getLayoutParams();
                params.leftMargin = leftMargin;// 修改左边距

                mRedPoint.setLayoutParams(params);
                Log.i(tag, "leftMargin" + leftMargin);
            }

            @Override
            public void onPageSelected(int position) {
                // 某个页面被选中
                if (position == mImageViewList.size() - 1) {// 最后一个页面显示开始体验的按钮
                    mBtnStart.setVisibility(View.VISIBLE);
                } else {
                    mBtnStart.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // 页面状态发生变化的回调
                Log.i(tag, "state:" + state);
            }
        });

        mRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                mRedPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mPointDis = mLlContainer.getChildAt(1).getLeft()
                        - mLlContainer.getChildAt(0).getLeft();
                Log.i(tag, "圆点距离:" + mPointDis);
            }
        });

        initData();

        mViewPager.setAdapter(new GuideAdapter());

        mBtnStart.setVisibility(View.INVISIBLE);
    }

    private void initData() {
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);//通过设置背景,可以让宽高填充布局
            mImageViewList.add(view);

            //初始化小圆点
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.shape_point_gray);

            //初始化布局参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            if (i > 0) {
                params.leftMargin = 20;
            }
            point.setLayoutParams(params);
            mLlContainer.addView(point);
        }
    }

    @OnClick(R.id.btn_start)
    public void onClick() {
        //更新sp, 已经不是第一次进入了
        SpUtil.putBoolean(getApplicationContext(), Constants.IS_FIRST_ENTER, false);

        //跳到主页面
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        // item的个数
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //Log.i(tag, "isViewFromObject:" + (view == object));
            return view == object;
        }

        // 初始化item布局
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = mImageViewList.get(position);
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
