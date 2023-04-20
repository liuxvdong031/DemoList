package com.xvdong.demolist;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.blankj.utilcode.util.SizeUtils;

import java.util.List;

/**
 * Created by xvDong on 2023/4/14.
 */

public class NoticeView extends ViewFlipper implements View.OnClickListener {
    private Context mContext;
    private List<String> mNotices;
    private OnNoticeClickListener mOnNoticeClickListener;

    public NoticeView(Context context) {
        super(context);
    }

    public NoticeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setFlipInterval(3000);
        int px = SizeUtils.dp2px(5f);
        setPadding(px, px, px, px);
        setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.notice_in));
        setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.notice_out));
    }

    public void addNotice(List<String> notices) {
        mNotices = notices;
        removeAllViews();
        for (int i = 0; i < mNotices.size(); i++) {
            String notice = notices.get(i);
            TextView textView = new TextView(mContext);
            textView.setSingleLine();
            textView.setText(notice);
            textView.setTextSize(20f);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setTextColor(Color.parseColor("#666666"));
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            textView.setTag(i);
            textView.setOnClickListener(this);
            NoticeView.this.addView(textView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
    }


    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        String notice = mNotices.get(position);
        if (mOnNoticeClickListener != null) {
            mOnNoticeClickListener.onNoticeClick(position, notice);
        }
    }

    public interface OnNoticeClickListener {
        void onNoticeClick(int position, String notice);
    }

    public void setOnNoticeClickListener(OnNoticeClickListener onNoticeClickListener) {
        mOnNoticeClickListener = onNoticeClickListener;
    }

}

