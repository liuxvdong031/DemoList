package com.example.qihang.draw.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.qihang.R;

import androidx.annotation.Nullable;

/**
 * Created by xvDong on 2024/3/22.
 */

public class PupilDistanceView extends View {

    private int mCurrentDistance;
    private int mTargetDistance;
    private Bitmap mEyeLeft;
    private Bitmap mEyeRight;
    private int mMeasuredWidth, mMeasuredHeight;
    private Paint mPaint;
    private Paint mLinePaint;
    private int mDifferenceDistance;
    public static final String TAG = "PupilDistanceView";


    public PupilDistanceView(Context context) {
        super(context);
        init();
    }


    public PupilDistanceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PupilDistanceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.GREEN);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(3); // 设置虚线的宽度
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mLinePaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        mLinePaint.setAntiAlias(false);

        mEyeLeft = BitmapFactory.decodeResource(getResources(), R.mipmap.eye_left);
        mEyeRight = BitmapFactory.decodeResource(getResources(), R.mipmap.eye_right);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasuredWidth = getMeasuredWidth();
        mMeasuredHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = mMeasuredWidth / 2;
        int leftStartPoint = center - mCurrentDistance / 2 - mEyeLeft.getWidth();

        int rightStartPoint = center + mCurrentDistance / 2;

        canvas.drawBitmap(mEyeLeft, leftStartPoint - mDifferenceDistance, (float) (mMeasuredHeight / 2 - mEyeLeft.getHeight() / 2), mPaint);
        canvas.drawBitmap(mEyeRight, rightStartPoint + mDifferenceDistance, (float) (mMeasuredHeight / 2 - mEyeRight.getHeight() / 2), mPaint);

        canvas.drawLine(leftStartPoint + mEyeLeft.getWidth() / 2 - mDifferenceDistance + 5, (mMeasuredHeight / 2 - mEyeLeft.getHeight() / 2) + mEyeLeft.getHeight() / 2,
                rightStartPoint + mEyeRight.getWidth() / 2 + mDifferenceDistance - 5, (mMeasuredHeight / 2 - mEyeRight.getHeight() / 2) + mEyeRight.getHeight() / 2, mLinePaint);

        Log.d(TAG, "差值为: " + mDifferenceDistance + "左眼: " + leftStartPoint);
    }


    private void adjustPupilDistance() {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(mCurrentDistance, mTargetDistance);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            mDifferenceDistance = (int) animation.getAnimatedValue();
            invalidate();
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                PupilDistanceView.this.postDelayed(() -> {
                    mCurrentDistance = mTargetDistance;
                    mDifferenceDistance = 0;
                }, 100);
            }
        });
        valueAnimator.start();
    }


    public int getCurrentDistance() {
        return mCurrentDistance;
    }

    public void setCurrentDistance(int currentDistance) {
        mCurrentDistance = currentDistance;
        invalidate();
    }

    public int getTargetDistance() {
        return mTargetDistance;
    }

    public void setTargetDistance(int targetDistance) {
        mTargetDistance = targetDistance;
        adjustPupilDistance();
    }


}
