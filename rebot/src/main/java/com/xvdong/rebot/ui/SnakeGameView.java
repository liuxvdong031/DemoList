package com.xvdong.rebot.ui;
/**
 * Created by xvDong on 2025/5/27.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class SnakeGameView extends View {

    private Paint paint;
    private int cellSize = 40;
    private ArrayList<Point> snake;
    private Point direction;
    private Point food;
    private Handler handler;
    private Runnable updateRunnable;
    private Random random;

    public SnakeGameView(Context context) {
        super(context);
        init();
    }

    public SnakeGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SnakeGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        snake = new ArrayList<>();
        snake.add(new Point(5, 5));
        direction = new Point(1, 0);
        food = new Point(10, 10);
        random = new Random();

        handler = new Handler(Looper.getMainLooper());
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                updateGame();
                invalidate();
                handler.postDelayed(this, 300);
            }
        };
        handler.post(updateRunnable);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getX() < getWidth() / 2) {
                        direction.set(0, -1); // 上
                    } else {
                        direction.set(0, 1); // 下
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 背景
        canvas.drawColor(Color.BLACK);

        // 食物
        paint.setColor(Color.RED);
        canvas.drawRect(food.x * cellSize, food.y * cellSize,
                (food.x + 1) * cellSize, (food.y + 1) * cellSize, paint);

        // 蛇
        paint.setColor(Color.GREEN);
        for (Point p : snake) {
            canvas.drawRect(p.x * cellSize, p.y * cellSize,
                    (p.x + 1) * cellSize, (p.y + 1) * cellSize, paint);
        }
    }

    private void updateGame() {
        Point head = snake.get(0);
        Point newHead = new Point(head.x + direction.x, head.y + direction.y);
        snake.add(0, newHead);

        // 吃到食物
        if (newHead.equals(food)) {
            int maxX = getWidth() / cellSize;
            int maxY = getHeight() / cellSize;
            food = new Point(random.nextInt(maxX), random.nextInt(maxY));
        } else {
            snake.remove(snake.size() - 1); // 移动
        }

        // 撞墙重置
        if (newHead.x < 0 || newHead.y < 0 || newHead.x * cellSize > getWidth() || newHead.y * cellSize > getHeight()) {
            snake.clear();
            snake.add(new Point(5, 5));
            direction.set(1, 0);
        }
    }
}
