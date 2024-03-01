package com.example.qihang.animation;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.qihang.R;

import androidx.appcompat.app.AppCompatActivity;

public class AnimationPart12Activity extends AppCompatActivity {


    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_part12);

        mImageView = findViewById(R.id.iv1);

        findViewById(R.id.btn1).setOnClickListener(v -> {

        });

        findViewById(R.id.btn2).setOnClickListener(v -> {

        });
    }
}