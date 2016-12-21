package com.tenray.zhbj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tenray.zhbj.R;
import com.tenray.zhbj.util.Constants;
import com.tenray.zhbj.util.SpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends Activity {
    private final String tag= getClass().getSimpleName();
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.activity_splash)
    RelativeLayout activitySplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       // activitySplash= (RelativeLayout) findViewById(R.id.activity_splash);
        ButterKnife.bind(this);
        //旋转动画
        RotateAnimation animRotate=new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animRotate.setDuration(1000);//动画时间
        animRotate.setFillAfter(true);//保持动画结束的状态

        //缩放动画
        ScaleAnimation animScale=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animScale.setDuration(1000);
        animScale.setFillAfter(true);//保持动画结束的状态

        //渐变动画
        AlphaAnimation animAlpha=new AlphaAnimation(0,1);
        animAlpha.setDuration(2000);
        animAlpha.setFillAfter(true);

        //动画集合
        AnimationSet animationSet=new AnimationSet(true);
        animationSet.addAnimation(animRotate);
        animationSet.addAnimation(animScale);
        animationSet.addAnimation(animAlpha);


        activitySplash.startAnimation(animationSet);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.i(tag,"动画开始onAnimationStart");
            }



            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i(tag,"动画重置onAnimationRepeat");
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Log.i(tag,"动画结束onAnimationEnd");

                // 动画结束,跳转页面
                // 如果是第一次进入, 跳新手引导
                // 否则跳主页面
                boolean isFirstEnter = SpUtil.getBoolean(SplashActivity.this, Constants.IS_FIRST_ENTER, true);
                Intent intent;
                if (isFirstEnter){
                    // 新手引导
                    intent = new Intent(getApplicationContext(),
                            GuideActivity.class);
                }
                else {
                    // 主页面
                    intent = new Intent(getApplicationContext(),
                            MainActivity.class);
                }
                startActivity(intent);
                finish();// 结束当前页面
            }
        });

    }
}
