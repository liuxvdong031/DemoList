package com.example.qihang.draw.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class RDSImageGenerator {

    private static final String TAG = "RDSImageGenerator";

    // 生成带有隐藏随机数字的RDS图像，并返回Bitmap
    public static Bitmap generateRDSImage(int width, int height) {
        Random rand = new Random();

        // 随机生成1到9的数字
        int number = rand.nextInt(9) + 1;
        int[][] numberShape = getNumberShape(number);  // 获取数字形状的数组

        // 数字形状的宽度和高度
        int numberWidth = numberShape.length * 10; // 放大每个像素为10x10块
        int numberHeight = numberShape[0].length * 10;

        // 随机生成数字的起始位置
        int startX = rand.nextInt(width - numberWidth);
        int startY = rand.nextInt(height - numberHeight);

        Log.d(TAG, "随机数字 " + number + " 的位置: (" + startX + ", " + startY + ")");

        // 创建Bitmap，并使用Canvas绘制随机点
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        // 遍历整个图像，为每个像素点生成随机的红绿色
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // 随机生成黑白像素
                int pixelColor = rand.nextBoolean() ? Color.WHITE : Color.BLACK;

                // 默认红绿色调
                int red = Color.red(pixelColor);
                int green = Color.green(pixelColor);

                // 在数字区域内应用更明显的3D效果
                int localX = (x - startX) / 10; // 获取放大后的数字区域坐标
                int localY = (y - startY) / 10;

                if (x >= startX && x < startX + numberWidth && y >= startY && y < startY + numberHeight) {
                    // 如果在数字形状区域内
                    if (localX < numberShape.length && localY < numberShape[0].length && numberShape[localX][localY] == 1) {
                        int redOffsetX = x + 5;  // 偏移红色通道
                        int greenOffsetX = x - 5;  // 偏移绿色通道

                        if (redOffsetX < width) {
                            red = 255;  // 数字区域红色加亮
                        }
                        if (greenOffsetX >= 0) {
                            green = 0;  // 数字区域绿色减弱
                        }
                    }
                }

                // 设置红绿色通道颜色
                paint.setColor(Color.rgb(red, green, 0));
                canvas.drawPoint(x, y, paint);
            }
        }

        return bitmap;
    }

    // 获取数字形状的布尔数组（1表示数字的像素区域，0表示空白区域）
    private static int[][] getNumberShape(int number) {
        switch (number) {
            case 1:
                return new int[][]{
                        {0, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0}
                };
            case 2:
                return new int[][]{
                        {1, 1, 1},
                        {0, 0, 1},
                        {1, 1, 1},
                        {1, 0, 0},
                        {1, 1, 1}
                };
            case 3:
                return new int[][]{
                        {1, 1, 1},
                        {0, 0, 1},
                        {1, 1, 1},
                        {0, 0, 1},
                        {1, 1, 1}
                };
            // 继续为数字4-9定义形状
            default:
                return new int[][]{
                        {1, 1, 1},
                        {1, 0, 1},
                        {1, 1, 1},
                        {1, 0, 1},
                        {1, 1, 1}
                };
        }
    }
}
