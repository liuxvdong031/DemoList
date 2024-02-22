package com.example.qihang.animation;

import android.animation.ArgbEvaluator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.qihang.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ValueAnimator高级进阶（一）
 * 自定义动画的插值器
 */
public class AnimationPart5Activity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_part5);

        mImageView = findViewById(R.id.iv1);
        findViewById(R.id.btn1).setOnClickListener(v -> {
            interpolatorAnimation();
        });

        findViewById(R.id.btn2).setOnClickListener(v -> {
//            Color.parseColor("#FFFFFF00"),
//            Color.parseColor("#88888888");
            colorConversionAnimator(0xffffff00,0xff0000ff);
        });
    }

    /**
     * 属性动画使用插值器
     */
    private void interpolatorAnimation() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 600);
        animator.addUpdateListener(animation -> {
            int curValue = (int) animation.getAnimatedValue();
            mImageView.layout(mImageView.getLeft(), curValue, mImageView.getRight(), curValue + mImageView.getHeight());
        });
        animator.setDuration(1000);
        //自定义插值器动画正常是0-600  这个插值器反过来了.变成了600-0
        animator.setInterpolator(new MyLinearInterpolator());
        //自定义转值器 原来的基础上增加了200
//        animator.setEvaluator(new MyEvaluator());
        animator.start();
    }

    /**
     * 背景色转换动画
     * @param startColor
     * @param endColor
     */
    private void colorConversionAnimator(int startColor, int endColor) {
        ValueAnimator animator = ValueAnimator.ofInt(startColor, endColor);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setDuration(3000);
        animator.addUpdateListener(animation -> {
            int curValue = (int) animation.getAnimatedValue();
            mImageView.setBackgroundColor(curValue);
        });

        animator.start();
    }

    /**
     * 自定义加速器
     */
    public static class MyLinearInterpolator implements TimeInterpolator {

        /**
         * 在重写插值器时，需要强有力的数学知识做基础，一般而言，都是通过数学公式来计算插值器的变化趋势的
         *
         * @param input input参数是一个float类型，它取值范围是0到1，表示当前动画的进度，
         *              取0时表示动画刚开始，取1时表示动画结束，取0.5时表示动画中间的位置，其它类推。
         * @return 进度值 0-1之间
         */
        public float getInterpolation(float input) {
            return 1 - input;
        }
    }

    /**
     * 自定义转值器
     * (int)(startInt + fraction * (endValue - startInt)) 系统原来的
     * 例如  ValueAnimator.ofInt(0, 600)
     * 结果为: 0+(600-0) * 进度(0.2)
     */
    public static class MyEvaluator implements TypeEvaluator<Integer> {
        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            //在原来的基础上增加了200
            return (int) (200 + startValue + fraction * (endValue - endValue));
        }
    }

}