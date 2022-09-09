package com.xvdong.demolist.business.coordinator.activity;

import android.view.View;

import com.xvdong.demolist.R;
import com.xvdong.demolist.business.coordinator.fragment.CoordinatorLayoutDemoFragment;
import com.xvdong.demolist.business.coordinator.fragment.CoordinatorLayoutDemoFragment2;
import com.xvdong.demolist.business.coordinator.fragment.CoordinatorLayoutDemoFragment3;
import com.xvdong.demolist.core.base.BaseActivity;
import com.xvdong.demolist.core.base.ContainerActivity;

public class CoordinatorActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coordinator;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn1){
            ContainerActivity.startContainerActivity(
                    this,
                    null,
                    CoordinatorLayoutDemoFragment.class.getCanonicalName());
        }else if (v.getId() == R.id.btn2){
            ContainerActivity.startContainerActivity(
                    this,
                    null,
                    CoordinatorLayoutDemoFragment2.class.getCanonicalName());
        }else if (v.getId() == R.id.btn3){
            ContainerActivity.startContainerActivity(
                    this,
                    null,
                    CoordinatorLayoutDemoFragment3.class.getCanonicalName());
        }
    }
}