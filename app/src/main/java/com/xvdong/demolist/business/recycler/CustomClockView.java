package com.xvdong.demolist.business.recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.view.View;

import java.util.Calendar;


/**
 * Created by xvDong on 2022/9/9.
 */

public class CustomClockView extends View implements Runnable {
    Paint paint;
    private Handler handler;
    int mCenterX, mHourLength, mMinuteLength, mSecondLength;

    public CustomClockView(Context context, Handler handler) {
        super(context);
        paint = new Paint(); //设置一个笔刷大小是3的黄色的画笔
        paint.setColor(Color.RED);
        this.handler = handler;
        handler.postDelayed(this, 1000);
    }

    //在这里我们将测试canvas提供的绘制图形方法
    @Override
    protected void onDraw(Canvas canvas) {

        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        //将要画的位置移动到屏幕中间
        canvas.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        //将位置移动画纸的坐标点:150,150
        //以半径为150和180画圆
        canvas.drawCircle(0, 0, 150, paint);
        canvas.drawCircle(0, 0, 180, paint);
        //使用path绘制路径文字
        canvas.save();
        //移动绘制文字的位置
        canvas.translate(0, 0);
        Path path = new Path();
        //绘制的时候要注意左上不能大于右下，否则不会显示
        RectF rect = new RectF(-100, -100, 100, 100);
        path.addArc(rect, -220, 280);
        Paint citePaint = new Paint(paint);
        citePaint.setTextSize(28);
        //设置画笔的粗细
        citePaint.setStrokeWidth(3);
        //float hOffset, float vOffset// 设置水平位置  vOffset  设置垂直位置
        // 如果hOffset为0 说明开始位置在path.addArc设置的startAngle开始角度
        // 如果vOffset 为0说明经过的位置是在与RectF的顶部相切处
        canvas.drawTextOnPath("http://blog.csdn.net/u014452224", path, 14, 0, citePaint);
        //为了方便一些转换操作，Canvas 还提供了保存和回滚属性的方法(save和restore)，
        // 比如你可以先保存目前画纸的位置(save)，
        // 然后旋转90度，向下移动100像素后画一些图形，画完后调用restore方法返回到刚才保存的位置
        canvas.restore();

        Paint smallPaint = new Paint(paint); //非数字刻度画笔对象
        smallPaint.setStrokeWidth(2);
        smallPaint.setColor(Color.GRAY);
        float y = 150;
        int count = 60; //总刻度数
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(6);
        paint.setColor(Color.RED);
        paint.setTextSize(24);
        paint.setStrokeWidth(3);


        for (int i = 0; i < count; i++) {
            if (i % 5 == 0) {
                //绘制数字刻度
                canvas.drawText(i == 0 ? "12" : String.valueOf(i / 5), ((i / 5) > 9 || i == 0) ? -15f : -6f, -y - 5f, paint);
            } else {
                //绘制非数字的刻度
                canvas.drawLine(0f, y, 0f, y + 15f, smallPaint);
            }
            canvas.rotate(360 / count, 0f, 0f); //旋转画纸
        }
        Calendar calendar = Calendar.getInstance();
        int currentMinute = calendar.get(Calendar.MINUTE);
        int currentHour = calendar.get(Calendar.HOUR);
        int currentSecond = calendar.get(Calendar.SECOND);
        // 计算分针和时间的角度
        double secondRadian = Math.toRadians((360 - ((currentSecond * 6) - 90)) % 360);
        double minuteRadian = Math.toRadians((360 - ((currentMinute * 6) - 90)) % 360);
        double hourRadian = Math.toRadians((360 - ((currentHour * 30) - 90)) % 360 - (30 * currentMinute / 60));
        // 设置实针为6个象素粗
        paint.setStrokeWidth(6);
        // 在表盘上画时针
        mCenterX = 0;
        mHourLength = 100;
        canvas.drawLine(mCenterX, mCenterX,
                (int) (mCenterX + mHourLength * Math.cos(hourRadian)),
                (int) (mCenterX - mHourLength * Math.sin(hourRadian)), paint);

        // 设置分针为4个象素粗
        paint.setStrokeWidth(4);
        mMinuteLength = 120;
        // 在表盘上画分针
        canvas.drawLine(mCenterX, mCenterX,
                (int) (mCenterX + mMinuteLength * Math.cos(minuteRadian)),
                (int) (mCenterX - mMinuteLength * Math.sin(minuteRadian)),
                paint);
        // 设置分针为2个象素粗
        paint.setStrokeWidth(2);
        // 在表盘上画秒针
        mSecondLength = 145;
        int centerY = 30;
        canvas.drawLine((int) (mCenterX - centerY * Math.cos(secondRadian)), (int) (mCenterX + centerY * Math.sin(secondRadian)),
                (int) (mCenterX + mSecondLength * Math.cos(secondRadian)),
                (int) (mCenterX - mSecondLength * Math.sin(secondRadian)),
                paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, 5, paint);


    }

    @Override
    public void run() {
        // 重新绘制View
        this.invalidate();
        // 重新设置定时器，在60秒后调用run方法
        handler.postDelayed(this, 1000);
    }
}

