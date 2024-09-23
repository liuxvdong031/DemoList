package com.example.qihang;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by xvDong on 2024/8/29.
 */

public class RedGreen3DImage {
    public static Bitmap[] createRedGreenImage(int width, int height, int boxSize) {
        // 创建红色和绿色的Bitmap
        Bitmap redBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Bitmap greenBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // 创建画布和画笔
        Canvas redCanvas = new Canvas(redBitmap);
        Canvas greenCanvas = new Canvas(greenBitmap);
        Paint paint = new Paint();

        Random rand = new Random();

        // 随机生成3D方块的起始位置
        int boxStartX = rand.nextInt(width - boxSize);
        int boxStartY = rand.nextInt(height - boxSize);

        // 遍历整个图片
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // 随机生成黑白像素
                boolean isWhite = rand.nextBoolean();

                // 默认灰度值
                int gray = isWhite ? 255 : 0;

                // 3D效果区域
                if (x >= boxStartX && x < boxStartX + boxSize && y >= boxStartY && y < boxStartY + boxSize) {
                    // 偏移绿色通道5像素来制造3D效果
                    int greenX = x - 5;
                    if (greenX >= boxStartX) {
                        isWhite = rand.nextBoolean();
                        gray = isWhite ? 255 : 0;
                    }
                }

                // 设置颜色
                int red = gray;
                int green = gray;

                // 将黑色像素替换为透明像素
                if (!isWhite) {
                    // 设置透明色
                    redCanvas.drawColor(Color.TRANSPARENT);
                    greenCanvas.drawColor(Color.TRANSPARENT);
                } else {
                    // 绘制红色图像
                    paint.setColor(Color.argb(255, red, 0, 0));
                    redCanvas.drawPoint(x, y, paint);

                    // 绘制绿色图像
                    paint.setColor(Color.argb(255, 0, green, 0));
                    greenCanvas.drawPoint(x, y, paint);
                }
            }
        }

        Bitmap[] bitmaps = new Bitmap[2];
        bitmaps[0] = redBitmap;
        bitmaps[1] = greenBitmap;
        return bitmaps;
    }

    public static Bitmap create3DImage(int width, int height, int boxSize) {
        // 创建合并的3D图像Bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // 创建画布和画笔
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        Random rand = new Random();

        // 随机生成3D方块的起始位置
        int boxStartX = rand.nextInt(width - boxSize);
        int boxStartY = rand.nextInt(height - boxSize);

        // 遍历整个图片
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // 随机生成黑白像素
                boolean isWhite = rand.nextBoolean();

                // 默认灰度值
                int gray = isWhite ? 255 : 0;

                // 3D效果区域
                int red = gray;
                int green = gray;

                if (x >= boxStartX && x < boxStartX + boxSize && y >= boxStartY && y < boxStartY + boxSize) {
                    // 在40x40的小方块区域内，红色和绿色通道进行偏移
                    // 偏移绿色通道5像素来制造3D效果
                    int greenX = x - 5;
                    if (greenX >= boxStartX && greenX < width) {
                        isWhite = rand.nextBoolean();
                        green = isWhite ? 255 : 0;
                    }
                }

                // 将黑色像素替换为透明像素
                if (gray == 0) {
                    paint.setColor(Color.TRANSPARENT);  // 黑色替换为透明
                } else {
                    // 将红色和绿色通道结合在一个图像中
                    paint.setColor(Color.argb(255, red, green, 0)); // 红色和绿色合并
                }

                // 绘制像素
                canvas.drawPoint(x, y, paint);
            }
        }

        // 返回合并后的 3D 图像
        return bitmap;
    }
}
