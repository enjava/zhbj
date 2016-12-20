package com.tenray.zhbj.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tenray.zhbj.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends Activity {

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

    }
}
