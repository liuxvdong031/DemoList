package com.example.qihang.animation;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.example.qihang.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ValueAnimator高级进阶（二）
 */
public class AnimationPart6Activity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_part6);

        mTextView = findViewById(R.id.tv1);
        findViewById(R.id.btn1).setOnClickListener(v -> {
            ofObjectAnimator();
        });
    }

    private void ofObjectAnimator(){
        ValueAnimator animator = ValueAnimator.ofObject(new CharEvaluator(),new Character('A'),new Character('Z'));
        animator.addUpdateListener(animation -> {
            char text = (char)animation.getAnimatedValue();
            mTextView.setText(String.valueOf(text));
        });
        animator.setDuration(10000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    public static class CharEvaluator implements TypeEvaluator<Character> {
        @Override
        public Character evaluate(float fraction, Character startValue, Character endValue) {
            int startInt  = (int)startValue;
            int endInt = (int)endValue;
            int curInt = (int)(startInt + fraction *(endInt - startInt));
            char result = (char)curInt;
            return result;
        }
    }
}