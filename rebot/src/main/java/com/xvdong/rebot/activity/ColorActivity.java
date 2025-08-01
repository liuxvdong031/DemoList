package com.xvdong.rebot.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xvdong.rebot.R;
import com.xvdong.rebot.common.Constants;

/**
 * Created by xvDong on 2025/7/23.
 */

public class ColorActivity extends AppCompatActivity {

    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, ColorActivity.class);
        starter.putExtra(Constants.BUNDLE,bundle);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
    }
}
