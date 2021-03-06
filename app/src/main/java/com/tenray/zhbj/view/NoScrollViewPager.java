package com.tenray.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不允许滑动的viewpager
 * 
 * @author Kevin
 * @date 2015-10-18
 */
public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollViewPager(Context context) {
		super(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// 重写此方法, 触摸时什么都不做, 从而实现对滑动事件的禁用
		return true;
	}

}
