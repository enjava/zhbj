package com.tenray.zhbj.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.tenray.zhbj.R;
import com.tenray.zhbj.activity.MainActivity;

/**
 * Created by en on 2016/12/23.
 */

public class BasePager {
    public Activity mActivity;
    public View mRootView;// 当前页面的布局对象
    public TextView tvTitle;
    public ImageButton btnMenu;
    public FrameLayout flContent;// 空的帧布局对象, 要动态添加布局

    public BasePager(Activity mActivity){
        this.mActivity=mActivity;
        mRootView= initView();
    }

    // 初始化布局
    public View initView(){
       View view= View.inflate(mActivity, R.layout.base_pager,null);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        btnMenu = (ImageButton) view.findViewById(R.id.btn_menu);
        flContent = (FrameLayout) view.findViewById(R.id.fl_content);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        return  view;
    }
    /**
     * 打开或者关闭侧边栏
     */
    protected void toggle() {
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        slidingMenu.toggle();// 如果当前状态是开, 调用后就关; 反之亦然
    }
    // 初始化数据
    public void initData() {

    }
}
