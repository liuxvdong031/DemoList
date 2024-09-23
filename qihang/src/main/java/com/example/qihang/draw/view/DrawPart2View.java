package com.example.qihang.draw.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by xvDong on 2024/3/1.
 */

public class DrawPart2View extends View {

    private Paint mPaint;
    private Path mPath;
    private Path mPath2;


    public static final String TAG = "DrawPart2View";

    private Path mDstPath = new Path();
    private float mEndDistance;
    private PathMeasure mPathMeasure;

    public DrawPart2View(Context context) {
        super(context);
        init();
    }

    public DrawPart2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawPart2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint = new Paint();

        mPaint.setAntiAlias(true);//抗锯齿功能
        mPaint.setColor(Color.BLUE);  //设置画笔颜色
        mPaint.setStyle(Paint.Style.STROKE);//仅描边
        mPaint.setStrokeWidth(5);//设置画笔宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND);//圆角线头


        mPath = new Path();
        mPath.moveTo(100,100);
        mPath.lineTo(200,200);
        mPath.lineTo(300,100);
        mPath.lineTo(400,200);
        mPath.lineTo(500,100);

        //使用path 画波浪线
        mPath.moveTo(100,300);
        //x是控制点  y是终点
        mPath.quadTo(200,200,300,300);
        mPath.quadTo(400,400,500,300);

        //使用path画一个S形状
        mPath.moveTo(500,500);
        mPath.quadTo(300,600,500,700);
        mPath.quadTo(700,800,500,900);

        RectF rect = new RectF(300, 600, 500, 800);
        mPath.addRect(rect, Path.Direction.CCW);

        mPath2 = new Path();
        // 定义对号的路径
        mPath2.moveTo(0.2f *1920 , 0.5f * 1200);
        mPath2.lineTo(0.45f * 1920, 0.7f * 1200);
        mPath2.lineTo(0.8f * 1920, 0.3f * 1200);
//        mPath2.close();

    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);
//        canvas.drawPath(mPath2, mPaint);

        mDstPath.reset();

        mDstPath.moveTo(0.2f *1920 , 0.5f * 1200);
        mPathMeasure.getSegment(0, mEndDistance, mDstPath, false);
        canvas.drawPath(mDstPath, mPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath2, false);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(10000);
        valueAnimator.addUpdateListener(animation -> {
            mEndDistance = (float) animation.getAnimatedValue() * mPathMeasure.getLength();
            invalidate();
        });
        valueAnimator.start();
    }

}
