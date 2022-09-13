package com.xvdong.demolist.business.coordinator.fragment;

import android.view.View;

import com.xvdong.demolist.R;
import com.xvdong.demolist.business.coordinator.adapter.SimpleRVAdapter;
import com.xvdong.demolist.core.base.BaseFragment;
import com.xvdong.demolist.core.data.bean.DataBean;
import com.xvdong.demolist.core.util.GlideEngine;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xvDong on 2022/9/6.
 */

public class TabFragment extends BaseFragment {

    private RecyclerView mRecyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public void initData() {
        super.initData();
        ArrayList<DataBean> dataBeans = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
        for (int i = 0; i < 20; i++) {
            DataBean dataBean = new DataBean("我是" + i, GlideEngine.DEFAULT_IMAGE_URL);
            dataBeans.add(dataBean);
        }
        SimpleRVAdapter simpleRVAdapter = new SimpleRVAdapter(mActivity, dataBeans);
        mRecyclerView.setAdapter(simpleRVAdapter);
    }
}
