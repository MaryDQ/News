package com.mlxphone.news;

import com.mlxphone.news.utils.HttpClientUtil;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getJson(){
        URL url=null;
        try {
            url = new URL("http://v.juhe.cn/toutiao/index?type=top&key=d728ab4e75e137c4f23aec12ed3ee6cd");
            HttpClientUtil.getJson(url, new HttpClientUtil.onJsonGetListener() {
                @Override
                public void jsonGetSuccess(String json) {
                    System.out.print(json);
                }

                @Override
                public void jsonGetFail(int responseCode) {
                    System.out.print(responseCode);
                }

                @Override
                public void jsonGetException(Exception e) {
                    System.out.print(e.toString());
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}