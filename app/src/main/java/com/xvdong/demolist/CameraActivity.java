package com.xvdong.demolist;

import android.Manifest;
import android.view.SurfaceView;

import com.xvdong.demolist.core.base.BaseActivity;

import androidx.core.app.ActivityCompat;

/**
 * Created by xvDong on 2023/2/13.
 */

class CameraActivity extends BaseActivity {

    private SurfaceView mGLSurfaceView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    protected void initData() {
        super.initData();
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},1);
        mGLSurfaceView = findViewById(R.id.surface_view);
    }
}
