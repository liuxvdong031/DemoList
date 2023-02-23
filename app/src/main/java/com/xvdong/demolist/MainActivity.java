package com.xvdong.demolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xvdong.demolist.business.coordinator.activity.CoordinatorActivity;
import com.xvdong.demolist.business.jetpack.JetpackActivity;
import com.xvdong.demolist.business.recycler.RecyclerViewDemoActivity;
import com.xvdong.demolist.business.custom.activity.CustomActivity;
import com.xvdong.demolist.core.ui.TouchHelperActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toCoordinator(View view) {
        startActivity(new Intent(this, CoordinatorActivity.class));
    }

    public void toRecycler(View view) {
        startActivity(new Intent(this, RecyclerViewDemoActivity.class));
    }

    public void toCustom(View view) {
        startActivity(new Intent(this, CustomActivity.class));
    }

    public void toJetpack(View view) {
        startActivity(new Intent(this, JetpackActivity.class));
    }

    public void toCamera(View view){
        startActivity(new Intent(this, CameraActivity.class));
    }
    public void toDrag(View view){
        startActivity(new Intent(this, TouchHelperActivity.class));
    }
}