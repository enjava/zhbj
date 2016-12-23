package com.tenray.zhbj.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.tenray.zhbj.R;
import com.tenray.zhbj.base.BasePager;
import com.tenray.zhbj.base.impl.GovAffairsPager;
import com.tenray.zhbj.base.impl.HomePager;
import com.tenray.zhbj.base.impl.NewsCenterPager;
import com.tenray.zhbj.base.impl.SettingPager;
import com.tenray.zhbj.base.impl.SmartServicePager;

import java.util.ArrayList;

/**
 * Created by en on 2016/12/22.
 */

public class ContentFragment extends BaseFragment {
    private final String tag = getClass().getSimpleName();

    private ViewPager mViewPager;
    private RadioGroup rgGroup;

    private ArrayList<BasePager> mPagers;// 五个标签页的集合
    @Override
    public View initView() {
        View inflate = View.inflate(mActivity, R.layout.fragment_content, null);
        mViewPager = (ViewPager) inflate.findViewById(R.id.vp_content);
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


        mViewPager.setAdapter(new ContentAdapter());
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BasePager pager = mPagers.get(position);
                pager.initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Log.i(tag,"initData");
    }

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
}
