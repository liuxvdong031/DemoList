package com.xvdong.demolist.business.coordinator;

import android.os.Bundle;
import android.view.View;

import com.xvdong.demolist.R;
import com.xvdong.demolist.core.base.BaseActivity;
import com.xvdong.demolist.core.base.ContainerActivity;

public class CoordinatorActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        findViewById(R.id.btn1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                ContainerActivity.startContainerActivity(
                        this,
                        null,
                        CoordinatorLayoutDemoFragment.class.getCanonicalName());
                break;
            default:
                break;
        }
    }
}