package com.example.qihang.draw;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qihang.R;
import com.example.qihang.draw.view.QRCodeGenerator;

public class DrawPart21Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_part21);
        ImageView imageView = findViewById(R.id.iv1);
        imageView.setImageBitmap(QRCodeGenerator.generateRedQRCode(400));

        findViewById(R.id.btn1).setOnClickListener(v -> {
            imageView.setImageBitmap(QRCodeGenerator.generateGreenQRCode(400));
        });
    }
}