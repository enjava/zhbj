package com.tenray.zhbj.fragment;

import android.util.Log;
import android.view.View;

import com.tenray.zhbj.R;

/**
 * Created by en on 2016/12/22.
 */

public class ContentFragment extends BaseFragment {
    private final String tag = getClass().getSimpleName();
    @Override
    public View initView() {
        View inflate = View.inflate(mActivity, R.layout.fragment_content, null);
        return inflate;
    }

    @Override
    protected void initData() {
        Log.i(tag,"initData");
    }
}
