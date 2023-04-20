package com.xvdong.demolist.temp; /**
 * Created by xvDong on 2023/4/19.
 */

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.xvdong.demolist.R;

public class CameraActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

}