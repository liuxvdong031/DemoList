package com.example.qihang.animation;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.example.qihang.R;

import androidx.appcompat.app.AppCompatActivity;

public class AnimationPart8Activity extends AppCompatActivity {

    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_part8);
        mImageView = findViewById(R.id.iv1);

        findViewById(R.id.btn1).setOnClickListener(v -> {
            PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("Rotation", 60f, -60f, 40f, -40f, -20f, 20f, 10f, -10f, 0f);
            PropertyValuesHolder colorHolder = PropertyValuesHolder.ofInt("BackgroundColor", 0xffffffff, 0xffff00ff, 0xffffff00, 0xffffffff);
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mImageView, rotationHolder, colorHolder);
            animator.setDuration(3000);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.start();
        });

        findViewById(R.id.btn2).setOnClickListener(v -> {
            /**
             * 左右震动效果
             */
            Keyframe frame0 = Keyframe.ofFloat(0f, 0);
            Keyframe frame1 = Keyframe.ofFloat(0.1f, -20f);
            Keyframe frame2 = Keyframe.ofFloat(0.2f, 20f);
            Keyframe frame3 = Keyframe.ofFloat(0.3f, -20f);
            Keyframe frame4 = Keyframe.ofFloat(0.4f, 20f);
            Keyframe frame5 = Keyframe.ofFloat(0.5f, -20f);
            Keyframe frame6 = Keyframe.ofFloat(0.6f, 20f);
            Keyframe frame7 = Keyframe.ofFloat(0.7f, -20f);
            Keyframe frame8 = Keyframe.ofFloat(0.8f, 20f);
            Keyframe frame9 = Keyframe.ofFloat(0.9f, -20f);
            Keyframe frame10 = Keyframe.ofFloat(1, 0);
            PropertyValuesHolder frameHolder1 = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2, frame3, frame4,frame5, frame6, frame7, frame8, frame9, frame10);


            /**
             * scaleX放大1.1倍
             */
            Keyframe scaleXframe0 = Keyframe.ofFloat(0f, 1);
            Keyframe scaleXframe1 = Keyframe.ofFloat(0.1f, 1.1f);
            Keyframe scaleXframe2 = Keyframe.ofFloat(0.2f, 1.1f);
            Keyframe scaleXframe3 = Keyframe.ofFloat(0.3f, 1.1f);
            Keyframe scaleXframe4 = Keyframe.ofFloat(0.4f, 1.1f);
            Keyframe scaleXframe5 = Keyframe.ofFloat(0.5f, 1.1f);
            Keyframe scaleXframe6 = Keyframe.ofFloat(0.6f, 1.1f);
            Keyframe scaleXframe7 = Keyframe.ofFloat(0.7f, 1.1f);
            Keyframe scaleXframe8 = Keyframe.ofFloat(0.8f, 1.1f);
            Keyframe scaleXframe9 = Keyframe.ofFloat(0.9f, 1.1f);
            Keyframe scaleXframe10 = Keyframe.ofFloat(1, 1);
            PropertyValuesHolder frameHolder2 = PropertyValuesHolder.ofKeyframe("ScaleX",scaleXframe0,scaleXframe1,scaleXframe2,scaleXframe3,scaleXframe4,scaleXframe5,scaleXframe6,scaleXframe7,scaleXframe8,scaleXframe9,scaleXframe10);


            /**
             * scaleY放大1.1倍
             */
            Keyframe scaleYframe0 = Keyframe.ofFloat(0f, 1);
            Keyframe scaleYframe1 = Keyframe.ofFloat(0.1f, 1.1f);
            Keyframe scaleYframe2 = Keyframe.ofFloat(0.2f, 1.1f);
            Keyframe scaleYframe3 = Keyframe.ofFloat(0.3f, 1.1f);
            Keyframe scaleYframe4 = Keyframe.ofFloat(0.4f, 1.1f);
            Keyframe scaleYframe5 = Keyframe.ofFloat(0.5f, 1.1f);
            Keyframe scaleYframe6 = Keyframe.ofFloat(0.6f, 1.1f);
            Keyframe scaleYframe7 = Keyframe.ofFloat(0.7f, 1.1f);
            Keyframe scaleYframe8 = Keyframe.ofFloat(0.8f, 1.1f);
            Keyframe scaleYframe9 = Keyframe.ofFloat(0.9f, 1.1f);
            Keyframe scaleYframe10 = Keyframe.ofFloat(1, 1);
            PropertyValuesHolder frameHolder3 = PropertyValuesHolder.ofKeyframe("ScaleY",scaleYframe0,scaleYframe1,scaleYframe2,scaleYframe3,scaleYframe4,scaleYframe5,scaleYframe6,scaleYframe7,scaleYframe8,scaleYframe9,scaleYframe10);

            Animator animator = ObjectAnimator.ofPropertyValuesHolder(mImageView, frameHolder1,frameHolder2,frameHolder3);
            animator.setDuration(1000);
            animator.start();
        });
    }
}