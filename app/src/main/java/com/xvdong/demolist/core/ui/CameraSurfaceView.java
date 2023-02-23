package com.xvdong.demolist.core.ui;

import android.content.Context;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

/**
 * Created by xvDong on 2023/2/13.
 * 涉及到的类:
 *    1:SurfaceView  //显示摄像头内容
 *    2:Camera2      //摄像头相关
 *    3:MediaRecorder//视频录制相关
 *    4:             //视频的合并
 *    https://blog.csdn.net/Vaccae/article/details/111596104
 *    https://www.jianshu.com/p/96ee1b7e67e3
 */

public class CameraSurfaceView extends GLSurfaceView implements SurfaceHolder.Callback {

   private int mCameraIndex = Camera.CameraInfo.CAMERA_FACING_BACK;

   public CameraSurfaceView(Context context) {
      super(context);
      init();
   }

   public CameraSurfaceView(Context context, AttributeSet attrs) {
      super(context, attrs);
      init();
   }

   @Override
   public void surfaceCreated(SurfaceHolder holder) {
      super.surfaceCreated(holder);
   }

   @Override
   public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
      super.surfaceChanged(holder, format, w, h);
   }

   @Override
   public void surfaceDestroyed(SurfaceHolder holder) {
      super.surfaceDestroyed(holder);
   }

   private void init() {

   }

}
