package com.example.qihang;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qihang.animation.AnimationMainActivity;
import com.example.qihang.draw.DrawMainActivity;
import com.example.qihang.view.ViewMainActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.iv_fusion);
        mImageView.setImageBitmap(null);
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

        //融像图片
        findViewById(R.id.btn4).setOnClickListener(view -> {
            isRed = !isRed;
            Bitmap redGreenImage = RedGreen3DImage.create3DImage(100, 100, 50);
            mImageView.setImageBitmap(redGreenImage);
        });



    }


    private boolean isRed = true;
}