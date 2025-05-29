package com.xvdong.rebot.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.xvdong.rebot.R;
import com.xvdong.rebot.common.Constants;

public class MirrorActivity extends AppCompatActivity {

    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, MirrorActivity.class);
        starter.putExtra(Constants.BUNDLE,bundle);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);
        //TODO 写死RelativeLayout的宽度,使其等于屏幕宽度的一半
    }
}