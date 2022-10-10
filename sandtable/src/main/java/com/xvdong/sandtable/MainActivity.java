package com.xvdong.sandtable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toImageDot(View v) {
        startActivity(new Intent(this, ImageMarkActivity.class));
    }
    public void toImageEdit(View v) {
        startActivity(new Intent(this, ImageEditActivity.class));
    }

}