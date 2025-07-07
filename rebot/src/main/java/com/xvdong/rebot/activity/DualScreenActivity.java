package com.xvdong.rebot.activity;

import android.app.Presentation;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.xvdong.rebot.R;
import com.xvdong.rebot.common.Constants;
import com.xvdong.rebot.util.MyPresentation;
import com.xvdong.rebot.util.MyPresentation2;

public class DualScreenActivity extends AppCompatActivity {


    private DisplayManager mDisplayManager;
    private Presentation mPresentation;
    private Display secondDisplay;


    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, DualScreenActivity.class);
        starter.putExtra(Constants.BUNDLE,bundle);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual_screen);

        View viewById = findViewById(R.id.presentation_text);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPresentation2 myPresentation2 = new MyPresentation2(DualScreenActivity.this,secondDisplay);
                myPresentation2.show();
                viewById.postDelayed(() -> {
                    mPresentation.dismiss();
                    mPresentation = null;
                }, 1000);
                System.gc();
            }
        });

        mDisplayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = mDisplayManager.getDisplays();
        // 查找辅助屏幕
        for (Display display : displays) {
            LogUtils.d(display.getName());
            if (display.getDisplayId() != Display.DEFAULT_DISPLAY) {
                // 找到了一个辅助屏幕，可以在这里创建并显示 Presentation
                secondDisplay = display;
                showPresentation(display);
                break; // 通常我们只使用第一个找到的辅助屏幕
            }
        }
    }

    private void showPresentation(Display display) {
        if (mPresentation == null) {
            mPresentation = new MyPresentation(this, display);
            // 设置 Presentation 的监听器，以便在显示或隐藏时做一些操作
//            mPresentation.setOnDismissListener(dialog -> {
//                mPresentation = null;
//            });
        }
        mPresentation.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresentation != null) {
            mPresentation.dismiss(); // 在 Activity 销毁时关闭 Presentation
        }
    }
}