package com.xvdong.sandtable;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

/**
 * Created by xvDong on 2022/9/22.
 */

public class MarkBean {
    private int id;
    //待售 在售 售罄等状态
    private int state;
    //X轴的百分比
    private float sx;
    //Y轴的百分比
    private float sy;
    //标记中间显示的标题
    private String title;
    //背景图
    private int bgDrawable;
    //选中状态时候的Drawable
    private @DrawableRes int checkedDrawable;
    //标记
    private TextView mTextView;

    public MarkBean() {
    }

    public MarkBean(int id, int state, float sx, float sy, String title, @DrawableRes int bgDrawable) {
        this.id = id;
        this.state = state;
        this.sx = sx;
        this.sy = sy;
        this.title = title;
        this.bgDrawable = bgDrawable;
    }

    public void initTextView(){
        if (mTextView != null){
            mTextView.setBackgroundResource(bgDrawable);
            mTextView.setTextColor(Color.WHITE);
            mTextView.setTextSize(16);
            mTextView.setText(title);
            mTextView.setPadding(10,0,10,0);
            mTextView.setGravity(Gravity.CENTER | Gravity.TOP);
            mTextView.setTag(id);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getSx() {
        return sx;
    }

    public void setSx(float sx) {
        this.sx = sx;
    }

    public float getSy() {
        return sy;
    }

    public void setSy(float sy) {
        this.sy = sy;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBgDrawable() {
        return bgDrawable;
    }

    public void setBgDrawable(int bgDrawable) {
        this.bgDrawable = bgDrawable;
    }

    public int getCheckedDrawable() {
        return checkedDrawable;
    }

    public void setCheckedDrawable(int checkedDrawable) {
        this.checkedDrawable = checkedDrawable;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setTextView(TextView textView) {
        mTextView = textView;
    }
}
