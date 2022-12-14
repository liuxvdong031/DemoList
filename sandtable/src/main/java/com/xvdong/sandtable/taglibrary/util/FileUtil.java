package com.xvdong.sandtable.taglibrary.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtil {
    public static final String TAG = FileUtil.class.getSimpleName();

    public static void saveBitmap(String fullName, Bitmap bitmap) {
        File f = new File(fullName);
        try {
            if (!f.exists()) {
                boolean success = f.createNewFile();
                if (!success) {
                    Log.e(TAG, "saveBitmap create file failed");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 60, fOut);
        try {
            if (fOut != null) {
                fOut.flush();
                fOut.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBitmapNoCompress(String fullName, Bitmap bitmap) {
        File f = new File(fullName);
        try {
            if (!f.exists()) {
                boolean success = f.createNewFile();
                if (!success) {
                    Log.e(TAG, "saveBitmap create file failed");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            if (fOut != null) {
                fOut.flush();
                fOut.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????
     * @param oldPath String ??????????????? ??????c:/fqf.txt
     * @param newPath String ??????????????? ??????f:/fqf.txt
     */
    public static void copyFile(String oldPath, String newPath) throws IOException {
        int byteRead;
        File oldFile = new File(oldPath);
        if (oldFile.exists()) { //???????????????
            InputStream inStream = new FileInputStream(oldPath); //???????????????
            FileOutputStream fs = new FileOutputStream(newPath);
            byte[] buffer = new byte[1024];
            while ((byteRead = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteRead);
                fs.flush();
            }
            inStream.close();
            fs.close();
        }
    }

    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            File cacheDir = context.getExternalCacheDir();

            if(cacheDir == null) {
                cacheDir = new File("/Android/data/" + context.getPackageName() + "/cache/");
            }
            if(!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            cachePath = cacheDir.getPath();
        } else {
            File cacheDir = context.getCacheDir();
            if(!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            cachePath = cacheDir.getPath();
        }
        File file = new File(cachePath + File.separator + uniqueName);
        File dir = file.getParentFile();
        if(!dir.exists()) {
            dir.mkdirs();
        }
        return file;
    }

    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * ??????SDcard??????
     *
     * @return boolean
     */
    public static boolean checkSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static void clearDir(String dirName) {
        File dir = new File(dirName);
        if(dir == null || !dir.exists()) {
            return;
        }
        for(File file : dir.listFiles()) {
            if(file.exists()) {
                file.delete();
            }
        }
    }

    //???SD????????????????????????
    public static void saveFile(String fullName, byte[] bytes) {
        //??????????????????openFileOutput???,???????????????????????????????????????
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(fullName);
            //???bytes?????????????????????
            output.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //???????????????
                if(output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ???????????????????????????
     * @param context
     * @return  ?????????????????????
     */
    public static String setMkdir(Context context)
    {
        String filePath;
        if(checkSDCard())
        {
            filePath = Environment.getExternalStorageDirectory()+File.separator+"myfile";
        }else{
            filePath = context.getCacheDir().getAbsolutePath()+File.separator+"myfile";
        }
        File file = new File(filePath);
        if(!file.exists())
        {
            boolean b = file.mkdirs();
            Log.e("file", "???????????????  ????????????    "+b);
        }else{
            Log.e("file", "????????????");
        }
        return filePath;
    }

    public static String saveBitmapToTemp(String filePath, Bitmap mBitmap) {
        File f = new File(filePath);
        File dir = f.getParentFile();
        if(!dir.exists()) {
            dir.mkdirs();
        }
        if(f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            Log.d("saveBitmapToTemp", "???????????????????????????" + e.toString());
            return null;
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 60, fOut);
        try {
            fOut.flush();
            fOut.close();
            return f.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ??????????????????
     * @param path ??????????????????
     */
    public static String sdCard2Uri(String path) {
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        return "file://" + path;
    }

    /**
     * assets?????????????????????
     *
     * @param path assets?????????????????????
     */
    public static String assets2Uri(String path) {
        // String imageUri = "assets://image.png"; // from assets
        return "assets://" + path;
    }

    /**
     * drawable?????????????????????
     *
     * @param drawableId drawable?????????????????????id
     */
    public static String drawable2Uri(int drawableId) {
        // String imageUri = "drawable://" + R.drawable.image; // from drawables
        // (only images, non-9patch)
//        ImageLoader.getInstance().displayImage("drawable://" + imageId,
//                imageView);
        return "drawable://" + drawableId;
    }

    /**
     * ????????????????????????????????????
     * @param uri ?????????????????????????????????
     */
    public static String content2Uri(String uri) {
        // String imageUri = "content://media/external/audio/albumart/13"; //
        // from content provider
        return "content://" + uri;
    }
}
