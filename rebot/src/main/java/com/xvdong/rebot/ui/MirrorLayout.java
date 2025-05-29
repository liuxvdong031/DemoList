package com.xvdong.rebot.ui;

/**
 * Created by xvDong on 2025/5/27.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;


/**
 *  * Created by xvDong on 2025/5/27.
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

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int halfWidth = getWidth() / 2;

        // 左边正常绘制
        canvas.save();
        canvas.clipRect(0, 0, halfWidth, getHeight());
        super.dispatchDraw(canvas);
        canvas.restore();

        // 右边重复绘制
        canvas.save();
        canvas.translate(halfWidth, 0);
        canvas.clipRect(0, 0, halfWidth, getHeight());
        super.dispatchDraw(canvas);
        canvas.restore();
    }
}
