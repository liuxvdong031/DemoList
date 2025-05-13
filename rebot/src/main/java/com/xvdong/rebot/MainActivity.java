package com.xvdong.rebot;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.print.PrintHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();

//        FileUtils.copyAssetToDownload(this, "image1.jpg");

        findViewById(R.id.iv1).setOnClickListener(v -> {
//            Uri uri = createPDFUri();
//            sharePDF(uri);

//            ArrayList<Uri> imageUris = createImageUris();
//            sharePics(imageUris);


            doPhotoPrint();
//            printServices();
        });

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printService();
            }
        });
        test();
    }

    private void test() {
        findViewById(R.id.btn1).setOnClickListener(v -> {
            setLocale("en");
        });

        findViewById(R.id.btn2).setOnClickListener(v -> {
            setLocale("zh");
        });
    }


    public void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        recreate();
    }


    private void printService() {
        // 创建文件对象，指向要分享的文件
        File imageFile = new File("/storage/emulated/0/Download/image1.jpg");
        // 通过 FileProvider 生成 content:// URI
        Uri imageUri = FileProvider.getUriForFile(this, "com.xvdong.rebot.provider", imageFile);
        // 创建打印意图
        Intent printIntent = new Intent(Intent.ACTION_SEND);
        printIntent.setType("image/*");
        printIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        // 授予临时权限，使目标应用可以读取该文件
        printIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 设置打印服务组件
        startActivity(printIntent);
    }


    private void printServices() {
        // 假设你有多个图片文件
        File imageFile1 = new File("/storage/emulated/0/Download/image1.jpg");
        File imageFile2 = new File("/storage/emulated/0/Download/image2.jpg");
        File imageFile3 = new File("/storage/emulated/0/Download/image3.jpg");
        // 创建 URI 列表
        ArrayList<Uri> imageUris = new ArrayList<>();
        imageUris.add(FileProvider.getUriForFile(this, "com.xvdong.rebot.provider", imageFile1));
        imageUris.add(FileProvider.getUriForFile(this, "com.xvdong.rebot.provider", imageFile2));
        imageUris.add(FileProvider.getUriForFile(this, "com.xvdong.rebot.provider", imageFile3));
        // 创建打印意图
        Intent printIntent = new Intent(Intent.ACTION_SEND);
        printIntent.setType("image/*");
        printIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        // 授予临时权限，使目标应用可以读取这些文件
        printIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 启动打印服务
        startActivity(printIntent);


    }

    @NonNull
    private ArrayList<Uri> createImageUris() {
        ArrayList<Uri> imageUris = new ArrayList<>();
        File image1 = new File("/storage/emulated/0/Download/image1.jpg");
        if (image1.exists()) {
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

    public static File convertImagesToPdf(List<Bitmap> images) throws IOException {
        // 创建一个 PdfDocument 实例
        PdfDocument pdfDocument = new PdfDocument();

        // A4 页面尺寸：595x842
        int pageWidth = 595;
        int pageHeight = 842;

        for (Bitmap image : images) {
            // 计算缩放比例
            float scaleWidth = (float) pageWidth / image.getWidth();
            float scaleHeight = (float) pageHeight / image.getHeight();
            float scale = Math.min(scaleWidth, scaleHeight); // 保证图片在页面内完全显示

            // 计算缩放后的图片尺寸
            int scaledWidth = (int) (image.getWidth() * scale);
            int scaledHeight = (int) (image.getHeight() * scale);

            // 创建新的缩放后的 Bitmap
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(image, scaledWidth, scaledHeight, true);

            // 创建页面，设置为 A4 尺寸
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);

            // 获取页面画布
            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();

            // 计算将缩放后的图片居中显示的起始坐标
            int x = (pageWidth - scaledWidth) / 2;
            int y = (pageHeight - scaledHeight) / 2;

            // 在页面上绘制缩放后的图片
            canvas.drawBitmap(scaledBitmap, x, y, paint);

            // 结束页面绘制
            pdfDocument.finishPage(page);

            // 回收 Bitmap 内存
            if (scaledBitmap != image) {
                scaledBitmap.recycle();
            }
        }

        // 保存 PDF 文件
        File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "images.pdf");
        try (FileOutputStream fileOutputStream = new FileOutputStream(pdfFile)) {
            pdfDocument.writeTo(fileOutputStream);
        } finally {
            pdfDocument.close();
        }

        return pdfFile;
    }

    //打印PDF文件
    public void printImagesAsPdf() {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        Bitmap bitmap1 = BitmapFactory.decodeFile("/storage/emulated/0/Download/image1.jpg");
        Bitmap bitmap2 = BitmapFactory.decodeFile("/storage/emulated/0/Download/image2.jpg");
        Bitmap bitmap3 = BitmapFactory.decodeFile("/storage/emulated/0/Download/image3.jpg");
        Bitmap bitmap4 = BitmapFactory.decodeFile("/storage/emulated/0/Download/image4.jpg");
        bitmaps.add(bitmap1);
        bitmaps.add(bitmap2);
        bitmaps.add(bitmap3);
        bitmaps.add(bitmap4);
        try {
            // 将图片转换为 PDF 文件
            File pdfFile = convertImagesToPdf(bitmaps);
            // 获取 PDF 文件的 Uri
            Uri pdfUri = FileProvider.getUriForFile(this, "com.xvdong.rebot.provider", pdfFile);
            // 创建打印意图
            Intent printIntent = new Intent(Intent.ACTION_SEND);
            printIntent.setType("application/pdf");
            printIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            // 授予临时权限
            printIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // 启动打印
            startActivity(printIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PDFPrinting", "Error creating or printing PDF", e);
        }
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
//        shareIntent.setPackage("com.hp.android.printservice");

        // 启动分享
        startActivity(Intent.createChooser(shareIntent, "请选择打印服务"));
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                1001);

    }

    private void doPhotoPrint() {
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.image1);
        photoPrinter.printBitmap("droids.jpg - test print", bitmap);
    }
}