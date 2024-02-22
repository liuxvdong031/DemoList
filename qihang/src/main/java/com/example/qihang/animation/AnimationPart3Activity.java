package com.example.qihang.animation;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.qihang.R;

import androidx.appcompat.app.AppCompatActivity;

public class AnimationPart3Activity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_part3);
        mImageView = findViewById(R.id.iv1);

        findViewById(R.id.btn_alpha).setOnClickListener(view -> {
            mImageView.startAnimation(getAlphaAnimation());
        });
        findViewById(R.id.btn_scale).setOnClickListener(view -> {
            mImageView.startAnimation(getScaleAnimation());
        });
        findViewById(R.id.btn_trans).setOnClickListener(view -> {
            mImageView.startAnimation(getTransAnimation());
        });
        findViewById(R.id.btn_rotate).setOnClickListener(view -> {
            mImageView.startAnimation(getRotateAnimation());
        });
    }

    private Animation getAlphaAnimation() {
        AlphaAnimation alphaAnim = new AlphaAnimation(1.0f, 0.1f);
        alphaAnim.setDuration(3000);
        alphaAnim.setFillBefore(true);
        return alphaAnim;
    }

    private Animation getScaleAnimation() {
        ScaleAnimation scaleAnim = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnim.setDuration(3000);
        scaleAnim.setFillBefore(true);
        return scaleAnim;
    }

    private Animation getTransAnimation() {
        TranslateAnimation translateAnim = new TranslateAnimation(
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, -80,
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, -80);
        translateAnim.setDuration(2000);
        translateAnim.setFillBefore(true);
        return translateAnim;
    }

    private Animation getRotateAnimation() {
        RotateAnimation rotateAnim = new RotateAnimation(0, -650,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setDuration(3000);
        rotateAnim.setInterpolator(new BounceInterpolator());//插值器
        rotateAnim.setFillAfter(true);
        return rotateAnim;
    }
}