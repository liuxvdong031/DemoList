package com.example.qihang.draw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by xvDong on 2024/3/1.
 */

public class DrawPart1View extends View {

    private Paint mPaint;

    public DrawPart1View(Context context) {
        super(context);
        init();
    }

    public DrawPart1View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawPart1View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint = new Paint();

        mPaint.setAntiAlias(true);//抗锯齿功能
        mPaint.setColor(Color.RED);  //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);//填充内部
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);//填充内部和描边
        mPaint.setStyle(Paint.Style.STROKE);//仅描边
        mPaint.setStrokeWidth(10);//设置画笔宽度
        mPaint.setShadowLayer(10, 15, 15, Color.GREEN);//设置阴影

        //字体样式设置
        mPaint.setTextAlign(Paint.Align.CENTER);//设置文字对齐方式，取值：align.CENTER、align.LEFT或align.RIGHT
        mPaint.setTextSize(12);//设置文字大小
        mPaint.setFakeBoldText(true);//设置是否为粗体文字
        mPaint.setUnderlineText(true);//设置下划线
        mPaint.setTextSkewX((float) -0.25);//设置字体水平倾斜度，普通斜体字是-0.25
        mPaint.setStrikeThruText(true);//设置带有删除线效果

        //其它设置
        mPaint.setTextScaleX(2);//只会将水平方向拉伸，高度不会变
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        //这两个功能一样，都是用来设置背景颜色的。
        canvas.drawRGB(255, 255, 0);
        canvas.drawColor(Color.BLUE);
        //画线
        canvas.drawLine(100,100,100,500,mPaint);
        //画点
        canvas.drawPoint(200,100,mPaint);
        //画多个点
        float []pts={50,10,150,100,250,200,350,400};
        canvas.drawPoints(pts,mPaint);
        //绘制矩形
        canvas.drawRect(300,300,500,500,mPaint);
        //绘制圆角矩形
        canvas.drawRoundRect(600,300,800,500,20,20,mPaint);
        //绘制椭圆
        canvas.drawOval(900,300,1000,500,mPaint);
        //绘制圆形
        canvas.drawOval(1100,300,1300,500,mPaint);
        //绘制弧形
        RectF rect1 = new RectF(100, 10, 300, 100);
        canvas.drawArc(rect1, 0, 90, true, mPaint);
        //参数 useCenter  true 扇形  false 弧形
        RectF rect2 = new RectF(400, 10, 600, 100);
        canvas.drawArc(rect2, 180, 270, false, mPaint);

    }
}
