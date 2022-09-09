package com.xvdong.demolist.business.coordinator.fragment;

import android.view.View;

import com.xvdong.demolist.R;
import com.xvdong.demolist.core.base.BaseFragment;

import androidx.appcompat.widget.Toolbar;

/**
 * Created by xvDong on 2022/9/5.
 * 协调员布局使用Demo , 基本使用
 */

public class CoordinatorLayoutDemoFragment3 extends BaseFragment {

    private Toolbar mToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_coorbinator3;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mToolbar = view.findViewById(R.id.tool_bar);

        mActivity.setSupportActionBar(mToolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        mActivity.getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
    }


}
