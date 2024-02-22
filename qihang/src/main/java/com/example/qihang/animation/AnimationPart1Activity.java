package com.example.qihang.animation;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.qihang.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * XML动画  平移/旋转/放大缩小/透明度
 *
 * android:duration        动画持续时间，以毫秒为单位
 * android:fillAfter       如果设置为true，控件动画结束时，将保持动画最后时的状态
 * android:fillBefore      如果设置为true,控件动画结束时，还原到开始动画前的状态
 * android:fillEnabled     与android:fillBefore 效果相同，都是在动画结束时，将控件还原到初始化状态
 * android:repeatCount      重复次数
 * android:repeatMode       重复类型，有reverse和restart两个值，
 *      reverse表示倒序回放，
 *      restart表示重新放一遍，
 *      必须与repeatCount一起使用才能看到效果。因为这里的意义是重复的类型，即回放时的动作。
 */
public class AnimationPart1Activity extends AppCompatActivity {

    private TextView mTextView;

    private Animation scaleAnimation ,alphaAnimation ,transAnimation,rotateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amimation_part1);
        mTextView = findViewById(R.id.tv1);

        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);
        transAnimation = AnimationUtils.loadAnimation(this, R.anim.translate);
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);

        findViewById(R.id.btn_alpha).setOnClickListener(view -> {
            mTextView.startAnimation(alphaAnimation);
        });
        findViewById(R.id.btn_scale).setOnClickListener(view -> {
            mTextView.startAnimation(scaleAnimation);
        });
        findViewById(R.id.btn_trans).setOnClickListener(view -> {
            mTextView.startAnimation(transAnimation);
        });
        findViewById(R.id.btn_rotate).setOnClickListener(view -> {
            mTextView.startAnimation(rotateAnimation);
        });

    }
}