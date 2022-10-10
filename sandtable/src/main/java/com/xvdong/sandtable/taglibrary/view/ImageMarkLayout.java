package com.xvdong.sandtable.taglibrary.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnMatrixChangedListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.xvdong.sandtable.MarkBean;
import com.xvdong.sandtable.taglibrary.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Created by cc on 2017/10/25.
 */

public class ImageMarkLayout extends FrameLayout implements View.OnClickListener {

    private static final String TAG = ImageMarkLayout.class.getSimpleName();
    private PhotoView photoView;
    private RectF tempRectF;
    private OnIconClickListener onIconClickListener;
    private OnLayoutReadyListener onLayoutReadyListener;
    private Matrix photoViewMatrix;
    boolean firstLoadPhotoView = true;
    private MarkBean lastSelectedMark;
    List<MarkBean> mMarkBeans = new ArrayList<>();

    public ImageMarkLayout(@NonNull Context context) {
        this(context, null);
    }

    public ImageMarkLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageMarkLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(final Context context) {
        photoView = new PhotoView(context);
        LayoutParams layoutParams =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(photoView, layoutParams);
        photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        photoView.setOnMatrixChangeListener(new OnMatrixChangedListener() {
            @Override
            public void onMatrixChanged(RectF rectF) {
                if (mMarkBeans != null && mMarkBeans.size() > 0) {
                    for (MarkBean bean : mMarkBeans) {
                        float newX = bean.getSx() * (rectF.right - rectF.left);
                        float newY = bean.getSy() * (rectF.bottom - rectF.top);
                        //保持底部中心位置不变
                        bean.getTextView().setX(rectF.left + newX - DensityUtil.dp2px(getContext(), 45) / 2);
                        bean.getTextView().setY(rectF.top + newY - DensityUtil.dp2px(getContext(), 48));
                    }
                }
                tempRectF = rectF;
                //图片加载完成后才可以添加图标
                if (onLayoutReadyListener != null) {
                    onLayoutReadyListener.onLayoutReady();
                    //保证只执行一次
                    onLayoutReadyListener = null;
                }

            }
        });
    }

    /**
     * 添加标记
     *
     * @param bean 标记类
     */
    private void addIcon(MarkBean bean) {
        //记住此时photoView的Matrix
        if (photoViewMatrix == null) {
            photoViewMatrix = new Matrix();
        }
        photoView.getSuppMatrix(photoViewMatrix);
        final TextView markTextView = new TextView(getContext());
        bean.setTextView(markTextView);
        bean.initTextView();
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DensityUtil.dp2px(getContext(), 28));
        float newX = bean.getSx() * (tempRectF.left - tempRectF.right);
        float newY = bean.getSy() * (tempRectF.bottom - tempRectF.top);
        bean.getTextView().setX(tempRectF.left + newX);
        bean.getTextView().setY(tempRectF.top + newY);
        bean.getTextView().setOnClickListener(this);
        addView(bean.getTextView(), layoutParams);
    }

    public void updateIconResource(MarkBean markBean) {
        if (lastSelectedMark != null) {
            lastSelectedMark.getTextView().setBackgroundResource(lastSelectedMark.getBgDrawable());
        }
        markBean.getTextView().setBackgroundResource(markBean.getCheckedDrawable());
        lastSelectedMark = markBean;
    }

    @Override
    public void onClick(View v) {
        if (onIconClickListener != null) {
            MarkBean markBean = getMarkBeanById((Integer) v.getTag());
            onIconClickListener.onIconClick(markBean);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (photoViewMatrix != null) {
            photoView.setDisplayMatrix(photoViewMatrix);
        }
    }

    public interface OnIconClickListener {
        void onIconClick(MarkBean markBean);
    }

    public interface OnLayoutReadyListener {
        void onLayoutReady();
    }

    public void setOnIconClickListener(OnIconClickListener onIconClickListener) {
        this.onIconClickListener = onIconClickListener;
    }

    public void setOnLayoutReadyListener(OnLayoutReadyListener onLayoutReadyListener) {
        this.onLayoutReadyListener = onLayoutReadyListener;
    }

    /**
     * 设置图片
     *
     * @param url 网址或本地路径
     */

    public void setBackgroundImage(String url) {
        firstLoadPhotoView = true;
        Glide.with(getContext()).load(url).override(getWidth(), getHeight()).into(photoView);
    }


    /**
     * 移除所有mark
     */
    public void removeAllMark() {
        if (mMarkBeans != null && mMarkBeans.size() > 0) {
            for (MarkBean markBean : mMarkBeans) {
                removeView(markBean.getTextView());
            }
            mMarkBeans.clear();
        }
    }

    /**
     * 移除mark
     */
    public void removeIcon(MarkBean markBean) {
        removeView(markBean.getTextView());
        mMarkBeans.remove(markBean);
    }

    /**
     * 添加mark标记
     *
     * @param MarkBeanList 标记集合
     */
    public void addIcons(List<MarkBean> MarkBeanList) {
        if (MarkBeanList != null && MarkBeanList.size() > 0) {
            mMarkBeans.addAll(MarkBeanList);
            for (MarkBean bean : MarkBeanList) {
                addIcon(bean);
            }
        }
    }

    /**
     * 更改选中标记
     *
     * @param id
     */
    public void changeCheckedMark(int id, boolean needChangeMatrix) {
        MarkBean markBean = getMarkBeanById(id);
        if (markBean != null) {
            updateIconResource(markBean);
            if (needChangeMatrix) {
                //1计算当前中心点
                float centerX = Math.abs(tempRectF.left) + (getWidth() / 2);
                float centerY = Math.abs(tempRectF.top) + (getHeight() / 2);
                //2目标中心点
                float targetX = markBean.getSx() * getWidth() * photoView.getScale();
                float targetY = markBean.getSy() * getHeight() * photoView.getScale();

                Matrix imageMatrix = photoView.getImageMatrix();
                imageMatrix.postTranslate(-(targetX - centerX), -(targetY - centerY));
                photoView.setDisplayMatrix(imageMatrix);
            }
        }
    }

    /**
     * 通过id 获取mark实例
     *
     * @param id
     * @return
     */
    private MarkBean getMarkBeanById(int id) {
        for (MarkBean MarkBean : mMarkBeans) {
            if (MarkBean.getId() == id) {
                return MarkBean;
            }
        }
        return null;
    }
}
