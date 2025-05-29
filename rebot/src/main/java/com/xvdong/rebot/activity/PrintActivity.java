package com.xvdong.rebot.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.print.PrintHelper;

import com.xvdong.rebot.common.Constants;
import com.xvdong.rebot.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xvDong on 2025/5/27.
 * 打印工具,可以打印单张和多张图片
 */

public class PrintActivity extends AppCompatActivity {


    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, PrintActivity.class);
        starter.putExtra(Constants.BUNDLE,bundle);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermission();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                1001);

    }

    /**
     *打印一张图片
     */
    private void sharePhoto() {
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.image1);
        photoPrinter.printBitmap("droids.jpg - test print", bitmap);
    }


    /**
     * 打印一组图片,将图片转为pdf后打印
     * @param uri
     */
    private void sharePDF(Uri uri) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
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

    public File convertImagesToPdf(List<Bitmap> images) throws IOException {
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

    private Uri createPDFUri() {
        File imagePath = new File("/storage/emulated/0/Android/data/com.xvdong.rebot/files/test.pdf");
        Uri uri = FileProvider.getUriForFile(this, "com.xvdong.rebot.provider", imagePath);
        return uri;
    }
}
