package com.xvdong.rebot;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();

        FileUtils.copyAssetToDownload(this,"image1.jpg");

        findViewById(R.id.tv_share).setOnClickListener(v -> {
//            Uri uri = createPDFUri();
//            sharePDF(uri);

            ArrayList<Uri> imageUris = createImageUris();
            sharePics(imageUris);
        });
    }

    @NonNull
    private ArrayList<Uri> createImageUris() {
        ArrayList<Uri> imageUris = new ArrayList<>();
        File image1 = new File("/storage/emulated/0/Download/image1.jpg");
        if (image1.exists()){
            Uri uri1 = FileProvider.getUriForFile(this, "com.xvdong.rebot.provider", image1);
            imageUris.add(uri1);
        }

//        File image2 = new File("/storage/emulated/0/Download/image.png");
//        if (image2.exists()) {
//            Uri uri2 = FileProvider.getUriForFile(this, "com.xvdong.rebot.provider", image2);
//            imageUris.add(uri2);
//        }
        return imageUris;
    }

    private Uri createPDFUri() {
        File imagePath = new File("/storage/emulated/0/Android/data/com.xvdong.rebot/files/test.pdf");
        Uri uri = FileProvider.getUriForFile(this, "com.xvdong.rebot.provider", imagePath);
        return uri;
    }

    private void sharePDF(Uri uri) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("image/*");
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
    }

    private void sharePics(ArrayList<Uri> imageUris) {
        // 创建Intent来分享图片
        Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.setType("image/*"); // 可以发送多种类型的图片
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setPackage("com.hp.android.printservice");

        // 启动分享
        startActivity(Intent.createChooser(shareIntent, "请选择打印服务"));
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                1001);

    }
}