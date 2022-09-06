package com.xvdong.demolist.business.coordinator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xvdong.demolist.R;
import com.xvdong.demolist.core.data.bean.DataBean;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xvDong on 2022/9/6.
 */

public class SimpleRVAdapter extends RecyclerView.Adapter<SimpleRVAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<DataBean> mDataBeans;

    public SimpleRVAdapter(Context context, ArrayList<DataBean> list) {
        this.context = context;
        this.mDataBeans = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_simple_rv_adapter, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataBean dataBean = mDataBeans.get(position);
        Glide.with(context).load(dataBean.url).into(holder.imageView);
        holder.textView.setText(dataBean.text);
    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
