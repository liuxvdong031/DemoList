package com.example.qihang;

import android.content.Intent;
import android.os.Bundle;

import com.example.qihang.animation.AnimationMainActivity;
import com.example.qihang.draw.DrawMainActivity;
import com.example.qihang.view.ViewMainActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //动画篇
        findViewById(R.id.btn1).setOnClickListener(view -> {
            startActivity(new Intent(this, AnimationMainActivity.class));
        });

        //绘制篇
        findViewById(R.id.btn2).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawMainActivity.class));
        });

        //视图篇
        findViewById(R.id.btn3).setOnClickListener(view -> {
            startActivity(new Intent(this, ViewMainActivity.class));
        });

    }
}