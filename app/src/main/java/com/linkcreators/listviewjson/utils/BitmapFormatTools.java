package com.linkcreators.listviewjson.utils;

/**
 * Bitmap与DrawAble与byte[]与InputStream之间的转换工具类
 * Created by Jun on 2015/10/30.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Bitmap与Drawable与byte[]与InputStream之间的转换工具类
 * @author Jun
 */

public class BitmapFormatTools {

    private static BitmapFormatTools tools = new BitmapFormatTools();

    public static BitmapFormatTools getInstance() {
        if (tools == null) {
            tools = new BitmapFormatTools();
            return tools;
        }
        return tools;
    }

    /**
     * 将byte[]转换成InputStream
     * @param bytes
     * @return
     */
    //
    public InputStream Byte2InputStream(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return byteArrayInputStream;
    }

    /**
     * 将InputStream转换成byte[]
     * @param inputStream
     * @return
     */
    public byte[] InputStream2Bytes(InputStream inputStream) {
        String str = "";
        byte[] readByte = new byte[1024];
        try {
            while(-1 != inputStream.read(readByte, 0, 1024)){
                str += new String(readByte).trim();
            }
            return str.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将Bitmap转换成InputStream
     * @param bitmap
     */
    public InputStream Bitmap2InputStream(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return inputStream;
    }

    /**
     * 将Bitmap转换成InputStream
     * @param bitmap
     * @param quality
     * @return
     */
    public InputStream Bitmap2InputStream(Bitmap bitmap, int quality) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //按指定的图片格式以及画质，将图片转换为输出流;
        //quality：画质，0-100.0表示最低画质压缩，100以最高画质压缩;
        //对于PNG等无损格式的图片，会忽略此项设置
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, byteArrayOutputStream);
        InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return inputStream;
    }

    /**
     * 将InputStream转换成Bitmap
     * @param inputStream
     * @return
     */
    public Bitmap InputStream2Bitmap(InputStream inputStream) {
        return BitmapFactory.decodeStream(inputStream);
    }

    /**
     * Drawable转换成InputStream
     * @param drawable
     * @return
     */
    public InputStream Drawable2InputStream(Drawable drawable) {
        Bitmap bitmap = this.drawable2Bitmap(drawable);
        return this.Bitmap2InputStream(bitmap);
    }

    /**
     * InputStream转换成Drawable
     * @param inputStream
     * @return
     */
    public Drawable InputStream2Drawable(Context context,InputStream inputStream) {
        Bitmap bitmap = this.InputStream2Bitmap(inputStream);
        return this.bitmap2Drawable(context,bitmap);
    }

    /**
     * Drawable转换成byte[]
     * @param drawable
     * @return
     */
    public byte[] Drawable2Bytes(Drawable drawable) {
        Bitmap bitmap = this.drawable2Bitmap(drawable);
        return this.Bitmap2Bytes(bitmap);
    }

    /**
     * byte[]转换成Drawable
     * @param bytes
     * @return
     */
    public Drawable Bytes2Drawable(Context context,byte[] bytes) {
        Bitmap bitmap = this.Bytes2Bitmap(bytes);
        return this.bitmap2Drawable(context,bitmap);
    }

    /**
     * Bitmap转换成byte[]
     * @param bitmap
     * @return
     */
    public byte[] Bitmap2Bytes(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * byte[]转换成Bitmap
     * @param bytes
     * @return
     */
    public Bitmap Bytes2Bitmap(byte[] bytes) {
//        if (0 != bytes.length && bytes != null) {
//            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//        }
//        return null;
        return (bytes == null || bytes.length == 0) ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * Drawable转换成Bitmap
     * @param drawable
     * @return
     */
    public Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Bitmap转换成Drawable
     * @param bitmap
     * @return
     */
    public Drawable bitmap2Drawable(Context context,Bitmap bitmap) {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(),bitmap);
        return bitmapDrawable;
    }
}