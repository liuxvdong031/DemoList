package com.xvdong.rebot;

/**
 * Created by xvDong on 2024/11/11.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.Random;

public class RDSImageGenerator {

    public static Bitmap generateRDSImage(int width, int height) {
        // 创建画布和图片
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // 背景颜色
        canvas.drawColor(Color.WHITE);

        // 初始化画笔
        Paint paint = new Paint();
        paint.setTextSize(150);
        paint.setTextAlign(Paint.Align.CENTER);

        // 生成随机数字
        Random random = new Random();
        int randomNumber = random.nextInt(9) + 1;
        String numberString = String.valueOf(randomNumber);

        // 红色文字（左偏）
        paint.setColor(Color.RED);
        canvas.drawText(numberString, width / 2 - 10, height / 2 + 50, paint);

        // 蓝色文字（右偏）
        paint.setColor(Color.BLUE);
        canvas.drawText(numberString, width / 2 + 10, height / 2 + 50, paint);

        return bitmap;
    }

    public static Bitmap generateRDSImage(int width, int height, int number) {
        // 创建空白Bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // 生成随机点背景
        Random random = new Random();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int color = random.nextBoolean() ? Color.BLACK : Color.WHITE;
                bitmap.setPixel(x, y, color);
            }
        }

        // 嵌入数字的立体效果
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(150);
        paint.setAntiAlias(true);

        // 在画布中央绘制数字
        String numberString = String.valueOf(number);
        canvas.drawText(numberString, width / 2 - 30, height / 2 + 50, paint);

        // 根据数字区域进行红蓝像素偏移
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (isNumberArea(bitmap, x, y)) {
                    int color = bitmap.getPixel(x, y);

                    // 边界检查，确保不会超出边界
                    if (color == Color.BLACK) {
                        if (x + 2 < width && y < height) { // 确保不会超出右边界
                            bitmap.setPixel(x + 2, y, Color.RED);
                        }
                        if (x - 2 >= 0 && y < height) { // 确保不会超出左边界
                            bitmap.setPixel(x - 2, y, Color.BLUE);
                        }
                    }
                }
            }
        }

        return bitmap;
    }



    // 判断是否在数字区域的方法
    private static boolean isNumberArea(Bitmap bitmap, int x, int y) {
        // 这里可以根据图像的灰度或阈值来检测是否是数字区域
        int color = bitmap.getPixel(x, y);
        return color == Color.BLACK;
    }
}
