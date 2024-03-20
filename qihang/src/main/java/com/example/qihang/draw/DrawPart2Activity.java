package com.example.qihang.draw;

import android.content.Context;
import android.os.Bundle;

import com.example.qihang.R;
import com.example.qihang.draw.view.DrawPart2View;

import androidx.appcompat.app.AppCompatActivity;

public class DrawPart2Activity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_part2);
        mContext = this;

        DrawPart2View drawPart2View = findViewById(R.id.dp_draw);

    }

}