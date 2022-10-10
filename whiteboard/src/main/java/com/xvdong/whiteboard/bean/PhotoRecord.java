package com.xvdong.whiteboard.bean;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;

public class PhotoRecord {
    public PhotoRecord() {
    }

    public Bitmap bitmap;//图形
    public Matrix matrix;//图形
    public RectF photoRectSrc = new RectF();
    public float scaleMax = 3;
    public int position;//顺序

}