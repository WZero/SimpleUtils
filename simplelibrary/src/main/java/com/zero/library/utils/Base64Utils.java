package com.zero.library.utils;


import android.util.Base64;

/**
 * @author Wang Base64 工具类
 */
public class Base64Utils {

    public static String encodeToString(final byte[] binaryData) {
        return new String(Base64.encode(binaryData, Base64.DEFAULT));
    }

    public static String encodeToString(final String binaryData) {
        return encodeToString(binaryData.getBytes());
    }

    public static byte[] encode(final byte[] binaryData) {
        return Base64.encode(binaryData, Base64.DEFAULT);
    }

    public static byte[] encode(final String binaryData) {
        return Base64.encode(binaryData.getBytes(), Base64.DEFAULT);
    }

    public static String decodeToString(final String base64String) {
        return new String(Base64.decode(base64String, Base64.DEFAULT));
    }

    public static byte[] decode(final byte[] base64Data) {
        return Base64.decode(base64Data, Base64.DEFAULT);
    }

    public static byte[] decode(final String base64String) {
        return Base64.decode(base64String, Base64.DEFAULT);
    }

    public static String decodeToString(final byte[] base64Data) {
        return new String(Base64.decode(base64Data, Base64.DEFAULT));
    }
}
