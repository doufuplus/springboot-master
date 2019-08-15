package com.doufuplus.boot.shiro.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Base64工具类
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/03
 */
public class Base64Util {

    /**
     * 加密JDK1.8
     */
    public static String encode(String str) {
        try {
            byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes("utf-8"));
            return new String(encodeBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encode(byte[] str) {
        byte[] encodeBytes = Base64.getEncoder().encode(str);
        return new String(encodeBytes);
    }

    /**
     * 解密JDK1.8
     */
    public static String decode(String str) {
        try {
            byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes("utf-8"));
            return new String(decodeBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密JDK1.8
     */
    public static String encodeThrowsException(String str) throws UnsupportedEncodingException {
        byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes("utf-8"));
        return new String(encodeBytes);
    }

    /**
     * 解密JDK1.8
     */
    public static String decodeThrowsException(String str) throws UnsupportedEncodingException {
        byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes("utf-8"));
        return new String(decodeBytes);
    }

}
