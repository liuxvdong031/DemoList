package com.xvdong.sandtable;

import android.os.Bundle;
import android.view.View;

import com.xvdong.sandtable.taglibrary.view.ImageDragRectLayout;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by KF on 2017/10/27.
 */

public class ImageEditActivity extends AppCompatActivity {
    private String mImgPath = "https://img95.699pic.com/photo/50164/0735.jpg_wh300.jpg";
    private ImageDragRectLayout mImageIdr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_drag_rect);
        getSupportActionBar().hide();
        mImageIdr = (ImageDragRectLayout) findViewById(R.id.idr_image);
        initData();
    }

    public void addIcon(View v) {
        mImageIdr.addMiddleIcon();
    }

    public void saveImage(View v) {
//        mImageIdr.savePhotoView(mImgPath);
        //如果是网络图片需要先把图片下载到本地
    }

    private void initData() {
        mImageIdr.setImage(mImgPath);
    }

}
