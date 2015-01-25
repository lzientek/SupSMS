package com.supinfo.supsms.app.helpers;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class EncodingHelper {
    public static String ToBase64(String textToEncode) throws UnsupportedEncodingException {

        String body64 = textToEncode;
        byte[] byteArray = body64.getBytes("UTF-8");

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}