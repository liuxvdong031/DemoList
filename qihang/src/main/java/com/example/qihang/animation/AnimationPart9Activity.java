package com.example.qihang.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.qihang.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * AnimatorSet
 * playSequentially 所有动画依次播放
 * playTogether 所有动画一起开始
 *
 * AnimatorSet.Builder 自由设置动画顺序
 */
public class AnimationPart9Activity extends AppCompatActivity {

    private ImageView mImageView,mImageView2,mImageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_part9);

        mImageView = findViewById(R.id.iv1);
        mImageView2 = findViewById(R.id.iv2);
        mImageView3 = findViewById(R.id.iv3);

        findViewById(R.id.btn1).setOnClickListener(v -> {
            doPlaySequentiallyAnimator();
        });

        findViewById(R.id.btn2).setOnClickListener(v -> {
            doPlayTogetherAnimator();
        });

        findViewById(R.id.btn3).setOnClickListener(v -> {
            infiniteLoop();
        });

        findViewById(R.id.btn4).setOnClickListener(v -> {
            doAnimatorSetBuilder();
        });
    }


    private void doAnimatorSetBuilder(){
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(mImageView2, "translationX", mImageView2.getTranslationX(), 400, 0);
        tv1TranslateY.setRepeatCount(10);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(mImageView3, "translationX", mImageView3.getTranslationX(), -400, 0);
        tv2TranslateY.setRepeatCount(10);
        tv2TranslateY.setStartDelay(2000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(tv1TranslateY).with(tv2TranslateY);
        animatorSet.setStartDelay(2000);
        animatorSet.setDuration(2000);
        animatorSet.start();

    }

    private void infiniteLoop(){
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(mImageView, "BackgroundColor",  0xffff00ff, 0xffffff00, 0xffff00ff);
        tv1BgAnimator.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(mImageView, "translationY", 0, 400, 0);
        tv1TranslateY.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(mImageView, "translationX", 0, 400, 0);
        tv2TranslateY.setRepeatCount(ValueAnimator.INFINITE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(tv1BgAnimator,tv1TranslateY,tv2TranslateY);
        animatorSet.setDuration(2000);
        animatorSet.start();

    }

    private void doPlayTogetherAnimator(){
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(mImageView, "BackgroundColor",  0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(mImageView, "translationY", 0, 300, 0);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(mImageView, "translationX", 0, 400, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(tv1BgAnimator,tv1TranslateY,tv2TranslateY);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }


    private void doPlaySequentiallyAnimator(){
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(mImageView, "BackgroundColor",  0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(mImageView, "translationY", 0, 300, 0);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(mImageView, "translationX", 0, 400, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(tv1BgAnimator,tv1TranslateY,tv2TranslateY);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }
}