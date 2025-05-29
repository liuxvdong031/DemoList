package com.xvdong.rebot;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.xvdong.rebot.activity.MirrorActivity;
import com.xvdong.rebot.activity.PrintActivity;
import com.xvdong.rebot.activity.TwoCameraActivity;
import com.xvdong.rebot.activity.VideoMirrorActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_print).setOnClickListener(v -> {
            PrintActivity.start(this,null);
        });

        findViewById(R.id.btn_camera).setOnClickListener(v -> {
            TwoCameraActivity.start(this,null);
        });

        findViewById(R.id.btn_mirror).setOnClickListener(v -> {
            MirrorActivity.start(this,null);
        });

        findViewById(R.id.btn_video).setOnClickListener(v -> {
            VideoMirrorActivity.start(this,null);
        });

    }
}