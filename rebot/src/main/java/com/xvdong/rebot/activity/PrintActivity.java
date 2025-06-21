package com.xvdong.rebot.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.print.PrintHelper;

import com.xvdong.rebot.R;
import com.xvdong.rebot.common.Constants;

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

    private static final int REQUEST_STORAGE_PERMISSION = 1;

    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, PrintActivity.class);
        starter.putExtra(Constants.BUNDLE, bundle);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        requestStoragePermission();
        findViewById(R.id.btn_pic).setOnClickListener(v -> {
            printPhoto();
        });
        findViewById(R.id.btn_pdf).setOnClickListener(v -> {
            printImagesAsPdf();
        });
    }

    /**
     * 打印一张图片
     */
    private void printPhoto() {
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image1);
        photoPrinter.printBitmap("droids.jpg - test print", bitmap);
    }


    /**
     * 打印一组图片,将图片转为pdf后打印
     *
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

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.image1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.image2);
//        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(),R.drawable.image3);
//        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(),R.drawable.image4);
        bitmaps.add(bitmap1);
        bitmaps.add(bitmap2);
//        bitmaps.add(bitmap3);
//        bitmaps.add(bitmap4);
        try {
            // 创建 PDF 文件
            File pdfFile = convertImagesToPdf(bitmaps);
            // 获取 PDF 文件的 Uri（使用 FileProvider）
            Uri pdfUri = FileProvider.getUriForFile(this, "com.xvdong.rebot.provider", pdfFile);
            // 获取打印管理器
            PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
            // 创建打印适配器
            PrintDocumentAdapter printAdapter = new PdfDocumentAdapter(this, pdfUri);
            // 设置打印任务名称
            String jobName = getString(R.string.app_name) + " Document";
            // 启动打印任务
            printManager.print(jobName, printAdapter, null);
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
        // 使用安全的文件保存方式
        File pdfFile = getOutputPdfFile();
        try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
            pdfDocument.writeTo(fos);
        } finally {
            pdfDocument.close();
        }
        return pdfFile;
    }

    private File getOutputPdfFile()throws IOException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10+ 使用 MediaStore
            ContentResolver resolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "images.pdf");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

            Uri uri = resolver.insert(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY), contentValues);
            if (uri == null) {
                throw new IOException("Failed to create PDF file");
            }
            return new File(getPathFromUri(uri));
        } else {
            // Android 9- 使用传统方式
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!downloadsDir.exists()) downloadsDir.mkdirs();
            return new File(downloadsDir, "images.pdf");
        }
    }

    // URI 转实际路径的辅助方法
    private String getPathFromUri(Uri uri) {
        try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
                return cursor.getString(index);
            }
        }
        return uri.getPath(); // 备用方案
    }


    private void requestStoragePermission() {
        // Android 11+ 使用 MANAGE_EXTERNAL_STORAGE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_STORAGE_PERMISSION);
            }
        } else {// Android 6.0-10 使用传统权限请求
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION
                );
            }
        }
    }


}
