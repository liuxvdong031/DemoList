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
        User hahah = new User("hahah");

    }

    public class User{
        public User(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}