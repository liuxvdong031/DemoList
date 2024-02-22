package com.example.qihang.animation;

import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.example.qihang.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 动画的插值器,不同的插值器哟不同的效果
 *
 * AccelerateDecelerateInterpolator     在动画开始与结束的地方速率改变比较慢，在中间的时候加速
 * AccelerateInterpolator               在动画开始的地方速率改变比较慢，然后开始加速
 * AnticipateInterpolator               开始的时候向后然后向前甩
 * AnticipateOvershootInterpolator      开始的时候向后然后向前甩一定值后返回最后的值
 * BounceInterpolator                   动画结束的时候弹起
 * CycleInterpolator                    动画循环播放特定的次数，速率改变沿着正弦曲线
 * DecelerateInterpolator               在动画开始的地方快然后慢
 * MyLinearInterpolator                   以常量速率改变
 * OvershootInterpolator                向前甩一定值后再回到原来位置
 */

public class AnimationPart2Activity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_part2);
        mImageView = findViewById(R.id.iv1);

        //AccelerateDecelerateInterpolator      在动画开始与结束的地方速率改变比较慢，在中间的时候加速
        findViewById(R.id.btn1).setOnClickListener(view -> {
            startAnimation(new AccelerateDecelerateInterpolator());
        });
        //AccelerateInterpolator                在动画开始的地方速率改变比较慢，然后开始加速
        findViewById(R.id.btn2).setOnClickListener(view -> {
            startAnimation(new AccelerateInterpolator());
        });
        //AnticipateInterpolator                开始的时候向后然后向前甩
        findViewById(R.id.btn3).setOnClickListener(view -> {
            startAnimation(new AnticipateInterpolator());
        });
        //AnticipateOvershootInterpolator      开始的时候向后然后向前甩一定值后返回最后的值
        findViewById(R.id.btn4).setOnClickListener(view -> {
            startAnimation(new AnticipateOvershootInterpolator());
        });
        //BounceInterpolator                   动画结束的时候弹起
        findViewById(R.id.btn5).setOnClickListener(view -> {
            startAnimation(new BounceInterpolator());
        });
        //CycleInterpolator                    动画循环播放特定的次数，速率改变沿着正弦曲线
        findViewById(R.id.btn6).setOnClickListener(view -> {
            startAnimation(new CycleInterpolator(2));
        });
        //DecelerateInterpolator               在动画开始的地方快然后慢
        findViewById(R.id.btn7).setOnClickListener(view -> {
            startAnimation(new DecelerateInterpolator());
        });
        //MyLinearInterpolator                   以常量速率改变
        findViewById(R.id.btn8).setOnClickListener(view -> {
            startAnimation(new LinearInterpolator());
        });
        //OvershootInterpolator                向前甩一定值后再回到原来位置
        findViewById(R.id.btn9).setOnClickListener(view -> {
            startAnimation(new OvershootInterpolator());
        });
    }

    private void startAnimation(Interpolator interpolator){
        // 加载动画资源文件
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
        // 将插值器应用到动画
        animation.setInterpolator(interpolator);
        // 开始动画
        mImageView.startAnimation(animation);
    }
}