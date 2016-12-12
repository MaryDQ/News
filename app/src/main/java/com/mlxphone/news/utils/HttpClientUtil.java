package com.mlxphone.news.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by MLXPHONE on 2016/11/30 0030.
 */

public class HttpClientUtil {
    public interface onJsonGetListener {
        public void jsonGetSuccess(String json);

        public void jsonGetFail(int responseCode);

        public void jsonGetException(Exception e);
    }

    public static void getJson(URL targetUrl, onJsonGetListener listener) {
        HttpURLConnection httpURLConnection = null;
        StringBuffer json = new StringBuffer();
        boolean isRight = false;
        while (!isRight) {
            try {
                httpURLConnection = (HttpURLConnection) targetUrl.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200) {
                    isRight = true;
                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String msg = null;
                    while ((msg = bufferedReader.readLine()) != null) {
                        json.append(msg);
                    }
                    bufferedReader.close();
                    inputStreamReader.close();
                    inputStream.close();
                    listener.jsonGetSuccess(json.toString());
                } else {
                    listener.jsonGetFail(httpURLConnection.getResponseCode());
                }
            } catch (IOException e) {
                listener.jsonGetException(e);
            } finally {
                httpURLConnection.disconnect();
            }
        }

        //return json.toString();
    }

    public static void setBitmapToImageView(final Context context, final URL targetURL, final ImageView imageView) {
        final String fileMD5 = MD5Util.getMD5(targetURL.toString());
        AsyncTask<URL, Void, Bitmap> asyncTask = new AsyncTask<URL, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(URL... urls) {
                Bitmap bitmap = null;
                URL url = urls[0];
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.connect();
                    if (connection.getResponseCode() == 200) {
                        InputStream is = connection.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);
                        is.close();
                        connection.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (imageView.getTag().equals(targetURL.toString())) {
                    BitmapUtil.lruCache.put(fileMD5, bitmap);
                    BitmapUtil.setFileCache(context, targetURL.toString(), bitmap);
                    imageView.setImageBitmap(bitmap);
                }
            }
        };
        asyncTask.execute(targetURL);
    }

}
