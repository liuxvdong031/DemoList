package com.xvdong.demolist.core.ui;

import android.graphics.Color;

import com.xvdong.demolist.R;
import com.xvdong.demolist.business.coordinator.adapter.SimpleRVAdapter;
import com.xvdong.demolist.core.base.BaseActivity;
import com.xvdong.demolist.core.data.bean.DataBean;
import com.xvdong.demolist.core.util.GlideEngine;

import java.util.ArrayList;
import java.util.Collections;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TouchHelperActivity extends BaseActivity {


    private ArrayList<DataBean> mDataBeans;
    private SimpleRVAdapter mSimpleRVAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_touch_helper;
    }

    @Override
    protected void initView() {
        super.initView();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mDataBeans = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        for (int i = 0; i < 20; i++) {
            DataBean dataBean = new DataBean("我是" + i, GlideEngine.DEFAULT_IMAGE_URL);
            mDataBeans.add(dataBean);
        }
        mSimpleRVAdapter = new SimpleRVAdapter(this, mDataBeans);
        recyclerView.setAdapter(mSimpleRVAdapter);
        helper.attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        //线性布局和网格布局都可以使用
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            if (adapterPosition > 3){
                int dragFrlg = 0;
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    dragFrlg = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int orientation = layoutManager.getOrientation();
                    if (orientation == LinearLayoutManager.HORIZONTAL) {
                        dragFrlg = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    } else {
                        dragFrlg = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    }
                }
                return makeMovementFlags(dragFrlg, 0);
            }else {
                return 0;
            }

        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            //得到当拖拽的viewHolder的Position  每一次Position改变，该方法都回调
            int fromPosition = viewHolder.getAdapterPosition();
            //拿到当前拖拽到的item的viewHolder
            int toPosition = target.getAdapterPosition();
            if (toPosition <= 3) return false;
            if (fromPosition < toPosition) {
                if (toPosition < mDataBeans.size() - 1) {//此处表明最后一个item不可替换，一般最后一个item是添加更多图片+
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mDataBeans, i, i + 1);
                    }
                    mSimpleRVAdapter.notifyItemMoved(fromPosition, toPosition);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mDataBeans, i, i - 1);
                }
                mSimpleRVAdapter.notifyItemMoved(fromPosition, toPosition);
            }
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //侧滑删除可以使用；
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        /**
         * 长按选中Item的时候开始调用
         * 长按高亮
         * @param viewHolder
         * @param actionState
         */
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setBackgroundColor(Color.RED);
                viewHolder.itemView.setScaleX(1.2f);
                viewHolder.itemView.setScaleY(1.2f);
                //获取系统震动服务//震动70毫秒
//                Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
//                vib.vibrate(70);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        /**
         * 手指松开的时候还原高亮
         * @param recyclerView
         * @param viewHolder
         */
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            viewHolder.itemView.setScaleX(1.0f);
            viewHolder.itemView.setScaleY(1.0f);
            mSimpleRVAdapter.notifyDataSetChanged();  //完成拖动后刷新适配器，这样拖动后删除就不会错乱
        }
    });
}