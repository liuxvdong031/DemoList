package com.example.qihang.draw.view;

/**
 * Created by xvDong on 2024/11/11.
 */
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class QRCodeGenerator {

    public static Bitmap generateRedQRCode(int size) {
        return generateQRCode(size, Color.RED, 0);
    }

    public static Bitmap generateGreenQRCode(int size) {
        return generateQRCode(size, Color.GREEN, 10);
    }

    private static Bitmap generateQRCode(int size, int color, int offsetX) {
        // 创建Bitmap并设置Canvas
        Bitmap bitmap = Bitmap.createBitmap(size + offsetX, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // 设置背景颜色为白色
        canvas.drawColor(Color.WHITE);

        // 创建指定颜色的画笔
        Paint paint = new Paint();
        paint.setColor(color);

        int matrixSize = 20; // 每个模块的大小
        int numModules = size / matrixSize; // 总的模块数

        // 填充伪二维码矩阵
        for (int row = 0; row < numModules; row++) {
            for (int col = 0; col < numModules; col++) {
                // 在中心形成“1”的空白区域
                if (isOneShape(row, col, numModules)) {
                    continue;
                } else if (Math.random() > 0.5) {
                    // 其他区域随机填充指定颜色模块
                    canvas.drawRect(
                            col * matrixSize + offsetX, // 将绿色二维码向右偏移10像素
                            row * matrixSize,
                            (col + 1) * matrixSize + offsetX,
                            (row + 1) * matrixSize,
                            paint
                    );
                }
            }
        }

        return bitmap;
    }

    // 判断点是否处于“1”的形状
    private static boolean isOneShape(int row, int col, int numModules) {
        int center = numModules / 2;

        // 设置“1”的形状位置
        return (col == center && row >= center - 3 && row <= center + 3)
                || (col == center - 1 && row == center - 2)
                || (col == center + 1 && row == center - 2);
    }
}
