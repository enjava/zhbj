package com.tenray.zhbj.fragment;

import android.util.Log;
import android.view.View;

import com.tenray.zhbj.R;

/**
 * Created by en on 2016/12/22.
 */

public class LeftMenuFragment extends BaseFragment {
    private final String tag = getClass().getSimpleName();

    @Override
    public View initView() {
        View inflate = View.inflate(mActivity, R.layout.fragment_left_menu, null);
       // ViewUtils.inject(this, inflate);// 注入view和事件
        return inflate;
    }

    @Override
    protected void initData() {
        Log.i(tag,"initData");

    }
}
