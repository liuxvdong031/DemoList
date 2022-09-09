package com.xvdong.demolist.business.recycler;

import com.xvdong.demolist.R;
import com.xvdong.demolist.business.coordinator.adapter.SimpleRVAdapter;
import com.xvdong.demolist.core.base.BaseActivity;
import com.xvdong.demolist.core.data.bean.DataBean;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewDemoActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycler_view_demo;
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    protected void initData() {
        super.initData();
        ArrayList<DataBean> dataBeans = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        for (int i = 0; i < 20; i++) {
            DataBean dataBean = new DataBean("我是" + i, "https://pic.qqtn.com/up/2019-9/15690311636958128.jpg");
            dataBeans.add(dataBean);
        }
        SimpleRVAdapter simpleRVAdapter = new SimpleRVAdapter(this, dataBeans);
        mRecyclerView.setAdapter(simpleRVAdapter);
        MyItemDecoration myItemDecoration = new MyItemDecoration();
        mRecyclerView.addItemDecoration(myItemDecoration);
    }
}