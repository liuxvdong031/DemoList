package com.example.qihang.draw;

import android.os.Bundle;

import com.example.qihang.R;
import com.example.qihang.draw.view.PupilDistanceView;

import androidx.appcompat.app.AppCompatActivity;

public class DrawPart3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_part3);

        PupilDistanceView pupilDistanceView = findViewById(R.id.pdv);

        findViewById(R.id.btn1).setOnClickListener(v -> {
            pupilDistanceView.setTargetDistance(200);
        });

        findViewById(R.id.btn2).setOnClickListener(v -> {
            pupilDistanceView.setTargetDistance(50);
        });
    }
}