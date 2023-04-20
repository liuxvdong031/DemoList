package com.xvdong.demolist.temp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by xvDong on 2023/4/19.
 */

public class LockPatternView extends View {

    private static final int ROWS = 3;  // 行数
    private static final int COLUMNS = 3;  // 列数
    private static final int DOT_SIZE = 20;  // 圆圈大小（半径为 10）

    private Paint mPaint;
    private List<Point> mPoints;
    private boolean[][] mSelectedDots;

    public LockPatternView(Context context) {
        super(context);
        init();
    }

    public LockPatternView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LockPatternView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // 初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);

        // 初始化所有点和选中状态
        mPoints = new ArrayList<>();
        mSelectedDots = new boolean[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                mPoints.add(new Point(j * DOT_SIZE * 3, i * DOT_SIZE * 3));
                mSelectedDots[i][j] = false;
            }
        }

        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                // 获取当前时间
                LocalTime now = LocalTime.now();
                // 定义要输出的格式
                DateTimeFormatter formatter = null;
                formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).withLocale(Locale.CHINA);

                // 执行格式化并输出结果
                String formattedTime = formatter.format(now);
                System.out.println(formattedTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Point p : mPoints) {
            canvas.drawCircle(p.x + DOT_SIZE, p.y + DOT_SIZE, DOT_SIZE, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < mPoints.size(); i++) {
                    Point p = mPoints.get(i);
                    if (event.getX() >= p.x && event.getX() <= p.x + DOT_SIZE * 2 &&
                            event.getY() >= p.y && event.getY() <= p.y + DOT_SIZE * 2) {
                        int row = i / COLUMNS;
                        int col = i % COLUMNS;
                        mSelectedDots[row][col] = true;
                        invalidate();
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                // 处理完九宫格操作后，将选中状态清空
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLUMNS; j++) {
                        mSelectedDots[i][j] = false;
                    }
                }
                invalidate();
                break;
        }
        return true;
    }
}
