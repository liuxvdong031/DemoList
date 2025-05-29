package com.xvdong.rebot.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.xvdong.rebot.R;
import com.xvdong.rebot.common.Constants;

public class VideoMirrorActivity extends AppCompatActivity {

    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, VideoMirrorActivity.class);
        starter.putExtra(Constants.BUNDLE,bundle);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_mirror);

    }
}