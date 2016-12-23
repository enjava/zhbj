package com.tenray.zhbj.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.tenray.zhbj.R;
import com.tenray.zhbj.activity.MainActivity;
import com.tenray.zhbj.base.BasePager;
import com.tenray.zhbj.base.impl.GovAffairsPager;
import com.tenray.zhbj.base.impl.HomePager;
import com.tenray.zhbj.base.impl.NewsCenterPager;
import com.tenray.zhbj.base.impl.SettingPager;
import com.tenray.zhbj.base.impl.SmartServicePager;
import com.tenray.zhbj.view.NoScrollViewPager;

import java.util.ArrayList;

/**
 * Created by en on 2016/12/22.
 */

public class ContentFragment extends BaseFragment {
    private final String tag = getClass().getSimpleName();

    private NoScrollViewPager mViewPager;
    private RadioGroup rgGroup;

    private ArrayList<BasePager> mPagers;// 五个标签页的集合
    @Override
    public View initView() {
        View inflate = View.inflate(mActivity, R.layout.fragment_content, null);
        mViewPager = (NoScrollViewPager) inflate.findViewById(R.id.vp_content);
        rgGroup = (RadioGroup) inflate.findViewById(R.id.rg_group);
        return inflate;
    }

    @Override
    protected void initData() {
        mPagers = new ArrayList<>();

        // 添加五个标签页
        mPagers.add(new HomePager(mActivity));
        mPagers.add(new NewsCenterPager(mActivity));
        mPagers.add(new SmartServicePager(mActivity));
        mPagers.add(new GovAffairsPager(mActivity));
        mPagers.add(new SettingPager(mActivity));


        //region RadioGroup设置点击事件
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        mViewPager.setCurrentItem(0,false);
                        break;
                    case R.id.rb_news:
                        mViewPager.setCurrentItem(1,false);
                        break;
                    case R.id.rb_smart:
                        mViewPager.setCurrentItem(2,false);
                        break;
                    case R.id.rb_gov:
                        mViewPager.setCurrentItem(3,false);
                        break;
                    case R.id.rb_setting:
                        mViewPager.setCurrentItem(4,false);
                        break;
                    default:
                        break;
                }
            }
        });
        //endregion

        mViewPager.setAdapter(new ContentAdapter());

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BasePager pager = mPagers.get(position);
                pager.initData();

                if (position == 0 || position == mPagers.size() - 1) {
                    // 首页和设置页要禁用侧边栏
                    setSlidingMenuEnable(false);
                } else {
                    // 其他页面开启侧边栏
                    setSlidingMenuEnable(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 手动加载第一页数据
        mPagers.get(0).initData();
        //初始时首页关闭侧边栏
        setSlidingMenuEnable(false);
        Log.i(tag,"initData");
    }

    /**
     * 设置侧边栏是否可以打开
     * @param enable
     */
    private void setSlidingMenuEnable(boolean enable) {
        // 获取侧边栏对象
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    /**
     * ViewPager 适配器
     */
    class ContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mPagers.get(position);
            View view = pager.mRootView;// 获取当前页面对象的布局

           // pager.initData();// 初始化数据, viewpager会默认加载下一个页面,
            // 为了节省流量和性能,不要在此处调用初始化数据的方法

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    // 获取新闻中心页面
    public NewsCenterPager getNewsCenterPager() {
        NewsCenterPager pager = (NewsCenterPager)mPagers.get(1);
        return pager;
    }

}
