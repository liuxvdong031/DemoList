package com.xvdong.rebot;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by xvDong on 2024/10/14.
 */

public class FileUtils {
    private static final String TAG = "FileUtils";

    // 将assets中的图片复制到Download目录
    public static void copyAssetToDownload(Context context, String assetFileName) {
        AssetManager assetManager = context.getAssets();
        InputStream in = null;
        OutputStream out = null;

        try {
            // 获取assets中的图片
            in = assetManager.open(assetFileName);

            // 获取Download目录
            File downloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File outFile = new File(downloadDirectory, assetFileName);

            // 创建输出流
            out = new FileOutputStream(outFile);

            // 复制文件
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            Log.d(TAG, "Image copied to Download folder: " + outFile.getAbsolutePath());

        } catch (IOException e) {
            Log.e(TAG, "Failed to copy asset file: " + assetFileName, e);
        } finally {
            // 关闭流
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e(TAG, "Failed to close input stream", e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    Log.e(TAG, "Failed to close output stream", e);
                }
            }
        }
    }
}
