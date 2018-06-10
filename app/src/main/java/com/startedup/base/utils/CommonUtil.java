package com.startedup.base.utils;

import android.content.Context;
import android.widget.Toast;

public class CommonUtil {

    public static void showToasLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showToasShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
