package com.example.mcs;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by towel on 2017/6/25.
 * 参见《第一行代码——Android》
 */

public class HandleImage {
    private static Context cotext;
    @TargetApi(19)
    public static String handleImageOnKitKat(Context context,Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        cotext=context;
        if (DocumentsContract.isDocumentUri(cotext, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    public static String handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        return imagePath;
    }
    private static String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = cotext.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void imageOutput(Bitmap bitmap){
        FileOutputStream fielOut=null;
        BufferedOutputStream bufOut=null;
        try{
            fielOut=cotext.openFileOutput("data", Context.MODE_PRIVATE);
            bufOut=new BufferedOutputStream(fielOut);
            bitmap.compress(Bitmap.CompressFormat.JPEG,30,bufOut);
            bufOut.flush();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(bufOut!=null)
                    bufOut.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private Bitmap imageInput(){
        FileInputStream fileInput=null;
        BufferedInputStream bufInput=null;
        Bitmap bitmap=null;
        try{
            fileInput=cotext.openFileInput("data");
            bufInput=new BufferedInputStream(fileInput);
            bitmap= BitmapFactory.decodeStream(bufInput);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (bufInput != null) {
                    bufInput.close();
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}
