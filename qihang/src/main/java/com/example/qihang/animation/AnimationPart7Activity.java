package com.example.qihang.animation;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.qihang.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ObjectAnimator基本使用
 */
public class AnimationPart7Activity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_part7);
        mImageView = findViewById(R.id.iv1);

        findViewById(R.id.btn1).setOnClickListener(v -> {
            ObjectAnimator redTranslationX = ObjectAnimator.ofFloat(mImageView,
                    "translationX",
                    mImageView.getTranslationX(),
                    mImageView.getTranslationX() + 20);
            redTranslationX.setDuration(100);
            redTranslationX.start();
        });

        findViewById(R.id.btn2).setOnClickListener(v -> {
            ObjectAnimator animator = ObjectAnimator.ofInt(mImageView,
                    "BackgroundColor",
                    0xffff00ff,
                    0xffffff00,
                    0xffff00ff);
            animator.setDuration(8000);
            animator.setEvaluator(new ArgbEvaluator());
            animator.start();
        });
    }


}