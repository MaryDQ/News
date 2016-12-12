package com.mlxphone.news.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by MLXPHONE on 2016/12/2 0002.
 */

public class BitmapUtil {
    public static final int LRUSIZE = (int) (Runtime.getRuntime().maxMemory() / 8192);

    //一级缓存，LruCache
    static LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(LRUSIZE) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getWidth() * value.getRowBytes();
        }
    };

    public static void setFileCache(Context context, String imageURL, Bitmap bitmap) {
        File cacheFile = context.getCacheDir();
        String fileMD5 = MD5Util.getMD5(imageURL);
        File targetFile = new File(cacheFile, fileMD5 + ".PNG");//缓存输出目录
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(targetFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getFileCache(Context context, String imageURL) {
        File cacheFile = context.getCacheDir();
        String fileMD5 = MD5Util.getMD5(imageURL);
        File targetFile = new File(cacheFile, fileMD5 + ".PNG");
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeFile(targetFile.getAbsolutePath());
        return bitmap;
    }

    public static void setBitmap(Context context, String imageURL, ImageView imageView) {
        Bitmap bitmap = null;
        String fileMD5 = MD5Util.getMD5(imageURL);
        bitmap = lruCache.get(fileMD5);
        //一级缓存为空
        if (bitmap == null) {
            bitmap = getFileCache(context, imageURL);
            //二级缓存为空
            if (bitmap == null) {
                try {
                    URL url = new URL(imageURL);
                    HttpClientUtil.setBitmapToImageView(context, url, imageView);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                lruCache.put(fileMD5, bitmap);
                String tag = (String) imageView.getTag();
                if (tag.equals(imageURL)) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        } else {
            String tag = (String) imageView.getTag();
            if (tag.equals(imageURL)) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }


}
