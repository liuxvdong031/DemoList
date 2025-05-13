package com.xvdong.demolist;

/**
 * Created by xvDong on 2025/5/8.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiTouchDrawView extends View {
    // 所有已完成的路径（按pointerId分组）
    private List<SparseArray<Path>> allPaths = new ArrayList<>();
    // 当前正在绘制的路径（按pointerId分组）
    private SparseArray<Path> activePaths = new SparseArray<>();
    // 路径颜色（按pointerId区分）
    private SparseArray<Integer> pathColors = new SparseArray<>();
    // 画笔
    private Paint paint;
    // 触摸点跟踪（按pointerId区分）
    private SparseArray<PointF> previousPoints = new SparseArray<>();
    private SparseArray<PointF> currentPoints = new SparseArray<>();
    private SparseArray<PointF> nextPoints = new SparseArray<>();
    // 随机颜色生成器
    private Random random = new Random();

    public MultiTouchDrawView(Context context) {
        super(context);
        init();
    }

    public MultiTouchDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4f);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制所有历史路径
        for (SparseArray<Path> pathGroup : allPaths) {
            for (int i = 0; i < pathGroup.size(); i++) {
                int pointerId = pathGroup.keyAt(i);
                paint.setColor(pathColors.get(pointerId, Color.BLACK));
                canvas.drawPath(pathGroup.get(pointerId), paint);
            }
        }
        // 绘制当前路径
        for (int i = 0; i < activePaths.size(); i++) {
            int pointerId = activePaths.keyAt(i);
            paint.setColor(pathColors.get(pointerId, Color.BLACK));
            canvas.drawPath(activePaths.get(pointerId), paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                // 为新手指分配随机颜色
                if (pathColors.get(pointerId) == null) {
                    pathColors.put(pointerId, Color.BLACK);
                }
                // 初始化新路径
                Path newPath = new Path();
                newPath.moveTo(event.getX(pointerIndex), event.getY(pointerIndex));
                activePaths.put(pointerId, newPath);
                // 初始化触摸点
                previousPoints.put(pointerId, new PointF(event.getX(pointerIndex), event.getY(pointerIndex)));
                currentPoints.put(pointerId, new PointF(event.getX(pointerIndex), event.getY(pointerIndex)));
                nextPoints.put(pointerId, new PointF());
                return true;

            case MotionEvent.ACTION_MOVE:
                // 更新所有活跃手指的路径
                for (int i = 0; i < event.getPointerCount(); i++) {
                    int activePointerId = event.getPointerId(i);
                    Path activePath = activePaths.get(activePointerId);
                    if (activePath == null) continue;

                    // 更新下一个点
                    nextPoints.get(activePointerId).set(event.getX(i), event.getY(i));
                    // 计算控制点（平滑曲线）
                    PointF prev = previousPoints.get(activePointerId);
                    PointF curr = currentPoints.get(activePointerId);
                    PointF next = nextPoints.get(activePointerId);
                    float ctrl1X = (prev.x + curr.x) / 2;
                    float ctrl1Y = (prev.y + curr.y) / 2;
                    float ctrl2X = (curr.x + next.x) / 2;
                    float ctrl2Y = (curr.y + next.y) / 2;
                    // 绘制贝塞尔曲线
                    activePath.cubicTo(ctrl1X, ctrl1Y, ctrl2X, ctrl2Y, next.x, next.y);
                    // 更新点状态
                    prev.set(curr);
                    curr.set(next);
                }
                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                // 将当前路径保存到历史记录
                SparseArray<Path> completedPathGroup = new SparseArray<>();
                completedPathGroup.put(pointerId, activePaths.get(pointerId));
                allPaths.add(completedPathGroup);
                // 清理临时数据
                activePaths.remove(pointerId);
                previousPoints.remove(pointerId);
                currentPoints.remove(pointerId);
                nextPoints.remove(pointerId);
                return true;

            case MotionEvent.ACTION_CANCEL:
                // 取消时保留已绘制路径
                for (int i = 0; i < activePaths.size(); i++) {
                    int id = activePaths.keyAt(i);
                    SparseArray<Path> canceledPathGroup = new SparseArray<>();
                    canceledPathGroup.put(id, activePaths.get(id));
                    allPaths.add(canceledPathGroup);
                }
                activePaths.clear();
                previousPoints.clear();
                currentPoints.clear();
                nextPoints.clear();
                return true;
        }
        return super.onTouchEvent(event);
    }

    // 清除所有路径
    public void clearCanvas() {
        allPaths.clear();
        activePaths.clear();
        pathColors.clear();
        invalidate();
    }

    // 获取当前所有路径（可用于保存或导出）
    public List<SparseArray<Path>> getAllPaths() {
        return allPaths;
    }
}