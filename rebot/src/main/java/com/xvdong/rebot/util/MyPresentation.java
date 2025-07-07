package com.xvdong.rebot.util;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.widget.TextView;

import com.xvdong.rebot.R;

/**
 * Created by xvDong on 2025/7/3.
 */

public class MyPresentation extends Presentation {

    public MyPresentation(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置 Presentation 的布局
        setContentView(R.layout.presentation_layout);

        // 在这里可以获取并操作 presentation_layout 中的视图
        TextView textView = findViewById(R.id.presentation_text);
        textView.setText("Hello from the second screen!");
        // ... 其他视图操作
    }
}