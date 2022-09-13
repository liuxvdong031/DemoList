package com.xvdong.demolist;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by xvDong on 2022/9/13.
 */

public class AppApplication extends Application {
   @Override
   public void onCreate() {
      super.onCreate();
      initSDK();
   }

   private void initSDK() {
      Utils.init(this);
   }
}
