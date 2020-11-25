package com.chewawa.baselibrary.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.chewawa.baselibrary.BaseApplication;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2019/9/27 18:38
 */
public class FileUtils {
    public static String photoPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    public static String getFileName(String path){

        int start=path.lastIndexOf("/");
        int end=path.lastIndexOf(".");
        if(start!=-1){
            return path.substring(start+1);
        }else{
            return null;
        }

    }
    public static String getAbsolutePath(String path){

        int start=path.lastIndexOf("/");
        if(start!=-1){
            return path.substring(0, start);
        }else{
            return null;
        }

    }
    public static void calcProgressToView(ProgressBar progressBar, long offset, long total) {
        final float percent = (float) offset / total;
        progressBar.setProgress((int) (percent * progressBar.getMax()));
    }


    public static File getParentFile(@NonNull Context context) {
        final File externalSaveDir = context.getExternalCacheDir();
        if (externalSaveDir == null) {
            return context.getCacheDir();
        } else {
            return externalSaveDir;
        }
    }
    public static String getMimeType(String filePath) {
        String mime;
        String ext = filePath.substring(filePath.lastIndexOf('.')).toLowerCase(Locale.US);
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String temp = ext.substring(1);
        mime = mimeTypeMap.getMimeTypeFromExtension(temp);
        mime = TextUtils.isEmpty(mime) ? "*/*" : mime;
        return mime;
    }
    /**
     * 截屏
     *
     * @return
     */
    public static boolean shotView(Context context, View view) {

        view.setDrawingCacheEnabled(true);
        try {
            File filesDir = context.getFilesDir();//  /data/user/0/com.car.wawa/files/1515397236823.jpg
            File cacheDir = context.getCacheDir();//   /data/user/0/com.car.wawa/cache
            File myCaptureFile = new File(cacheDir, System.currentTimeMillis() + ".jpg");

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
//            Bitmap drawingCache = view.getDrawingCache();
            Bitmap drawingCache = loadBitmapFromView(view);
            drawingCache.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
            // 其次把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(), myCaptureFile.getAbsolutePath(), myCaptureFile.getName(), null);
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(myCaptureFile)));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        view.setDrawingCacheEnabled(false);
        return false;
    }
    public static Bitmap loadBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(screenshot);
        canvas.drawColor(Color.WHITE);
        canvas.translate(-v.getScrollX(), -v.getScrollY());//我们在用滑动View获得它的Bitmap时候，获得的是整个View的区域（包括隐藏的），如果想得到当前区域，需要重新定位到当前可显示的区域
        v.draw(canvas);// 将 view 画到画布上
        return screenshot;
    }
    public static String fileSizeConvert(long length) {
//        return Formatter.formatShortFileSize(SysApplication.getInstance(), size);
        String result = null;
        int sub_string = 0;
        // 如果文件长度大于1GB
        if (length >= 1073741824) {
            sub_string = String.valueOf((float) length / 1073741824).indexOf(
                    ".");
            result = ((float) length / 1073741824 + "000").substring(0,
                    sub_string + 3) + "GB";
        } else if (length >= 1048576) {
            // 如果文件长度大于1MB且小于1GB,substring(int beginIndex, int endIndex)
            sub_string = String.valueOf((float) length / 1048576).indexOf(".");
            result = ((float) length / 1048576 + "000").substring(0,
                    sub_string + 3) + "MB";
        } else if (length >= 1024) {
            // 如果文件长度大于1KB且小于1MB
            sub_string = String.valueOf((float) length / 1024).indexOf(".");
            result = ((float) length / 1024 + "000").substring(0,
                    sub_string + 3) + "KB";
        } else if (length < 1024)
            result = Long.toString(length) + "B";
        return result;
    }
    public static Bitmap Base64ToBitMap(String base64) {
        byte[] decode = Base64.decode(base64, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
        return bitmap;
    }
    public interface OnSaveVideoCoverListener{
        void onSaveVideoCoverSuccess(String savePath);
    }
    // string & str：图片名称
    public static void saveBitmap(Bitmap bitmap,String savePath) {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(new File(savePath)));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getExternalCacheDir(){
        File file = BaseApplication.getInstance().getExternalCacheDir();
        if(file != null){
            return file.getAbsolutePath();
        }
        return "";
    }

}
