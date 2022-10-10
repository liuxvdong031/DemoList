package com.xvdong.sandtable;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xvdong.sandtable.taglibrary.view.ImageMarkLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ImageMarkActivity extends AppCompatActivity {

    private ImageMarkLayout mImageMarkLayout;
    private List<String> titles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_mark);

        mImageMarkLayout = (ImageMarkLayout) findViewById(R.id.idl_idl_photo);
        //设置背景图片
        mImageMarkLayout.setBackgroundImage("https://img95.699pic.com/photo/50164/0735.jpg_wh300.jpg");
        initIcon();
        mImageMarkLayout.setOnIconClickListener(new ImageMarkLayout.OnIconClickListener() {
            @Override
            public void onIconClick(MarkBean markBean) {
                Toast.makeText(ImageMarkActivity.this,markBean.getTitle() + "号楼",Toast.LENGTH_SHORT).show();
                mImageMarkLayout.changeCheckedMark(markBean.getId(),false);
            }
        });
    }

    private void initIcon() {

        final List<MarkBean> marks = new ArrayList<>();
        MarkBean markBean = new MarkBean(0,1, 0.35f, 0.5f, "1#", R.drawable.mark_in_stock);
        markBean.setCheckedDrawable(R.drawable.mark_checked);
        marks.add(markBean);
        markBean = new MarkBean(1, 2, 0.5f, 0.5f, "二期2#", R.drawable.mark_in_stock);
        markBean.setCheckedDrawable(R.drawable.mark_checked);
        marks.add(markBean);
        markBean = new MarkBean(2, 3, 0.4f, 0.6f, "3#", R.drawable.mark_for_sale);
        markBean.setCheckedDrawable(R.drawable.mark_checked);
        marks.add(markBean);
        markBean = new MarkBean(3, 3, 0.6f, 0.7f, "D区4#", R.drawable.mark_sold_out);
        markBean.setCheckedDrawable(R.drawable.mark_checked);
        marks.add(markBean);

        if (titles == null){
            titles = new ArrayList<>();
        }
        for (MarkBean mark : marks) {
            titles.add(mark.getTitle());
        }

        //监听图片是否加载完成
        mImageMarkLayout.setOnLayoutReadyListener(new ImageMarkLayout.OnLayoutReadyListener() {
            @Override
            public void onLayoutReady() {
                mImageMarkLayout.addIcons(marks);
            }
        });
    }

    public void moveto1(View view) {
        mImageMarkLayout.changeCheckedMark(0,true);
    }
    public void moveto2(View view) {
        mImageMarkLayout.changeCheckedMark(1,true);
    }
    public void moveto3(View view) {
        mImageMarkLayout.changeCheckedMark(2,true);
    }
    public void moveto4(View view) {
        mImageMarkLayout.changeCheckedMark(3,true);
    }
}
