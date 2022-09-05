package com.xvdong.demolist.business.coordinator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xvdong.demolist.R;
import com.xvdong.demolist.core.base.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Created by xvDong on 2022/9/5.
 * 协调员布局使用Demo , 基本使用
 */

public class CoordinatorLayoutDemoFragment extends BaseFragment {

    private Toolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        View view = View.inflate(activity, R.layout.fragment_coorbinator, null);
        mToolbar = view.findViewById(R.id.tool_bar);

        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        activity.getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        return view;
    }

}
