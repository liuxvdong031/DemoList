package com.xvdong.demolist.business.custom.activity;

import android.os.Bundle;

import com.xvdong.demolist.R;

import androidx.appcompat.app.AppCompatActivity;
import io.supercharge.shimmerlayout.ShimmerLayout;

public class CustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        ShimmerLayout shimmerText = findViewById(R.id.shimmer_text);
        shimmerText.startShimmerAnimation();
    }
}