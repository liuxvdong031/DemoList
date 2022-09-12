package com.xvdong.demolist.business.recycler;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import com.xvdong.demolist.business.coordinator.adapter.SimpleRVAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xvDong on 2022/9/9.
 * 这种效果只能看, 不支持点击.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mHeaderPaint;
    private Paint mTextPaint;
    private int groupHeaderHeight = 200;
    private Rect textRect = new Rect(10, 1000, 10, 1000);

    public MyItemDecoration() {
        super();
        mHeaderPaint = new Paint();
        mHeaderPaint.setColor(Color.GREEN);
        mHeaderPaint.setStrokeWidth(3f);
        mHeaderPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLUE);
        mTextPaint.setTextSize(50f);
    }

    /**
     * 在提供给 RecyclerView 的 Canvas 中绘制任何适当的装饰。
     * 通过此方法绘制的任何内容都将在绘制项目视图之前绘制，
     * 因此将出现在视图下方。
     *
     * @param canvas       要绘制的画布
     * @param recyclerView ItemDecoration 正在绘制 的RecyclerView
     * @param state        RecyclerView 的当前状态
     */
    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        super.onDraw(canvas, recyclerView, state);
        if (recyclerView.getAdapter() instanceof SimpleRVAdapter) {
            SimpleRVAdapter adapter = (SimpleRVAdapter) recyclerView.getAdapter();
            //当前屏幕的item个数
            int childCount = recyclerView.getChildCount();
            //recyclerView的左边padding值作为绘制分割线的左
            int left = recyclerView.getPaddingLeft();
            //分割线的右边
            int right = recyclerView.getWidth() - recyclerView.getPaddingRight();
            for (int i = 0; i < childCount; i++) {
                //获取对应i的View
                View childAt = recyclerView.getChildAt(i);
                int childLayoutPosition = recyclerView.getChildLayoutPosition(childAt);
                boolean isGroupHeader = adapter.isGroupHeader(childLayoutPosition);
                //是否为头部
                if (isGroupHeader && childAt.getTop() - groupHeaderHeight - recyclerView.getPaddingTop() >= 0) {
                    canvas.drawRect(left, childAt.getTop() - groupHeaderHeight, right, childAt.getTop(), mHeaderPaint);
                    String groupName = adapter.getGroupName(childLayoutPosition);
                    Log.i("BK", groupName + " " + childAt.getTop());
                    mTextPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                    canvas.drawText(groupName, left + 20, childAt.getTop() - groupHeaderHeight / 2
                            + textRect.height() / 2, mTextPaint);
                } else if (childAt.getTop() - groupHeaderHeight - recyclerView.getPaddingTop() >= 0) {
                    //分割线
                    canvas.drawRect(left, childAt.getTop() - 1, right, childAt.getTop(), mHeaderPaint);
                }
            }
        }
    }

    /**
     * 在提供给 RecyclerView 的 Canvas 中绘制任何适当的装饰。
     * 通过此方法绘制的任何内容都将在绘制项目视图之后绘制，
     * 因此将出现在视图之上。
     *
     * @param canvas       Canvas to draw into
     * @param recyclerView RecyclerView this ItemDecoration is drawing into
     * @param state        The current state of RecyclerView.
     */
    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        super.onDrawOver(canvas, recyclerView, state);
        try {
            if (recyclerView.getAdapter() instanceof SimpleRVAdapter) {
                SimpleRVAdapter adapter = (SimpleRVAdapter) recyclerView.getAdapter();
                //屏幕可视的第一个itemView的位置
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                //获取position对应的view
                View itemView = recyclerView.findViewHolderForAdapterPosition(firstVisibleItemPosition).itemView;
                int left = recyclerView.getPaddingLeft();
                int right = recyclerView.getWidth() - recyclerView.getPaddingRight();
                int top = recyclerView.getPaddingTop();
                //当屏幕可视范围内，第二个itemView是下一组的头部的时候
                boolean isGroupHeader = adapter.isGroupHeader(firstVisibleItemPosition + 1);
                if (isGroupHeader) {//这种情况就要将上一个吸顶的慢慢往上顶的效果
                    Log.i("BK", "onDrawOver1: " + firstVisibleItemPosition);
                    //bottom会随着上滑越来越小
                    int bottom = Math.min(groupHeaderHeight, itemView.getBottom()-recyclerView.getPaddingTop());
                    canvas.drawRect(left, top, right, top + bottom, mHeaderPaint);
                    String groupName = adapter.getGroupName(firstVisibleItemPosition);
                    mTextPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                    canvas.drawText(groupName, left + 20, top + bottom - groupHeaderHeight / 2
                            + textRect.height() / 2, mTextPaint);
                } else {//固定在顶部的效果
                    Log.i("BK", "onDrawOver2: " + firstVisibleItemPosition);
                    canvas.drawRect(left, top, right, top + groupHeaderHeight, mHeaderPaint);
                    String groupName = adapter.getGroupName(firstVisibleItemPosition);
                    mTextPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                    canvas.drawText(groupName, left + 20, top + groupHeaderHeight / 2
                            + textRect.height() / 2, mTextPaint);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检索给定项目的任何偏移量。 <code>outRect<code>
     * 的每个字段指定项目视图应插入的像素数，类似于填充或边距。
     * 默认实现将 outRect 的边界设置为 0 并返回。
     *
     * <p>
     * If this ItemDecoration does not affect the positioning of item views, it should set
     * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
     * before returning.
     *
     * <p>
     * If you need to access Adapter for additional data, you can call
     * {@link RecyclerView#getChildAdapterPosition(View)} to get the adapter position of the
     * View.
     *
     * @param outRect      Rect to receive the output.
     * @param view         The child view to decorate
     * @param recyclerView RecyclerView this ItemDecoration is decorating
     * @param state        The current state of RecyclerView.
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, recyclerView, state);
        //标识绘制的偏移量,通过outRect.set(0, 0, 0, 0)来设置的。
        if (recyclerView.getAdapter() instanceof SimpleRVAdapter) {
            SimpleRVAdapter adapter = (SimpleRVAdapter) recyclerView.getAdapter();
            //RecyclerView的LayoutParams，是有viewHolder的，所以可以通过View 获取LayoutParams,再拿到ViewHolder
            //获取当前view对应的position
            int childLayoutPosition = recyclerView.getChildLayoutPosition(view);
            //判断是否是头部
            boolean isGroupHeader = adapter.isGroupHeader(childLayoutPosition);
            if (isGroupHeader) {
                //如果当前item是头部，则预留更大的空间
                outRect.set(0, groupHeaderHeight, 0, 0);
            } else {
                //不是头部隔开：1像素
                outRect.set(0, 1, 0, 0);
            }
        }
    }
}
