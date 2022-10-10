package com.xvdong.demolist.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Html;
import android.util.AttributeSet;

import com.xvdong.demolist.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by xvDong on 2022/9/26.
 */

public class KeyValueTextView extends AppCompatTextView {

    private String mKeyText;
    private String mValueText;
    private String mKeyColor;
    private String mValueColor;

    public KeyValueTextView(@NonNull Context context) {
        super(context);
    }

    public KeyValueTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.KeyValueTextView);
        mKeyText = ta.getString(R.styleable.KeyValueTextView_keyText);
        mValueText = ta.getString(R.styleable.KeyValueTextView_valueText);
        mKeyColor = ta.getString(R.styleable.KeyValueTextView_keyColor);
        mValueColor = ta.getString(R.styleable.KeyValueTextView_valueColor);
        String str1 = String.format("<font color=\""+ mKeyColor +"\">%s</font><font color=\""+ mValueColor +"\">%s</font>", mKeyText, mValueText);
        setText(Html.fromHtml(str1));
    }

    public void  setValueText(String valueText){
        mValueText = valueText;
        String str1 = String.format("<font color=\""+ mKeyColor +"\">%s</font><font color=\""+ mValueColor +"\">%s</font>", mKeyText, mValueText);
        setText(Html.fromHtml(str1));
    }

}
