package com.xvdong.rebot.ui;

/**
 * Created by xvDong on 2025/5/27.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;


/**
 * * Created by xvDong on 2025/5/27.
 * 镜像效果布局 右边复制左边的效果
 */
public class MirrorLayout extends FrameLayout {

    public MirrorLayout(Context context) {
        super(context);
    }

    public MirrorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MirrorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean leftEye = false;
    private boolean rightEye = true;
    private float leftPD = 0;
    private float rightPD = 0;

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int halfWidth = getWidth() / 2;
        // 左边正常绘制
        canvas.save();
        canvas.translate(leftPD, 0);//左瞳距
        canvas.clipRect(0, 0, halfWidth, getHeight());
        if (leftEye){
            canvas.drawColor(Color.BLACK);
        }else {
            super.dispatchDraw(canvas);
        }
        canvas.restore();

        // 右边重复绘制
        canvas.save();
        canvas.translate(halfWidth + rightPD, 0);//右瞳距
        canvas.clipRect(0, 0, halfWidth, getHeight());
        if (rightEye){
            canvas.drawColor(Color.BLACK);
        }else {
            super.dispatchDraw(canvas);
        }
        canvas.restore();
    }

    public boolean isLeftEye() {
        return leftEye;
    }

    public void setLeftEye(boolean leftEye) {
        this.leftEye = leftEye;
    }

    public boolean isRightEye() {
        return rightEye;
    }

    public void setRightEye(boolean rightEye) {
        this.rightEye = rightEye;
    }

    public float getLeftPD() {
        return leftPD;
    }

    public void setLeftPD(float leftPD) {
        this.leftPD = leftPD;
    }

    public float getRightPD() {
        return rightPD;
    }

    public void setRightPD(float rightPD) {
        this.rightPD = rightPD;
    }
}
