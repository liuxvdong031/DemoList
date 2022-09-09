package com.xvdong.demolist.core.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by xvDong on 2022/9/5.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initListener();
        initData();
    }

    protected abstract int getLayoutId();

    protected  void initView(){};

    protected  void initListener() {};

    protected  void initData() {};
}
