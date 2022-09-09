package com.xvdong.demolist.business.coordinator;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

/**
 * Created by xvDong on 2022/9/7.
 */

public class DependencyBehavior extends CoordinatorLayout.Behavior<View> {

    private int width;

    public DependencyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        width = context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 确定依赖关系
     *
     * @param parent
     * @param child      要执行动作的View
     * @param dependency child要依赖的View,,,也就是Child要监听的View
     * @return 根据逻辑确定依赖关系
     */
    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        Log.e("Behavior", "----child: " + child.toString());
        Log.e("Behavior", "----dependency: " + dependency.toString());
        return dependency instanceof TextView;
    }

    /**
     * 状态(大小、位置、显示与否等)发生变化时该方法执行
     * 在这里我们定义child要执行的具体动作
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        int top = dependency.getTop();
        int left = dependency.getLeft();
        return true;
    }



    /**
     * 当子View调用NestedScrollingChild的方法startNestedScroll时，会调用该方法。
     * 一定要按照自己的需求返回true,该方法决定了当前控件是否能接收到其内部View(并非是直接子View)滑动时的参数
     *
     * @param coordinatorLayout
     * @param child             这个是要依赖其他滚动View的另一个View,这里是FloatingActionButton
     * @param directTargetChild 直接触发嵌套滚动的view的对象
     * @param target            触发嵌套滚动的view (在这里如果不涉及多层嵌套的话，target和directTargetChild是相同的)
     * @param type  嵌套滑动的方向标志
     * @return 根据返回值确定我们关心那个方向的滑动(x轴 / y轴)，这里我们关心的是y轴方向
     */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        Log.e("123", "directTargetChild: " + directTargetChild);
        Log.e("123", "target: " + target);//在这里directTargetChild和target都是NestedScrollView
        return (type & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }



    /**
     * 当子View调用dispatchNestedPreScroll时会调用该方法
     * 该方法会传入内部View移动的dx,dy,如果你需要消耗一定的dx,dy,就会通过最后一个参数consumed
     * 进行指定。例如我要消耗一半的dy,就可以写consumed[1]=dy/2
     * dx表示本次滚动x方向产生的总距离
     *
     * @param coordinatorLayout
     * @param child             此处是FloatingActionButton
     * @param target            同上
     * @param dxConsumed        target已经消费的x方向的距离
     * @param dyConsumed        target已经消费的x方向的距离
     * @param dxUnconsumed      x方向剩下的滑动距离
     * @param dyUnconsumed      y方向剩下的滑动距离
     */
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
    }



}
