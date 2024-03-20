package com.xvdong.demolist;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xvdong.demolist.business.coordinator.activity.CoordinatorActivity;
import com.xvdong.demolist.business.custom.activity.CustomActivity;
import com.xvdong.demolist.business.jetpack.JetpackActivity;
import com.xvdong.demolist.business.recycler.RecyclerViewDemoActivity;
import com.xvdong.demolist.core.ui.TouchHelperActivity;
import com.xvdong.demolist.temp.CameraActivity;
import com.xvdong.demolist.temp.MyCanvasView;
import com.xvdong.demolist.temp.UnlockActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.imageView);
        MyCanvasView view = findViewById(R.id.canvas);
        float[] numbers = {0f, 1f, 3f, 5f, 9f};
        view.setNumbers(numbers);
        init();
    }

    public void toCoordinator(View view) {
        startActivity(new Intent(this, CoordinatorActivity.class));
    }

    public void toRecycler(View view) {
        startActivity(new Intent(this, RecyclerViewDemoActivity.class));
    }

    public void toCustom(View view) {
        startActivity(new Intent(this, CustomActivity.class));
    }

    public void toJetpack(View view) {
        startActivity(new Intent(this, JetpackActivity.class));
    }

    public void toCamera(View view) {
        startActivity(new Intent(this, CameraActivity.class));
    }

    public void toDrag(View view) {
        startActivity(new Intent(this, TouchHelperActivity.class));
    }


    public void shakeView(View view) {
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(view, "rotation", 0, 10);
        rotateAnimator.setInterpolator(new CycleInterpolator(3));
        rotateAnimator.setDuration(500);
        rotateAnimator.start();
    }


    public void startAnim(View view) {

    }

    /**
     * Helper method to obtain a Parser with registered strike-through &amp; table extensions
     * &amp; task lists (added in 1.0.1)
     *
     * @return a Parser instance that is supported by this library
     * @since 1.0.0
     */


    public void jietu() {
        // 获取ImageView的Bitmap对象
        mImageView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(mImageView.getDrawingCache());
        mImageView.setDrawingCacheEnabled(false);

// 获取屏幕尺寸
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

// 获取ImageView在屏幕中的位置
        int[] location = new int[2];
        mImageView.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];

// 计算要截取的区域
        int width = mImageView.getWidth();
        int height = mImageView.getHeight();
        if (x < 0) {
            width += x;
            x = 0;
        }
        if (y < 0) {
            height += y;
            y = 0;
        }
        if (x + width > screenWidth) {
            width = screenWidth - x;
        }
        if (y + height > screenHeight) {
            height = screenHeight - y;
        }
        Rect rect = new Rect(x, y, x + width, y + height);

// 截取指定区域的Bitmap
        Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height());

        LogUtils.d("截图成功");
    }

    public void jietua(View view) {
        jietu();
    }


    //定义成为一个方法,直接调用就行了
    private void init() {
        NoticeView noticeView = findViewById(R.id.notice_view);
        List<String> notices = new ArrayList<>();
        notices.add("大促销下单拆福袋，亿万新年红包随便拿");
        notices.add("家电五折团，抢十亿无门槛现金红包");
        notices.add("星球大战剃须刀首发送200元代金券");
        noticeView.addNotice(notices);
        noticeView.startFlipping();

        noticeView.setOnNoticeClickListener((position, notice) -> {
            ToastUtils.showLong(notice);
        });
    }

    public void jiesuo(View view) {
        startActivity(new Intent(this, UnlockActivity.class));
    }


}