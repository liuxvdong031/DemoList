package com.xvdong.demolist.temp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyCanvasView extends View {
    private float[] numbers;
    private Paint[] paintArray;
    private int centerX, centerY;
    private int radius = 100; // 最内层正五边形的半径
    private int gap = 50; // 每个同心正五边形之间的间距
    private Paint mPaint;

    public MyCanvasView(Context context) {
        super(context);
    }

    public MyCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setNumbers(float[] numbers) {
        this.numbers = numbers;
        paintArray = new Paint[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            paintArray[i] = new Paint();
            paintArray[i].setColor(Color.BLACK);
            paintArray[i].setStyle(Paint.Style.FILL);
            paintArray[i].setAntiAlias(true);

            mPaint = new Paint();
            mPaint.setColor(Color.BLUE);
            mPaint.setStrokeWidth(3);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAntiAlias(true);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        drawPentagons(canvas);
        drawPoints(canvas);
    }

    private void drawPentagons(Canvas canvas) {
        Paint pentagonPaint = new Paint();
        pentagonPaint.setStyle(Paint.Style.STROKE);
        pentagonPaint.setColor(Color.BLACK);
        pentagonPaint.setAntiAlias(true);

        Paint pointPaint = new Paint();
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(Color.BLACK);
        pointPaint.setTextSize(40);
        pointPaint.setTypeface(Typeface.DEFAULT_BOLD);
        pointPaint.setAntiAlias(true);

        for (int i = 0; i < 5; i++) {
            Path path = new Path();
            for (int j = 0; j < 5; j++) {
                float angle = (float) ((2 * Math.PI / 5) * j + Math.PI / 2);
                float x = (float) (centerX + (radius + gap * i) * Math.cos(angle));
                float y = (float) (centerY - (radius + gap * i) * Math.sin(angle));

                if (j == 0) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }
            path.close();
            canvas.drawPath(path, pentagonPaint);
            if (i == 4) {
                for (int j = 0; j < 5; j++) {
                    float angle = (float) ((2 * Math.PI / 5) * j + Math.PI / 2);
                    float x = (float) (centerX + (radius + gap * 4) * Math.cos(angle));
                    float y = (float) (centerY - (radius + gap * 4) * Math.sin(angle));
                    if (j == 0) {
                        canvas.drawText("价格竞争力", x - 125, y - 40, pointPaint);
                    } else if (j == 1) {
                        canvas.drawText("房源维护度", x - 240, y + 20, pointPaint);
                    } else if (j == 2) {
                        canvas.drawText("住房宝热度", x - 240, y - 20, pointPaint);
                    } else if (j == 3) {
                        canvas.drawText("业主配合度", x + 40, y - 20, pointPaint);
                    } else if (j == 4) {
                        canvas.drawText("房源优势", x + 40, y + 20, pointPaint);
                    }
                }
            }
        }
    }

    // 在 drawPoints() 方法中修改如下
    private void drawPoints(Canvas canvas) {
        float maxNumber = 10;
        float minNumber = 1;

        for (float number : numbers) {
            if (number > maxNumber) {
                maxNumber = number;
            }
            if (number < minNumber) {
                minNumber = number;
            }
        }

        float range = maxNumber - minNumber;

        for (int i = 0; i < numbers.length; i++) {
            float angle = (float) (2 * Math.PI / 5 * i + Math.PI / 2);
            float x = (float) (centerX + (radius + 200 * ((numbers[i] - minNumber) / range)) * Math.cos(angle));
            float y = (float) (centerY - (radius + 200 * ((numbers[i] - minNumber) / range)) * Math.sin(angle));

            canvas.drawCircle(x, y, 15, mPaint);
            mPaint.setTextSize(40);
            canvas.drawText(String.valueOf(numbers[i]), x, y - 20, mPaint);
            // 绘制连接线段
            if (i > 0) {
                float preX = (float) (centerX + (radius + 200 * ((numbers[i - 1] - minNumber) / range)) * Math.cos((float) (2 * Math.PI / 5 * (i - 1) + Math.PI / 2)));
                float preY = (float) (centerY - (radius + 200 * ((numbers[i - 1] - minNumber) / range)) * Math.sin((float) (2 * Math.PI / 5 * (i - 1) + Math.PI / 2)));
                canvas.drawLine(preX, preY, x, y, mPaint);
            }
            // 绘制连接最后一个点与第一个点的线段
            if (i == numbers.length - 1) {
                float firstX = (float) (centerX + (radius + 200 * ((numbers[0] - minNumber) / range)) * Math.cos((float) (2 * Math.PI / 5 * 0 + Math.PI / 2)));
                float firstY = (float) (centerY - (radius + 200 * ((numbers[0] - minNumber) / range)) * Math.sin((float) (2 * Math.PI / 5 * 0 + Math.PI / 2)));
                canvas.drawLine(x, y, firstX, firstY, mPaint);
            }
        }
    }
}
