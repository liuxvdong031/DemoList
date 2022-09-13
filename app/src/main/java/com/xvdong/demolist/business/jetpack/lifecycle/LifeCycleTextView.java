package com.xvdong.demolist.business.jetpack.lifecycle;

import android.content.Context;
import android.util.AttributeSet;

import com.blankj.utilcode.util.LogUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created by xvDong on 2022/9/13.
 */

public class LifeCycleTextView extends AppCompatTextView implements LifecycleEventObserver {

    public LifeCycleTextView(Context context) {
        super(context);
    }

    public LifeCycleTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        LogUtils.json(event);
    }
}
