package com.example.qihang.animation;

import android.content.Intent;
import android.os.Bundle;

import com.example.qihang.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 启航的自定义view三部曲之动画篇
 */
public class AnimationMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        //alpha、scale、translate、rotate、set的xml属性及用法
        findViewById(R.id.btn1).setOnClickListener(view -> {
            startActivity(new Intent(this,AnimationPart1Activity.class));
        });
        //Interpolator插值器
        findViewById(R.id.btn2).setOnClickListener(view -> {
            startActivity(new Intent(this,AnimationPart2Activity.class));
        });
        //代码生成alpha、scale、translate、rotate、set及插值器动画
        findViewById(R.id.btn3).setOnClickListener(view -> {
            startActivity(new Intent(this,AnimationPart3Activity.class));
        });
        //ValueAnimator基本使用
        findViewById(R.id.btn4).setOnClickListener(view -> {
            startActivity(new Intent(this,AnimationPart4Activity.class));
        });
        //ValueAnimator高级进阶（一）
        findViewById(R.id.btn5).setOnClickListener(view -> {
            startActivity(new Intent(this,AnimationPart5Activity.class));
        });
        //ValueAnimator高级进阶（二）
        findViewById(R.id.btn6).setOnClickListener(view -> {
            startActivity(new Intent(this,AnimationPart6Activity.class));
        });
        //ObjectAnimator基本使用
        findViewById(R.id.btn7).setOnClickListener(view -> {
            startActivity(new Intent(this,AnimationPart7Activity.class));
        });
        //PropertyValuesHolder与Keyframe
        findViewById(R.id.btn8).setOnClickListener(view -> {
            startActivity(new Intent(this,AnimationPart8Activity.class));
        });
        //联合动画的代码实现
        findViewById(R.id.btn9).setOnClickListener(view -> {
            startActivity(new Intent(this,AnimationPart9Activity.class));
        });
        //联合动画的XML实现与使用示例
        findViewById(R.id.btn10).setOnClickListener(view -> {
            startActivity(new Intent(this,AnimationPart10Activity.class));
        });
        //layoutAnimation与gridLayoutAnimation
        findViewById(R.id.btn11).setOnClickListener(view -> {
            startActivity(new Intent(this,AnimationPart11Activity.class));
        });
        //animateLayoutChanges与LayoutTransition
        findViewById(R.id.btn12).setOnClickListener(view -> {
            startActivity(new Intent(this,AnimationPart12Activity.class));
        });
        //实现ListView Item进入动画
        findViewById(R.id.btn13).setOnClickListener(view -> {
            startActivity(new Intent(this,AnimationPart13Activity.class));
        });
    }
}