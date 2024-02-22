package com.example.qihang.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.qihang.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ValueAnimator
 */
public class AnimationPart4Activity extends AppCompatActivity {

    private ImageView mImageView;
    private final String TAG = "AnimationPart4Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_part4);

        mImageView = findViewById(R.id.iv1);

        findViewById(R.id.btn1).setOnClickListener(v -> {
            startValueAnimator();
        });

    }

    private void startValueAnimator() {
//        ValueAnimator animator = ValueAnimator.ofFloat(0, 400);
        ValueAnimator animator = ValueAnimator.ofInt(0, 400,400,800);
        animator.setDuration(10000);
        animator.setStartDelay(1000);//延迟多久再开始动画
        animator.setRepeatCount(100);//动画从重复次数
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.addUpdateListener(animation -> {
            int curValue = (int) animation.getAnimatedValue();
            Log.d(TAG, "curValue:" + curValue);
            mImageView.layout(curValue, curValue, curValue + mImageView.getWidth(), curValue + mImageView.getHeight());
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
            }
        });
        animator.start();

    }
}