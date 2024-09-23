package com.example.qihang.draw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by xvDong on 2024/3/1.
 */

public class DrawPart4View extends View {

    private Paint mBluePaint;
    private Paint mRedPaint;

    public DrawPart4View(Context context) {
        super(context);
        init();
    }

    public DrawPart4View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawPart4View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mBluePaint = new Paint();

        mBluePaint.setAntiAlias(true);//抗锯齿功能
        mBluePaint.setColor(Color.BLUE);  //设置画笔颜色
        mBluePaint.setStyle(Paint.Style.STROKE);//仅描边
        mBluePaint.setStrokeWidth(5);//设置画笔宽度
        mBluePaint.setStrokeCap(Paint.Cap.ROUND);//圆角线头

        mRedPaint = new Paint();

        mRedPaint.setAntiAlias(true);//抗锯齿功能
        mRedPaint.setColor(Color.RED);  //设置画笔颜色
        mRedPaint.setStyle(Paint.Style.STROKE);//仅描边
        mRedPaint.setStrokeWidth(5);//设置画笔宽度
        mRedPaint.setStrokeCap(Paint.Cap.ROUND);//圆角线头

    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        canvas.drawRect(0, 0, 100, 100, mBluePaint);

        canvas.translate(100, 100);//画布的平移

        canvas.drawRect(0, 0, 100, 100, mRedPaint);

        canvas.save();//保存之前的画布状态

        canvas.rotate(45, 50, 50);//画布旋转45°

        canvas.drawRect(0, 0, 100, 100,mBluePaint);//画的就是旋转了45°的

        canvas.restore();//恢复保存之前的画布状态

        canvas.drawRect(10, 10, 110, 110, mRedPaint);


        canvas.clipRect(100, 100, 200, 200);//画布的裁剪

        canvas.drawColor(Color.BLACK);


    }

}
