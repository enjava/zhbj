package com.tenray.zhbj.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.tenray.zhbj.R;
import com.tenray.zhbj.fragment.ContentFragment;
import com.tenray.zhbj.fragment.LeftMenuFragment;

public class MainActivity extends SlidingFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.left_menu);
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 全屏触摸
        slidingMenu.setBehindOffset(600);// 屏幕预留200像素宽度
        initFragment();
    }

    private void initFragment(){
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();//开启事务
        mFragmentTransaction.replace(R.id.fl_left_menu, new LeftMenuFragment(),"left_menu");;// 用fragment替换帧布局;参1:帧布局容器的id;参2:是要替换的fragment;参3:标记
        mFragmentTransaction.replace(R.id.fl_main, new ContentFragment(),"main");
        mFragmentTransaction.commit();
        // Fragment fragment =
        // fm.findFragmentByTag(TAG_LEFT_MENU);//根据标记找到对应的fragment
    }
}
