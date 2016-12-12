package com.mlxphone.news.utils;

import java.security.MessageDigest;

/**
 * Created by MLXPHONE on 2016/12/2 0002.
 */

public class MD5Util {
    public static String getMD5(String message) {
        StringBuffer sb = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(message.getBytes("UTF-8"));
            byte data[] = messageDigest.digest();
            sb = new StringBuffer();
            for (int i = 0; i < data.length; i++) {
                String str = Integer.toHexString(0xff & data[i]);
                if (str.length() == 1) {
                    sb.append(0).append(str);
                } else {
                    sb.append(str);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
