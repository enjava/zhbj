package com.tenray.zhbj.base.impl;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tenray.zhbj.activity.MainActivity;
import com.tenray.zhbj.base.BaseMenuDetailPager;
import com.tenray.zhbj.base.BasePager;
import com.tenray.zhbj.base.impl.menu.InteractMenuDetailPager;
import com.tenray.zhbj.base.impl.menu.NewsMenuDetailPager;
import com.tenray.zhbj.base.impl.menu.PhotosMenuDetailPager;
import com.tenray.zhbj.base.impl.menu.TopicMenuDetailPager;
import com.tenray.zhbj.entity.NewsMenu;
import com.tenray.zhbj.fragment.LeftMenuFragment;
import com.tenray.zhbj.global.GlobalConstants;
import com.tenray.zhbj.util.CacheUtils;

import java.util.ArrayList;

/**
 * 新闻中心
 *
 * @author Kevin
 * @date 2015-10-18
 */
public class NewsCenterPager extends BasePager {
    private final String tag = getClass().getSimpleName();

   private ArrayList<BaseMenuDetailPager> mMenuDetailPagers;// 菜单详情页集合

    private  NewsMenu mNewsData;

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        System.out.println("新闻中心初始化啦...");

        // 要给帧布局填充布局对象
//        TextView view = new TextView(mActivity);
//        view.setText("新闻中心");
//        view.setTextColor(Color.RED);
//        view.setTextSize(22);
//        view.setGravity(Gravity.CENTER);
//
//        flContent.addView(view);

        // 修改页面标题
        tvTitle.setText("新闻");

        // 显示菜单按钮
        btnMenu.setVisibility(View.VISIBLE);

// 先判断有没有缓存,如果有的话,就加载缓存
        String cache = CacheUtils.getCache(GlobalConstants.CATEGORY_URL,
                mActivity);
        if (!TextUtils.isEmpty(cache)) {
            Log.i(tag,"发现缓存啦...");
            processData(cache);
        }else
        // 请求服务器,获取数据
        // 开源框架: XUtils
        getDataFromServer();
    }

    /**
     * 从服务器获取数据 需要权限:<uses-permission android:name="android.permission.INTERNET"
     * />
     */
    private void getDataFromServer() {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, GlobalConstants.CATEGORY_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //请求成功
                Log.i(tag, responseInfo.result);
                processData(responseInfo.result);

                // 写缓存
                CacheUtils.setCache(GlobalConstants.CATEGORY_URL,
                        responseInfo.result, mActivity);
            }

            @Override
            public void onFailure(HttpException e, String msg) {
                //请求失败
                Log.i(tag, msg);
                Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT)
                        .show();
            }


        });
    }

    //解析数据
    protected void processData(String json) {
        // Gson: Google Json
        Gson gson = new Gson();
        mNewsData = gson.fromJson(json, NewsMenu.class);
        Log.i(tag, "解析结果:" + mNewsData);

        // 获取侧边栏对象
        MainActivity mainUI = (MainActivity) mActivity;
        LeftMenuFragment fragment = mainUI.getLeftMenuFragment();

        // 给侧边栏设置数据
        fragment.setMenuData(mNewsData.data);

        // 初始化4个菜单详情页
        mMenuDetailPagers = new ArrayList<>();
        mMenuDetailPagers.add(new NewsMenuDetailPager(mActivity));
        mMenuDetailPagers.add(new TopicMenuDetailPager(mActivity));
        mMenuDetailPagers.add(new PhotosMenuDetailPager(mActivity));
        mMenuDetailPagers.add(new InteractMenuDetailPager(mActivity));

        // 将新闻菜单详情页设置为默认页面
         setCurrentDetailPager(0);
    }

    // 设置菜单详情页
    public void setCurrentDetailPager(int position) {
        // 重新给frameLayout添加内容
       BaseMenuDetailPager pager = mMenuDetailPagers.get(position);// 获取当前应该显示的页面
       View view = pager.mRootView;// 当前页面的布局

        // 清除之前旧的布局
        flContent.removeAllViews();

       flContent.addView(view);// 给帧布局添加布局

        // 初始化页面数据
        pager.initData();

        // 更新标题
        tvTitle.setText(mNewsData.data.get(position).title);
    }

}
