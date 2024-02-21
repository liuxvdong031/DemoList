package com.example.qihang.view;

import android.content.Intent;
import android.os.Bundle;

import com.example.qihang.R;

import androidx.appcompat.app.AppCompatActivity;
/**
 * 启航的自定义view三部曲之视图篇
 */
public class ViewMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_main);
        findViewById(R.id.btn1).setOnClickListener(v -> {
            startActivity(new Intent(this, ViewPatr1Activity.class));
        });

        findViewById(R.id.btn2).setOnClickListener(v -> {
            startActivity(new Intent(this, ViewPatr2Activity.class));
        });

        findViewById(R.id.btn2).setOnClickListener(v -> {
            startActivity(new Intent(this, ViewPatr3Activity.class));
        });
    }
}