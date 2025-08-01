package com.xvdong.rebot.view;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.xvdong.rebot.R;

/**
 * Created by xvDong on 2025/7/23.
 */
public class ColorPickerView extends LinearLayout {
    private View colorPreview;
    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    private TextView redValue;
    private TextView greenValue;
    private TextView blueValue;

    private int red = 0;
    private int green = 0;
    private int blue = 0;

    public ColorPickerView(Context context) {
        super(context);
        init(context);
    }

    public ColorPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ColorPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(
                R.layout.color_picker_view,
                this, true
        );

        colorPreview = findViewById(R.id.color_preview);
        redSeekBar = findViewById(R.id.red_seekbar);
        greenSeekBar = findViewById(R.id.green_seekbar);
        blueSeekBar = findViewById(R.id.blue_seekbar);
        redValue = findViewById(R.id.red_value);
        greenValue = findViewById(R.id.green_value);
        blueValue = findViewById(R.id.blue_value);

        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        };

        redSeekBar.setOnSeekBarChangeListener(listener);
        greenSeekBar.setOnSeekBarChangeListener(listener);
        blueSeekBar.setOnSeekBarChangeListener(listener);

        updateColor();
    }

    private void updateColor() {
        red = redSeekBar.getProgress();
        green = greenSeekBar.getProgress();
        blue = blueSeekBar.getProgress();

        // 更新颜色预览
        colorPreview.setBackgroundColor(Color.rgb(red, green, blue));

        // 更新十六进制文本显示
        redValue.setText(String.format("%02X", red));
        greenValue.setText(String.format("%02X", green));
        blueValue.setText(String.format("%02X", blue));
    }

    /**
     * 获取当前选择的颜色
     * @return ARGB 格式的颜色值
     */
    public int getColor() {
        return Color.rgb(red, green, blue);
    }
}
